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
 * 用户积分   线上线下  综合列表
 * @author user3
 *
 */
public class MemberPointListService extends BaseURIService{

	private static final long serialVersionUID = 2198503165848412507L;
	
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
		String onlinePoint ="0";//线上积分
		String offlinePoint="0";//线下积分
		if(StringUtils.hasText(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionId);
				if(userId!=-1l){
					TMember member = tmembermanager.selectByPrimaryKey(userId);
					if(member!=null){
						if(member.getIntegral()!=null){
							onlinePoint =String.valueOf(member.getIntegral());
						}
								TSysParameterManager sysParameter = (TSysParameterManager) getSpringBean("tsysparametermanager");
								Map<String,String> publicMap = new HashMap<String,String>();
								publicMap.put("memberId", String.valueOf(member.getId()));
								String homePageConfigId = sysParameter.getKeys("public_service_url");
								String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getJifenOutline");
								// 这里 根据小魏返回内容处理
								JSONObject resultJson = JSONObject.fromObject(resultJsonstr);
								if(resultJson.get("statusCode").equals("1")){
									offlinePoint = String.valueOf(resultJson.get("jifen"));
								}
								String getThirtyJifenJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getThirtyJifen");
								JSONObject getThirtyJifenResultJson = JSONObject.fromObject(getThirtyJifenJsonstr);
								List<Map<String,String>> pointList = new ArrayList<Map<String,String>>();
								if(getThirtyJifenResultJson.get("statusCode").equals("1")){
									JSONArray resultArray = getThirtyJifenResultJson.getJSONArray("list");
									List<Map<String, Object>> _resultList = (List<Map<String, Object>>) JSONArray.toCollection(resultArray, Map.class);
									if(_resultList!=null&&_resultList.size()>0){
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
										for(Map<String, Object> resultMap_hd:_resultList){
											pointMap = new HashMap<String,String>();
											pointMap.put("pointId", String.valueOf(resultMap_hd.get("id")==null?"0":resultMap_hd.get("id")));//消费记录id
											pointMap.put("integral", String.valueOf(resultMap_hd.get("integral")));//消费分数
											pointMap.put("createDate",  String.valueOf(resultMap_hd.get("create_date")));//消费时间
											if("0".equals(resultMap_hd.get("id"))){//是否海典
												if(Double.valueOf(String.valueOf(resultMap_hd.get("integral")))>0){
													pointMap.put("pointType", "1");//赠送 
												}else{
													pointMap.put("pointType", "2");//消费
												}
												pointMap.put("remark", String.valueOf(resultMap_hd.get("remark")==null?"":resultMap_hd.get("remark")));//消费备注
											}else{
												if(String.valueOf(resultMap_hd.get("source")).equals("10")||String.valueOf(resultMap_hd.get("source")).equals("11")){
													pointMap.put("pointType", "2");//消费
												}else{
													pointMap.put("pointType", "1");//赠送
												}
//												if(resultMap_hd.get("remark")!=null&&!"".equals(resultMap_hd.get("remark"))){
//													pointMap.put("remark", String.valueOf(resultMap_hd.get("remark")));//消费备注
//												}else{
													pointMap.put("remark", pointRemarkMap.get(String.valueOf(resultMap_hd.get("source"))));//消费备注
//												}
											}
											pointList.add(pointMap);
										}
									}
								}
								resultmap.put("pointList",pointList);//优惠券列表
								statusCode = "1";// 成功
						
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
		resultmap.put("statusCode", statusCode);
		resultmap.put("errorMessage", errorMessage);
		resultmap.put("onlinePoint", onlinePoint);//线上积分
		resultmap.put("offlinePoint", offlinePoint);//线下积分
		resultmap.put("pointSum", String.valueOf(Integer.valueOf(onlinePoint)+Integer.valueOf(offlinePoint)));//总分数
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

}
