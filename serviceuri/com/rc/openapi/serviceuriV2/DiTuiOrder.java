package com.rc.openapi.serviceuriV2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.service.TOrderItemManager;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CartManagerImpl;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.serviceuri.CartUtil;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderItem;

public class DiTuiOrder extends BaseURIService {
	
	private OpenSqlManageImpl opensqlmanage;
	private CartManagerImpl cartmanager;
	private TOrderItemManager torderitemmanager;
	private TOrderManager tordermanager;
	private TMemberManager tmembermanager;

	
	public static void main(String[] args) throws SQLException, IOException {
		
		DiTuiOrder o = new DiTuiOrder();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
		o.opensqlmanage = (OpenSqlManageImpl) context.getBean("opensqlmanage");
		Map map = new HashMap();
		
		
		
		List<String> list = new ArrayList<String>();
		 File file=new File("c://no.txt");
		 if(file.isFile() && file.exists()){ //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
            new FileInputStream(file),"UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
            	map.put("no", lineTxt);
            	o.opensqlmanage.insertByMap_drug(map, "c_version.insrt-a");
            }
            read.close();
		 }
		 
	}
	
	/**
	 * 获取订单号
	 * @return
	 */
	public String getOrderSn(){
		
		   opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		   Map<String,Object> paramMap = new HashMap<String,Object>();
		   paramMap.put("len", 6);
		   String ordersn =(String) opensqlmanage.selectForObjectByMap(paramMap, "t_order.select_order_sn_call");
		   if("-1".equals(ordersn)){
			  return getOrderSn();
		   }else{
			   return ordersn;
		   }
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = null;
		
		try{
			
			out = response.getWriter();
			
			opensqlmanage = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
			cartmanager = (CartManagerImpl) getSpringBean("cartmanager");
			tordermanager = (TOrderManager) getSpringBean("tordermanager");
			torderitemmanager = (TOrderItemManager) getSpringBean("torderitemmanager");
			tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
			
			String goosdid = request.getParameter("id");
			String name = request.getParameter("name");
			String phone = request.getParameter("phone");
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			String address_detail = request.getParameter("address_detail");
			String sessionid = request.getParameter("sessionid");
			String payway = request.getParameter("payway");
			String address1 = request.getParameter("address1");// 省市县
			String phone_type = request.getParameter("phone_type");
			
			System.out.println("手机类型=="+phone_type);
			
			System.out.println("sessionid============>"+sessionid);
			System.out.println("area================"+area);
			
			long userid = tmembermanager.getMemberId(sessionid);
			
			if(userid==-1){
				out.print(-1);
				out.close();
				return;
			}
		
			boolean isContain = CartUtil.isContain(Long.parseLong(goosdid));
			
			if(isContain)
			{
				boolean b = cartmanager.ditui(0, userid); 	//如该用户已经参加过地推
				System.out.println("b-------------->"+b);
				if(b){
					out.print(-2);
					out.close();
					return;
				}
			}
			
//			String system = request.getParameter("system");
			
			
			BigDecimal yunfei = new BigDecimal(0);
			
			if("1".equals(province)){	// 北京
				 TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
				 String ysd_ysdj_areaid =sys.getKeys("deliveryway_ysd_ysdj_areaid");//配送方式-药士达-药师到家-支持区域id
			    boolean ysd_ysdj_areaid_flag = false;
		 		for(int i=0;i<ysd_ysdj_areaid.split(",").length;i++){
		 			if(ysd_ysdj_areaid.split(",")[i].equals(area)){
		 				ysd_ysdj_areaid_flag= true;
		 			}
		 		}
				 if(ysd_ysdj_areaid_flag){	// 望京
					yunfei = new BigDecimal(0); 
				}else{
					yunfei = new BigDecimal(5); 
				}
			}else{	// 非北京地区
				yunfei = new BigDecimal(12); 
			}
			
			System.out.println("yunfei=============="+yunfei);
			
			// 查询商品 
			Map map = new HashMap();
			map.put("id", goosdid);
			Map rs = (HashMap)opensqlmanage.selectForObjectByMap(map, "mycart.get-ditui-goods");
			String price = (String)rs.get("price");
			String gname = (String)rs.get("full_name");
			String gsn = (String)rs.get("sn");
			
			
			TOrder order = new TOrder();
			String orderSn =getOrderSn();
			order.setOrderSn("m"+orderSn);//订单号
			order.setMemberId(userid);
			
			 
			 if("android".equals(phone_type)){
			    order.setOrderSource(3);//订单来源 1 pc 2 wap 3 安卓 4 ios 5 手动下单
			 }else{
			    order.setOrderSource(4);//订单来源 1 pc 2 wap 3 安卓 4 ios 5 手动下单
			 }
			    
			 
			 order.setShippingFee(yunfei);//运费
			 order.setPromotionalDiscount(new BigDecimal(0));//促销折扣
			 order.setCouponDiscount(new BigDecimal(0));//优惠券折扣
			 order.setCouponId(null);
			 order.setOrderAmount(new BigDecimal("0.01"));//订单金额
			 order.setPayableAmount(new BigDecimal("0.01"));//应付金额
			 order.setIntegrationDiscount(new BigDecimal("0"));//积分折扣
			 order.setUseIntegration(0);//使用积分
			 order.setAdjustAmount(new BigDecimal("0"));//调整金额
			 
			 
			
			 order.setPaidAmount(new BigDecimal("0"));//已付金额
			 order.setRewardIntegration(order.getOrderAmount().intValue()/2);//赠送积分   
		   
			 order.setReceiver(name);//收货人 
			 order.setAreaId(Long.valueOf(area));//地区id
			 order.setAreaName(address1);//地区名称
			 order.setDetailedAddress(address_detail);//详细地址
			 order.setZipCode("");//邮编
			 order.setPhone(phone);//联系电话
			 order.setIfInvoice(0);
			 order.setInvoiceTitle("");
			 order.setTax(new BigDecimal(0));
			 order.setRemark("地推订单");
			 
			 if("1".equals(payway) || "2".equals(payway) ){	// 支付宝
				
				if(yunfei.compareTo(new BigDecimal(0))==1){
					order.setDeliveryId(2L);
				}else{
					order.setDeliveryId(1L);
				}
			    order.setPaymentId(1L);//支付方式
			  }else{	// 货到付款
					order.setDeliveryId(3L);
					order.setPaymentId(4L);//支付方式
			  }
			 
			 order.setIsPush(1);
			 order.setPushTime(new Date());
			 order.setDeleteStatus(0);
			 order.setOrderStatus(0);
			 order.setOrderAmount(new BigDecimal(price));
			 order.setPayableAmount(new BigDecimal(price).add(yunfei));
			 order.setOrderType(1);
			 order.setReturnType(1);
			 order.setCreateDate(new Date());
			 order.setVerifyStatus(1);
			 
			 long orderid = tordermanager.insertSelective(order);
			 
			 TOrderItem item = new TOrderItem();
			 
			 item.setOrderId(orderid);
			 item.setGoodsId(Long.parseLong(goosdid));
			 item.setIfPremiums(0);
			 item.setQuantity(1);
			 item.setPrice(new BigDecimal(price));
			 item.setCreateTime(new Date());
			 torderitemmanager.insertSelective(item);
			 
			out.print(orderid);
			out.close();
			
		}catch(Exception e){
			out.print(-1);
			out.close();
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doPost(request, response);
	}
	

}
