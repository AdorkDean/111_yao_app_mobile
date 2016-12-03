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
 * 线下积分列表
 * @author user3
 *
 */
public class MemberOffLinePointListService extends BaseURIService {

	private static final long serialVersionUID = -3422686255901206214L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
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
		Map<String, Object> resultmap = new HashMap<String, Object>();
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
								String resultJsonstr =ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getIntegralOutLine");
								JSONObject resultJson = JSONObject.fromObject(resultJsonstr);
								if(resultJson.get("statusCode").equals("1")){
									JSONObject data = resultJson.getJSONObject("data");
									resultmap.put("pagesum", String.valueOf(data.get("pageSize")));//总页数
									resultmap.put("count", String.valueOf(data.get("count")));//总条数
									JSONArray resultArray = data.getJSONArray("list");
									List<Map<String, Object>> _resultList = (List<Map<String, Object>>) JSONArray.toCollection(resultArray, Map.class);
									List<Map<String,String>> pointList = new ArrayList<Map<String,String>>();
									if(_resultList!=null&&_resultList.size()>0){
										Map<String,String> pointMap = null;
										for(Map<String,Object> resultMapJson:_resultList){
											pointMap = new HashMap<String,String>();
											pointMap.put("pointId", "0");//消费记录id
											pointMap.put("integral", String.valueOf(resultMapJson.get("integral")));//消费分数
											pointMap.put("createDate", String.valueOf(resultMapJson.get("create_date")));//消费时间
											
											if(Double.valueOf(String.valueOf(resultMapJson.get("integral")))>0){
												pointMap.put("pointType", "1");//赠送 
											}else{
												pointMap.put("pointType", "2");//消费
											}
											pointMap.put("remark", String.valueOf(resultMapJson.get("remark")));//消费备注
											pointList.add(pointMap);
										}
									}
									resultmap.put("pointList",pointList);//优惠券列表
									statusCode = "1";// 成功
								}else{
									statusCode = "0";// 失败
									errorMessage="获取数据失败";
								}
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
		resultmap.put("pageNo", pageNo);//当前页数
		resultmap.put("pageSize", pageSize);//每页条数
		resultmap.put("statusCode", statusCode);
		resultmap.put("errorMessage", errorMessage);
		JSONObject json4 = JSONObject.fromObject(resultmap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
}
