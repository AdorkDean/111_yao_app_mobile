package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TGoodsCommentManager;
import com.rc.openapi.service.TGoodsManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TGoods;
import com.rc.openapi.vo.TGoodsComment;
import com.rc.openapi.vo.TGoodsExample;

/**
 * 门店订单评论
 * @author Administrator
 *
 */
public class StoreOrderCommentService extends BaseURIService {

	private static final long serialVersionUID = 8312396122974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String statusCode = "评论成功";
		String message = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map map = new HashMap();
		String sessionId = request.getParameter("sessionId");
		String orderSn = request.getParameter("orderSn");
		String content = request.getParameter("content");
		try {
			if(null !=sessionId && !("").equals(sessionId)){
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionId);
				if(null !=userId && !("").equals(userId) && null !=orderSn && !("").equals(orderSn)){
					Map<String, String> publicMap = new HashMap<String, String>();
					publicMap.put("member_id", userId.toString());
					publicMap.put("order_id", orderSn);
					TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
					String homePageConfigId = sys.getKeys("public_service_url");
					//String homePageConfigId="http://public.111yao.com/sltRouter?method=";
					String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
							+ "getOffLineOrderItemList");  //调用public接口
					JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
					if (jsonObject != null) {
						System.out.println(jsonObject);
						statusCode = jsonObject.get("statusCode") == null ? "0" : jsonObject.get("statusCode").toString();// 成功
						message = jsonObject.get("message") == null ? "" : jsonObject.get("message").toString();
						if(!statusCode.equals("0")){
							resultMap = (Map<String, Object>) JSONObject.toBean(JSONObject.fromObject(jsonObject.get("order")),
									Map.class);
							JSONArray jsonArray=jsonObject.getJSONArray("list");
							List list = JSONArray.toList(jsonArray, Map.class);
							TGoodsManager  tgoodsmanager = (TGoodsManager) getSpringBean("tgoodsmanager");
							TGoodsCommentManager tgoodscommentmanager = (TGoodsCommentManager) getSpringBean("tgoodscommentmanager");
							Map goodsMap = null;
							for (int i = 0; i < list.size(); i++) {
								TGoodsExample example = new TGoodsExample();
								goodsMap = (Map) list.get(i);
								String no = (String) goodsMap.get("orderItemSn");
								example.createCriteria().andGoodsnoEqualTo(no);
								List gList = tgoodsmanager.selectByExample(example);
								if(null !=gList && gList.size()>0){
									TGoods goods = (TGoods) gList.get(0);
									TGoodsComment comment = new TGoodsComment(); 
									comment.setSumFraction(20);
									comment.setType(1);
									comment.setSeller(5);
									comment.setGoods(5);
									comment.setFastMail(5);
									comment.setFastMailPeople(5);
									comment.setIsShow(0);
									comment.setMemberId(new Long(userId));
									comment.setGoodsId(goods.getId());
									comment.setOrderId(-1L);
									comment.setCreateTime(new Date());
									comment.setComment(content);
									tgoodscommentmanager.insertSelective(comment);
								}
							} 
							statusCode = "评论成功";
						}else{
							statusCode = message;
						}	
					}
				}else{
					statusCode="参数错误";
				}
			}else{
				statusCode="参数错误";
			}
		} catch (Exception e) {
			statusCode = "系统异常";
			e.printStackTrace();
		}
		ConstantUtil.reJSON(statusCode, request, response);
	}
	
}
