package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.CPaymentWayManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.service.impl.TLeaderStayMoneyManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.CPaymentWay;
import com.rc.openapi.vo.TLeaderStayMoney;
import com.rc.openapi.vo.TLeaderStayMoneyExample;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;
/**
 * 取消订单
 * @author Administrator
 *
 */
public class UpdateOrderStatusService extends BaseURIService {

	private static final long serialVersionUID = 4410296124974362142L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String ordersn =request.getParameter("ordersn");
		Map map = new HashMap();
		if(null != sessionId && !("").equals(sessionId)&&null != ordersn && !("").equals(ordersn)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
			Long userId;
			try {
				userId = tmembermanager.getMemberId(sessionId);
				if(null != userId && userId != -1){
					TOrderManager tordermanager = (TOrderManager)getSpringBean("tordermanager");
					TOrderExample tOrderExample = new TOrderExample();
					tOrderExample.createCriteria().andOrderSnEqualTo(ordersn);
					List<TOrder> list = tordermanager.selectByExample(tOrderExample);
					TOrder tOrder = null;
					boolean flag = false;
					//FIXME Wangming();1:判断订单是否已支付;2:是否是秀粉的订单;3:给领袖添加待入账流水;
					
					if(list!=null && list.size()==1){
						//1,：判断是否已支付；
						tOrder = list.get(0);
						if(tOrder.getPaymentId()!=null ){
							CPaymentWayManager cpaymentwaymanager = (CPaymentWayManager)getSpringBean("cpaymentwaymanager");
							CPaymentWay cPaymentWay = cpaymentwaymanager.selectByPrimaryKey(tOrder.getPaymentId());
							if(cPaymentWay!=null && cPaymentWay.getPaymentCode()!=null && "xszf".equals(cPaymentWay.getPaymentCode())){
								//支付过的，
								flag = true;
							}
						}
					}
					
					tordermanager.cancelOrder(userId, ordersn);
					
					//支付过的
					if(flag && tOrder!=null && tOrder.getMemberId()!=null && tOrder.getOrderStatus()!=null && (tOrder.getOrderStatus()==1||tOrder.getOrderStatus()==2)){//支付过得
						//2：判断是否是秀粉订单；
						OpenSqlManage opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
						Map<String, Object> _paramMap = new HashMap<String, Object>();
						_paramMap.put("memberId", tOrder.getMemberId());//订单用户
						
						Map<String, Object> phoneMap = (Map<String, Object>) opensqlmanage.selectForObjectByMap(
								_paramMap, "t_member_leader.selectLeaderPhoneByXiufenId");
						//查出来的memberId是领袖的id
						
						TLeaderStayMoneyManagerImpl tleaderstaymoneymanager = (TLeaderStayMoneyManagerImpl)getSpringBean("tleaderstaymoneymanager");
						TLeaderStayMoneyExample _tTLeaderStayMoneyExample = new TLeaderStayMoneyExample(); 
						_tTLeaderStayMoneyExample.createCriteria().andTypeEqualTo(1).andOrderIdEqualTo(tOrder.getId());
						List<TLeaderStayMoney> _lisTLeaderStayMoneys = tleaderstaymoneymanager.selectByExample(_tTLeaderStayMoneyExample);
						if(_lisTLeaderStayMoneys!=null && _lisTLeaderStayMoneys.size()>0){
							tleaderstaymoneymanager.cancelOrderInsertStayByHDF(phoneMap,tOrder);
						}
					}
					map.put("statusCode", "1");
				}else{
					//无效用户
					map.put("statusCode", "2");
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.put("statusCode", "0");
			}
		}else{
		    //参数为空
			map.put("statusCode", "3");
		}
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
