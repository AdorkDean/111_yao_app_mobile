package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.commons.util.DateUtil;
import com.rc.openapi.dubbo.model.TGoodModel;
import com.rc.openapi.dubbo.model.TOrderReturnModel;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.serviceApi.OrderReturnServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.serviceuriV5.returnModel.ReturnOrderReturnModel;
import com.rc.openapi.serviceuriV5.returnModel.ReturnTGoodModel;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.util.PageResult;

/**
 * 获取退换货列表(我的售后)
 * 
 * @author WWF
 * @createTime 2016-8-29 上午11:21:02
 */
public class GetOrderReturnService extends BaseURIService {

	private static final long serialVersionUID = -3047640756926524333L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");//sessionId
		String pageNo = request.getParameter("pageNo");//pageNo
		String pageSize = request.getParameter("pageSize");//pageSize
		
		//非必填项
		String returnStatus = request.getParameter("returnStatus");//状态
		String returnOrderNo = request.getParameter("returnOrderNo");//退换货编号
		String fromDate = request.getParameter("fromDate");//
		String toDate = request.getParameter("toDate");//
		
		Date _from = null;
		Date _to = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(sessionId==null ||"".equals(sessionId)){
			resultMap.put("statusCode", "0");
			resultMap.put("message", "sessionId为空");
			ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
			return;
		}
		if(pageNo==null||"".equals(pageNo)||!NumberUtil.isNumeric(pageNo)){
			pageNo = "0";
		}
		if(pageSize==null||"".equals(pageSize)||!NumberUtil.isNumeric(pageSize)){
			pageSize = "20";
		}
		List<Integer> orderStatusList = null;
		if(returnStatus!=null&&!"".equals(returnStatus)){
			orderStatusList = new ArrayList<Integer>();
			String[] _status = returnStatus.split(",");
			for (String string : _status) {
				if(NumberUtil.isNumeric(string)){
					orderStatusList.add(Integer.valueOf(string));
				}
			}
		}
		if(fromDate!=null&&!"".equals(fromDate)){
			try {
				_from = DateUtil.fromStringToDate("yyyy-MM-dd HH:mm:ss", fromDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
				_from = null;
			}
		}
		if(toDate!=null&&!"".equals(toDate)){
			try {
				_to = DateUtil.fromStringToDate("yyyy-MM-dd HH:mm:ss", toDate);
			} catch (ParseException e1) {
				e1.printStackTrace();
				_to = null;
			}
		}
		
		TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		OrderReturnServiceDubboApi orderreturnservicedubboapi = (OrderReturnServiceDubboApi) getSpringBean("orderreturnservicedubboapi");
		
		Long memberId = 0L;
		try {
			memberId = tmembermanager.getMemberId(sessionId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(memberId==null||memberId<=0L){
			resultMap.put("statusCode", "0");
			resultMap.put("message", "登录信息有误,请确认");
			ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);
			return;
		}
		PageWraper pw = new PageWraper();
		PageResult rs = new PageResult();
		rs.setP_curPage(Integer.parseInt(pageNo)); // 第一页数据
		rs.setP_pageSize(Integer.parseInt(pageSize)); // 第页显示2条，这里不设置，默认为20条
		
		List<Integer> serviceTypeList = new ArrayList<Integer>();
		serviceTypeList.add(0);
		serviceTypeList.add(2);
		try {
			pw = orderreturnservicedubboapi.getOrderReturn(memberId.longValue(), returnOrderNo, orderStatusList, _from, _to, Integer.valueOf(pageNo), Integer.valueOf(pageSize),serviceTypeList);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<ReturnOrderReturnModel> result = new ArrayList<ReturnOrderReturnModel>();
		
		
		List<TOrderReturnModel> returnList = pw.getResult();
		ReturnOrderReturnModel newReturn = null;
		List<ReturnTGoodModel> newGoodsList = null;
		for (TOrderReturnModel tOrderReturnModel : returnList) {
			newReturn = new ReturnOrderReturnModel();
			newReturn.setCreateTime(tOrderReturnModel.getCreateTime()==null?"":sdf.format(tOrderReturnModel.getCreateTime()));
			newReturn.setExpressCompany(tOrderReturnModel.getExpressCompany());
			newReturn.setExpressDelivery(tOrderReturnModel.getExpressDelivery());
			newReturn.setFinishDt(tOrderReturnModel.getFinishDt()==null?"":sdf.format(tOrderReturnModel.getFinishDt()));
			newReturn.setId(tOrderReturnModel.getId()==null?"":tOrderReturnModel.getId().toString());
			newReturn.setMemberId(tOrderReturnModel.getMemberId()==null?"":tOrderReturnModel.getMemberId().toString());
			newReturn.setOldOrderId(tOrderReturnModel.getOldOrderId()==null?"":tOrderReturnModel.getOldOrderId().toString());
			newReturn.setOrderSn(tOrderReturnModel.getOrderSn());
			newReturn.setOrderStatus(tOrderReturnModel.getOrderStatus()==null?"":tOrderReturnModel.getOrderStatus().toString());
			newReturn.setRefundAccount(tOrderReturnModel.getRefundAccount());
			newReturn.setRefundAmount(tOrderReturnModel.getRefundAmount()==null?"":tOrderReturnModel.getRefundAmount().toString());
			newReturn.setRefundBank(tOrderReturnModel.getRefundBank());
			newReturn.setRefundDescribe(tOrderReturnModel.getRefundDescribe());
			newReturn.setRefundRemark(tOrderReturnModel.getRefundRemark());
			newReturn.setServiceType(tOrderReturnModel.getServiceType()==null?"":tOrderReturnModel.getServiceType().toString());
			newReturn.setShipperPhone(tOrderReturnModel.getShipperPhone());
			
			newGoodsList = new ArrayList<ReturnTGoodModel>();
			List<TGoodModel> subList = tOrderReturnModel.getGoodsList();
			ReturnTGoodModel returnTGoodModel = null;
			for (TGoodModel tGoodModel : subList) {
				returnTGoodModel = new ReturnTGoodModel();
				returnTGoodModel.setAbbreviationPicture(tGoodModel.getAbbreviationPicture());
				returnTGoodModel.setGoodsid(tGoodModel.getGoodsid()==null?"":tGoodModel.getGoodsid().toString());
				returnTGoodModel.setGoodsName(tGoodModel.getGoodsName());
				returnTGoodModel.setPrice(tGoodModel.getPrice()==null?"":tGoodModel.getPrice().toString());
				returnTGoodModel.setQuantity(tGoodModel.getQuantity()==null?"":tGoodModel.getQuantity().toString());
				returnTGoodModel.setRefundProcess(tGoodModel.getRefundProcess()==null?"":tGoodModel.getRefundProcess().toString());
				returnTGoodModel.setShortName(tGoodModel.getShortName());
				returnTGoodModel.setSpec(tGoodModel.getSpec());
				returnTGoodModel.setSumPrice(returnTGoodModel.getSumPrice());
				newGoodsList.add(returnTGoodModel);
			}
			newReturn.setGoodsList(newGoodsList);
			result.add(newReturn);
		}
		pw.setResult(result);
		resultMap.put("statusCode", "1");
		resultMap.put("message", "");
		resultMap.put("itemList", pw);
		ConstantUtil.reJSON(JSONObject.fromObject(resultMap).toString(), request, response);		
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public static void main(String[] args) throws ParseException {
		Date fromDate =DateUtil.fromStringToDate("yyyy-MM-dd HH:mm:ss", "hhjj-9979-00");
		System.out.println(fromDate);
	}

}
