package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TMember;
/**
 * 线上积分列表
 * @author user3
 *
 */
public class MemberOnLinePointListService extends BaseURIService{
	private static final long serialVersionUID = 1968451508728171255L;
   
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		Map<String, Object> resultmap = new HashMap<String, Object>();
		String statusCode = "";
		String errorMessage ="";
		String pageNo = request.getParameter("pageNo");//当前页
		String pageSize = request.getParameter("pageSize");//每页显示多少条数据
		if(!StringUtils.hasText(pageNo)){
			pageNo="1";
		}
		if(!StringUtils.hasText(pageSize)){
			pageSize="10";
		}
		if(StringUtils.hasText(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionId);
				if(userId!=-1l){
					TMember member = tmembermanager.selectByPrimaryKey(userId);
					if(member!=null){
						TSysParameterManager sysParameter = (TSysParameterManager) getSpringBean("tsysparametermanager");
						Map<String,String> publicMap = new HashMap<String,String>();
						publicMap.put("memberId", String.valueOf(member.getId()));
						publicMap.put("pageNo", pageNo);
						publicMap.put("pageSize", pageSize);
						String homePageConfigId = sysParameter.getKeys("public_service_url");
						String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getIntegralOnLine");
						JSONObject resultJson = JSONObject.fromObject(resultJsonstr);
						if(resultJson.get("statusCode").equals("1")){
							JSONObject data = resultJson.getJSONObject("data");
							JSONObject pageInfo = data.getJSONObject("pageInfo");
							resultmap.put("pagesum", String.valueOf(pageInfo.get("pages")));//总页数
							resultmap.put("count", String.valueOf(pageInfo.get("count")));//总条数
							JSONArray resultArray = data.getJSONArray("result");
							List<Map<String, Object>> _resultList = (List<Map<String, Object>>) JSONArray.toCollection(resultArray, Map.class);
							if(_resultList!=null&&_resultList.size()>0){
								List<Map<String,String>> pointList = new ArrayList<Map<String,String>>();
								Map<String,String> pointMap = null;
								Map<String,String> pointRemarkMap = new HashMap<String,String>();
								pointRemarkMap.put("1", "注册赠送");
								pointRemarkMap.put("2", "完善昵称赠送");
								pointRemarkMap.put("3", "完善真实姓名赠送");
								pointRemarkMap.put("4", "完善性别赠送");
								pointRemarkMap.put("5", "完善血型赠送");
								pointRemarkMap.put("6", "完善病历赠送");
								pointRemarkMap.put("7", "评论赠送");
								pointRemarkMap.put("8", "签到赠送");
								pointRemarkMap.put("9", "下单购买完成赠送");
								pointRemarkMap.put("10", "兑换优惠券");
								pointRemarkMap.put("11", "线下消费");
								for(Map<String,Object> resultMapJson:_resultList){
									pointMap = new HashMap<String,String>();
									
									pointMap.put("pointId", String.valueOf(resultMapJson.get("id")));//消费记录id
									pointMap.put("integral", String.valueOf(resultMapJson.get("integral")));//消费分数
									pointMap.put("createDate", String.valueOf(resultMapJson.get("createDate")));//消费时间
//									1 注册 2完善用户名 3完善真实姓名 4完善性别 5完善血型  6完善会员病历(常用药、病史、过敏史) 7 评论 8 签到  9订单完成  10兑换优惠券   11海典消分
									if(String.valueOf(resultMapJson.get("source")).equals("10")||String.valueOf(resultMapJson.get("source")).equals("11")){
										pointMap.put("pointType", "2");//消费
									}else{
										pointMap.put("pointType", "1");//赠送
									}
//									if(resultMapJson.get("remark")!=null&&!"".equals(resultMapJson.get("remark"))){
//										pointMap.put("remark", String.valueOf(resultMapJson.get("remark")));//消费备注
//									}else{
										pointMap.put("remark", pointRemarkMap.get(String.valueOf(resultMapJson.get("source"))));//消费备注
//									}
									pointList.add(pointMap);
								}
								resultmap.put("pointList",pointList);//优惠券列表
							}
							statusCode = "1";// 成功
						}else{
							statusCode = "0";// 失败
							errorMessage="获取数据失败";
						}
					}else{
						statusCode = "3";// 非法用户
						errorMessage ="非法请求";
					}
				}else{
					statusCode = "3";// 非法用户
					errorMessage ="非法请求";
				}
				
			} catch (Exception e) {
				statusCode = "0";// 
				errorMessage ="服务器异常";
				e.printStackTrace();
			}
		}else{
			statusCode = "2";// sessionId 不能为空
			errorMessage ="非法请求";
		}
		resultmap.put("pageNo", pageNo);//当前页数
		resultmap.put("pageSize", pageSize);//每页条数
		resultmap.put("statusCode", statusCode);
		resultmap.put("errorMessage", errorMessage);
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
	
}
