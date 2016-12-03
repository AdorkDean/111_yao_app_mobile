package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.service.TOrderItemManager;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.service.TReturnManager;
import com.rc.openapi.serviceApi.OrderReturnServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;
import com.rc.openapi.vo.TOrderItem;
import com.rc.openapi.vo.TOrderItemExample;
import com.rc.openapi.vo.TReturn;
import com.rc.openapi.vo.TReturnExample;
import com.rc.openapi.vo.TReturnItem;

/**
 * 退换货提交接口
 * @author WWF
 * @createTime 2016-8-24 上午11:53:12
 */
public class SubmitOrderReturnService extends BaseURIService {

	private static final long serialVersionUID = 646102189335073532L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultData resultData = new ResultData();
		OrderReturnServiceDubboApi orderreturnservicedubboapi = (OrderReturnServiceDubboApi) getSpringBean("orderreturnservicedubboapi");
		TOrderManager tordermanager = (TOrderManager) getSpringBean("tordermanager");
		TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		TOrderItemManager torderitemmanager = (TOrderItemManager) getSpringBean("torderitemmanager");
		OpenSqlManage openSqlManage = (OpenSqlManage) getSpringBean("opensqlmanage");
//		TReturnItemManager treturnitemmanager = (TReturnItemManager) getSpringBean("treturnitemmanager");
		TReturnManager treturnmanager = (TReturnManager) getSpringBean("treturnmanager");
		
		String sessionid = request.getParameter("sessionId");
		String orderId = request.getParameter("orderId");
		String returnType = request.getParameter("serviceType");//退换货类型(0:退货退款,2:仅退款);
		String returnRemark = request.getParameter("content");//说明
		String returnParam = request.getParameter("products");//商品信息
		String img1 = request.getParameter("img1");// 图片1
		String img2 = request.getParameter("img2");// 图片2
		String img3 = request.getParameter("img3");// 图片3
		String img4 = request.getParameter("img4");// 图片4
		Long memeberId = null;
		
