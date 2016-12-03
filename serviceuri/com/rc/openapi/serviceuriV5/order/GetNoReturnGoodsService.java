package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.dubbo.vo.TOrderItem;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.serviceApi.OrderCommentServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.serviceuriV5.order.model.ResultOrderItem;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderExample;

/**
 * 取能退换货的商品
 * 
 * @author WWF
 * @createTime 2016-8-25 上午11:49:40
 */
public class GetNoReturnGoodsService extends BaseURIService {

	private static final long serialVersionUID = 5850590826985771244L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String orderId = request.getParameter("orderId");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		TOrderManager tordermanager = (TOrderManager) getSpringBean("tordermanager");
		OrderCommentServiceDubboApi ordercommentservicedubboapi = (OrderCommentServiceDubboApi) getSpringBean("ordercommentservicedubboapi");
		Long memberId = null;
		if (sessionId == null || "".equals(sessionId)) {
			resultMap.put("statusCode", "0");
			resultMap.put("message", "sessionId为空");
			ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
			return;
		}else{
			try {
				memberId = tmembermanager.getMemberId(sessionId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(memberId==null||memberId<=0L){
				resultMap.put("statusCode", "0");
				resultMap.put("message", "登录信息有误!");
				ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
				return;
			}
		}
		if(orderId==null||"".equals(orderId)||!NumberUtil.isNumeric(orderId)){
			resultMap.put("statusCode", "0");
			resultMap.put("message", "订单ID有误");
			ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
			return;
		}
		TOrderExample tOrderExample = new TOrderExample();
		tOrderExample.createCriteria().andIdEqualTo(Long.valueOf(orderId)).andMemberIdEqualTo(Long.valueOf(memberId));
		List<TOrder> orderList = null;
		List<ResultOrderItem> resultList = new ArrayList<ResultOrderItem>();
		try {
			orderList = tordermanager.selectByExample(tOrderExample);
			if(orderList==null||orderList.size()<=0){
				resultMap.put("statusCode", "0");
				resultMap.put("message", "订单信息有误,或非本账号订单");
				ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
				return;
			}
			List<TOrderItem> itemList = ordercommentservicedubboapi.getNoReturnGoods(Long.valueOf(orderId),memberId);
			ResultOrderItem resultOrderItem = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (TOrderItem tOrderItem : itemList) {
				resultOrderItem = new ResultOrderItem();
				resultOrderItem.setBrokerage(tOrderItem.getBrokerage()==null?"":tOrderItem.getBrokerage().toString());
				resultOrderItem.setCreateTime(tOrderItem.getCreateTime()==null?"":sdf.format(tOrderItem.getCreateTime()));
				resultOrderItem.setGoodsId(tOrderItem.getGoodsId()==null?"":tOrderItem.getGoodsId().toString());
				resultOrderItem.setId(tOrderItem.getId()==null?"":tOrderItem.getId().toString());
				resultOrderItem.setIfPremiums(tOrderItem.getIfPremiums()==null?"":tOrderItem.getIfPremiums().toString());
				resultOrderItem.setIfReviews(tOrderItem.getIfReviews()==null?"":tOrderItem.getIfReviews().toString());
				resultOrderItem.setOrderId(orderId);
				resultOrderItem.setPrice(tOrderItem.getPrice()==null?"":tOrderItem.getPrice().toString());
				resultOrderItem.setQuantity(tOrderItem.getQuantity()==null?"":tOrderItem.getQuantity().toString());
				resultOrderItem.setRefundProcess(tOrderItem.getRefundProcess()==null?"":tOrderItem.getRefundProcess().toString());
				resultList.add(resultOrderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultMap.put("statusCode", "1");
		resultMap.put("message", "");
		resultMap.put("itemList", resultList);
		ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
