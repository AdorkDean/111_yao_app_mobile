package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.CPaymentWayManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CDeliveryWayManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.service.impl.TMemberReceiverLatLonManagerImpl;
import com.rc.openapi.service.impl.TMemberReceiverManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.vo.CDeliveryWay;
import com.rc.openapi.vo.CDeliveryWayExample;
import com.rc.openapi.vo.CPaymentWay;
import com.rc.openapi.vo.TMemberReceiver;
import com.rc.openapi.vo.TMemberReceiverLatLon;

/**
 * 配送方式集合
 * @author user3
 *
 */
public class CDeliveryListURLService extends BaseURIService{
	private static final long serialVersionUID = 6198887281862291516L;

	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		String receiverNewFlag =  request.getParameter("receiverNewFlag");//使用新的收货地址标记   Flag  1 表示是 
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
		String errorMessage= "";
		List<Map<String,String>> deliveryWayList = new ArrayList<Map<String,String>>();
		if (sessionId != null && !"".equals(sessionId)) {
			try {
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if (userId == -1l) {
					statusCode = "3";// 非法用户
					errorMessage ="此用户信息不完整,请重新登录！";
				} else {
					String paymentWayId = request.getParameter("paymentWayId");//支付方式
					String receiverId = request.getParameter("receiverId");//收货地址id
					if(!StringUtils.hasText(paymentWayId)){
						paymentWayId="1";
					}
					CPaymentWayManager cpaymentwaymanager = (CPaymentWayManager)getSpringBean("cpaymentwaymanager");
					CPaymentWay paymentWay=	cpaymentwaymanager.selectByPrimaryKey(Long.valueOf(paymentWayId.trim()));
					Map<String,String> resultsmap  = new HashMap<String,String>();
					
						CDeliveryWayExample example = new CDeliveryWayExample();
						example.createCriteria().andDeliveryCodeNotEqualTo("hdfk");
						CDeliveryWayManagerImpl cdeliverywaymanager = (CDeliveryWayManagerImpl)getSpringBean("cdeliverywaymanager");
						List<CDeliveryWay> deliveryList = cdeliverywaymanager.selectByExample(example);
						Map<String,String> resultMap = null;
						 String receiverFlag ="0";// 0 表示外地   
						 TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
						 String ysd_ysdj_areaid =sys.getKeys("deliveryway_ysd_ysdj_areaid");//配送方式-药士达-药师到家-支持区域id
						 String	hdfk_area = sys.getKeys("paymethod_hdfk_area");
						 boolean ysd_ysdj_areaid_flag = false;
						 String areaName ="";
						 if(!"1".equals(receiverNewFlag)){
								TMemberReceiverManagerImpl tmemberreceivermanager = (TMemberReceiverManagerImpl) getSpringBean("tmemberreceivermanager");
								TMemberReceiver memberReceiver = tmemberreceivermanager.selectByPrimaryKey(Long.valueOf(receiverId.trim()));
								for(int i=0;i<ysd_ysdj_areaid.split(",").length;i++){
						 			if(ysd_ysdj_areaid.split(",")[i].equals(String.valueOf(memberReceiver.getAreaId()))){
						 				ysd_ysdj_areaid_flag= true;
						 			}
						 		}
								areaName = memberReceiver.getArea();
							}else{
								TMemberReceiverLatLonManagerImpl tmemberreceiverlatlonmanager = (TMemberReceiverLatLonManagerImpl) getSpringBean("tmemberreceiverlatlonmanager");
								TMemberReceiverLatLon receiverLatLon =tmemberreceiverlatlonmanager.selectByPrimaryKey(Long.valueOf(receiverId.trim()));
								 if(receiverLatLon!=null&&StringUtils.hasText(receiverLatLon.getStoreId())){
			            			 ysd_ysdj_areaid_flag= true;
			            		 }
								 areaName = receiverLatLon.getArea();
							}
						 if(ysd_ysdj_areaid_flag){//判断望京   是望京
							 receiverFlag="1";//表示 在门店的配送范围之内
			    		 }else if(areaName.indexOf(hdfk_area)>0){//判断是否是北京   
			    			 receiverFlag="2";//表示北京
			    		 }
						
						if(deliveryList!=null&&deliveryList.size()>0){
							for(CDeliveryWay delivery:deliveryList){
								resultMap = new HashMap<String,String>();
								resultMap.put("id", String.valueOf(delivery.getId()));
								resultMap.put("name", delivery.getName());
								resultMap.put("instro", delivery.getInstro());
								resultMap.put("isflag", "0");//表示否
								if(!paymentWay.getPaymentCode().equals("hdfk")){
									    if(delivery.getDeliveryCode().equals("ptkd")){//普通快递
										   if(receiverFlag.equals("0")||receiverFlag.equals("2")){//外地  或是北京
											   resultMap.put("isflag", "1");//表示是
										   }
										}else if(delivery.getDeliveryCode().equals("ysd")){//药士达
										   if(receiverFlag.equals("1")){//表示 望京
											   resultMap.put("isflag", "1");//表示是
										   }
										}else if(delivery.getDeliveryCode().equals("ysdj")){//药师到家
										   if(receiverFlag.equals("1")){//表示 望京
												   resultMap.put("isflag", "1");//表示是
											}
										}
								}else{
									if(receiverFlag.equals("1")){//表示 望京
										if(delivery.getDeliveryCode().equals("ysd")|| delivery.getDeliveryCode().equals("ysdj")){//药士达    药师到家
											 resultMap.put("isflag", "1");//表示是
										}
									}
								}
								resultMap.put("deliveryPrice",  NumberUtil.format2String(delivery.getInitPrice()));//价格
								deliveryWayList.add(resultMap);
							}
							statusCode="1";
						}
				}
			} catch (Exception e) {
				statusCode = "0";// 签到失败
				errorMessage ="操作失败";
				e.printStackTrace();
			}
		} else {
			statusCode = "2";// sessionId 不能为空
			errorMessage ="操作失败";
		}
		map.put("statusCode", statusCode);
		map.put("errorMessage", errorMessage);
		map.put("deliveryWayList", deliveryWayList);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
	
	
}