		//各种判断为空
		if(sessionid==null||"".equals(sessionid)){
			resultData.setStatusCode("0");
			resultData.setMessage("登录信息有误");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}else{
			try {
				memeberId = tmembermanager.getMemberId(sessionid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(memeberId==null||memeberId<=0L){
				resultData.setStatusCode("0");
				resultData.setMessage("登录信息有误");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}
		}
		if(orderId==null||"".equals(orderId)){
			resultData.setStatusCode("0");
			resultData.setMessage("订单信息有误");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		if(returnType==null||"".equals(returnType)){
			resultData.setStatusCode("0");
			resultData.setMessage("退换货类型不能为空");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		if(returnRemark==null||"".equals(returnRemark)){
			resultData.setStatusCode("0");
			resultData.setMessage("退换货说明不能为空");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		if(returnParam==null||"".equals(returnParam)){
			resultData.setStatusCode("0");
			resultData.setMessage("退换货商品信息有误.");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		
		Long _orderId = Long.valueOf(orderId);
		if(_orderId<0){
			resultData.setMessage("订单信息有误");
			resultData.setStatusCode("0");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		TOrder tOrder = null;
		try {
			TOrderExample tOrderExample = new TOrderExample();
			tOrderExample.createCriteria().andIdEqualTo(_orderId).andMemberIdEqualTo(memeberId);
			tOrder = tordermanager.selectByPrimaryKey(_orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(tOrder==null){
			resultData.setMessage("订单信息有误");
			resultData.setStatusCode("0");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		if(tOrder.getOrderStatus()==null||(tOrder.getOrderStatus()!=1&&tOrder.getOrderStatus()!=2&&tOrder.getOrderStatus()!=3)){
			resultData.setMessage("该订单不符合退换货状态");
			resultData.setStatusCode("0");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		
		
		TReturnExample tReturnExample = new TReturnExample();
		tReturnExample.createCriteria().andOldOrderIdEqualTo(_orderId).andMemberIdEqualTo(tOrder.getMemberId());
		List<TReturn> returnList = null;
		try {
			returnList = treturnmanager.selectByExample(tReturnExample);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//判断是否存在已退换货的;
		if(returnList!=null&&returnList.size()>0){//多次退换货
			String str[] = returnParam.split(":");
			Map<Long, Integer> goodsQu = new HashMap<Long, Integer>();
			List<Long> goodIdsList = new ArrayList<Long>();
			for (String string : str) {
				String[] goodQuantity = string.split(",");
				if(goodQuantity!=null&&goodQuantity.length>1){
					if("2".equals(returnType)){//仅退款,数量查订单项的;
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("goodsId", Long.valueOf(goodQuantity[0]));
						param2.put("orderId", _orderId);
						Integer ll = (Integer) openSqlManage.selectForObjectByMap(param2, "t_order_item.selectQuantityByGoodIdOrder");
						System.out.println("查询订单项信息;");
						goodsQu.put(Long.valueOf(goodQuantity[0]), ll==null?1:ll);
					}else{
						goodsQu.put(Long.valueOf(goodQuantity[0]), Integer.valueOf(goodQuantity[1]));
					}
					goodIdsList.add(Long.valueOf(goodQuantity[0]));
				}
			}
			Map<String, Object> param1 = new HashMap<String, Object>();
			param1.put("id", returnList.get(0).getId());
			param1.put("goodsIds", goodIdsList);
			
			List<TReturnItem> itemsList = openSqlManage.selectForListByMap(param1, "t_return_item.selectDataByMap");
			if(itemsList!=null&&itemsList.size()>0){
				resultData.setMessage("所选商品有已退换商品");
				resultData.setStatusCode("0");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}
		}
		
		try {
			TOrderItemExample tOrderItemExample = new TOrderItemExample();
			tOrderItemExample.createCriteria().andOrderIdEqualTo(_orderId);
			List<TOrderItem> itemList = torderitemmanager.selectByExample(tOrderItemExample);
			List<Long> allItem = new ArrayList<Long>();
			for (TOrderItem tOrderItem : itemList) {
				allItem.add(tOrderItem.getGoodsId());
			}
			
			//登录者的订单;
			//判断,退换货商品信息
			String str[] = returnParam.split(":");
			List<Long> goodsIds = new ArrayList<Long>();
			Map<Long,Integer> paramMap = new HashMap<Long,Integer>();
			
			for (String string : str) {
				String[] goodQuantity = string.split(",");
				if(goodQuantity!=null&&goodQuantity.length>1){
					if(allItem.contains(Long.valueOf(goodQuantity[0]))){
						if("2".equals(returnType)){//仅退款,数量查订单项的;
							Map<String, Object> param2 = new HashMap<String, Object>();
							param2.put("goodsId", Long.valueOf(goodQuantity[0]));
							param2.put("orderId", _orderId);
							Integer ll = (Integer) openSqlManage.selectForObjectByMap(param2, "t_order_item.selectQuantityByGoodIdOrder");
							paramMap.put(Long.valueOf(goodQuantity[0]), ll==null?1:ll);
						}else{
							paramMap.put(Long.valueOf(goodQuantity[0]), Integer.valueOf(goodQuantity[1]));
						}
						goodsIds.add(Long.valueOf(goodQuantity[0]));
					}
				}
			}
			
			if(goodsIds!=null&&goodsIds.size()>0){
				List<String> imgList = new ArrayList<String>();
				if(img1!=null){
					imgList.add(img1);
				}
				if(img2!=null){
					imgList.add(img2);
				}
				if(img3!=null){
					imgList.add(img3);
				}
				if(img4!=null){
					imgList.add(img4);
				}
				try {
					boolean _flag = orderreturnservicedubboapi.submitReturnInfo("app",_orderId, returnType, returnRemark, imgList, null, paramMap);
					if(_flag){
						resultData.setMessage("提交成功");
						resultData.setStatusCode("1");
					}else{
						resultData.setMessage("提交失败");
						resultData.setStatusCode("0");
					}
				} catch (Exception e) {
					e.printStackTrace();
					resultData.setMessage("提交失败");
					resultData.setStatusCode("0");
				}
			}else{
				resultData.setMessage("退换货商品信息有误");
				resultData.setStatusCode("0");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
		return;
	}
	
	
	public class ResultData{
		private String statusCode;
		private String message;
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
	
}
