package com.rc.openapi.serviceuriV5.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.service.TOrderItemManager;
import com.rc.openapi.service.TOrderManager;
import com.rc.openapi.serviceApi.OrderCommentServiceDubboApi;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.NumberUtil;
import com.rc.openapi.vo.TOrder;
import com.rc.openapi.vo.TOrderItem;
import com.rc.openapi.vo.TOrderItemExample;

/**
 * 提交商品评论,根据订单项评价
 * 
 * @author WWF
 * @createTime 2016-8-25 上午9:47:38
 */
public class SubmitCommentService extends BaseURIService {

	private static final long serialVersionUID = 900002653652007842L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultData resultData = new ResultData();
		
		String sessionId = request.getParameter("sessionId");
		String orderId = request.getParameter("orderId");//订单ID;
		String goodId = request.getParameter("productid");//商品ID;
		String content = request.getParameter("content");//评论内容
		String score = request.getParameter("score");//评分
		
		String img1 = request.getParameter("img1");// 图片1
		String img2 = request.getParameter("img2");// 图片2
		String img3 = request.getParameter("img3");// 图片3
		String img4 = request.getParameter("img4");// 图片4
		verificationForm(sessionId,"sessionId",request,response);
		if(!verificationForm(orderId,"订单ID",request,response)){
			return;
		}else if(!NumberUtil.isNumeric(orderId)){
			resultData.setStatusCode("0");
			resultData.setMessage("订单ID有误");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		if(!verificationForm(goodId,"商品ID",request,response)){
			return;
		}else if(!NumberUtil.isNumeric(goodId)){
			resultData.setStatusCode("0");
			resultData.setMessage("商品ID有误");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return;
		}
		if(!verificationForm(content,"评论内容",request,response)){
			return;
		}
		if(!verificationForm(score,"评分",request,response)){
			return;
		}else if(!NumberUtil.isNumeric(goodId)){
			score = "5";
		}
		TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		TOrderManager tordermanager = (TOrderManager) getSpringBean("tordermanager");
		TOrderItemManager torderitemmanager = (TOrderItemManager) getSpringBean("torderitemmanager");
		Long memberId = 0L;
		try {
			memberId = tmembermanager.getMemberId(sessionId);
			if(memberId<=0L){
				resultData.setStatusCode("0");
				resultData.setMessage("登录信息有误,请确认");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}
			TOrder tOrder = tordermanager.selectByPrimaryKey(Long.valueOf(orderId));
			
			if(tOrder==null||tOrder.getMemberId()==null||tOrder.getMemberId().longValue()!=memberId.longValue()){
				System.out.println(tOrder.getMemberId()+"----"+memberId);
				System.out.println(tOrder.getMemberId().longValue()!=memberId.longValue());
				resultData.setStatusCode("0");
				resultData.setMessage("订单信息有误,或非本人订单");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}
			TOrderItemExample tOrderItemExample = new TOrderItemExample();
			tOrderItemExample.createCriteria().andOrderIdEqualTo(Long.valueOf(orderId)).andGoodsIdEqualTo(Long.valueOf(goodId));
			List<TOrderItem> ll = torderitemmanager.selectByExample(tOrderItemExample);
			if(ll==null||ll.size()<=0){
				resultData.setStatusCode("0");
				resultData.setMessage("订单商品信息有误,或非本人订单");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}else if(ll!=null&&ll.size()>0&&ll.get(0).getIfReviews()!=null&&ll.get(0).getIfReviews()==1){
				resultData.setStatusCode("0");
				resultData.setMessage("该订单已评论,请勿重复评论");
				ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
				return;
			}
			//提交
			OrderCommentServiceDubboApi ordercommentservicedubboapi = (OrderCommentServiceDubboApi) getSpringBean("ordercommentservicedubboapi");
			List<String> imgList = new ArrayList<String>();
			if(img1!=null&&!"".equals(img1)){
				imgList.add(img1);
			}
			if(img2!=null&&!"".equals(img2)){
				imgList.add(img2);
			}
			if(img3!=null&&!"".equals(img3)){
				imgList.add(img3);
			}
			if(img4!=null&&!"".equals(img4)){
				imgList.add(img4);
			}
			
			try {
				ordercommentservicedubboapi.submitOrderComment(memberId, Long.valueOf(orderId), Long.valueOf(goodId),
						Integer.valueOf(score), content, null, null);
				resultData.setStatusCode("1");
				resultData.setMessage("提交成功");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				resultData.setStatusCode("0");
				resultData.setMessage("提交失败");
			} catch (Exception e) {
				e.printStackTrace();
				resultData.setStatusCode("0");
				resultData.setMessage("提交失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resultData.setStatusCode("0");
			resultData.setMessage("提交失败");
		}
		ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
		
	}
	
	private boolean verificationForm(String fieldName,String warningName,HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(fieldName==null||"".equals(fieldName)){
			ResultData resultData = new ResultData();
			resultData.setStatusCode("0");
			resultData.setMessage(warningName+"不能为空");
			ConstantUtil.reJSON(JSONObject.fromObject(resultData).toString(), request, response);
			return false;
		}else{
			return true;
		}
	}

}
