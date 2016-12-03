package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
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

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.model.PositionVO;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.service.impl.TMemberIntegralManagerImpl;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberIntegral;

/**
 * 签到成功
 * @author user3
 *
 */
public class SignSuccessService extends BaseURIService{

	private static final long serialVersionUID = 5459701844341684273L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");// 用户Key
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = "";
		String pointSum ="";
		String onlinePoint ="0";//线上积分
		String offlinePoint="0";//线下积分
		String tomorrowPoint="5";
		List<PositionVO> list = new ArrayList<PositionVO>();
		if(StringUtils.hasText(sessionId)){
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
			try {
				Long userId = tmembermanager.getMemberId(sessionId);
				if(userId!=-1l){
					TMember member = tmembermanager.selectByPrimaryKey(userId);
					if(member!=null){
						onlinePoint =String.valueOf(member.getIntegral());
						TSysParameterManager sysParameter = (TSysParameterManager) getSpringBean("tsysparametermanager");
						Map<String,String> publicMap = new HashMap<String,String>();
						publicMap.put("memberId", String.valueOf(member.getId()));
						String homePageConfigId = sysParameter.getKeys("public_service_url");
						String resultJsonstr;
						try {
							resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId+"getJifenOutline");
							JSONObject resultJson = JSONObject.fromObject(resultJsonstr);
							if(resultJson.get("statusCode").equals("1")){
								offlinePoint = String.valueOf(resultJson.get("jifen"));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						OpenSqlManageImpl opensqlmanage =(OpenSqlManageImpl) getSpringBean("opensqlmanage");
						Map<String,Object> paramMap= new HashMap<String,Object>();
						paramMap.put("channel", Integer.valueOf(InfoUtil.getInstance().getInfo("config", "sign_positionId")));
//						list =opensqlmanage.selectForListByMap(paramMap, "c_position.select_PositionVO");
						
						TMemberIntegralManagerImpl tmemberintegralmanager = (TMemberIntegralManagerImpl) getSpringBean("tmemberintegralmanager");
						SimpleDateFormat yyyy = new SimpleDateFormat("yyyy-MM-dd");
						List<TMemberIntegral> singList = tmemberintegralmanager.selectByUserIdDate(
								userId.intValue(), yyyy.format(new Date()));
						int sign_point_jiange =Integer.valueOf(InfoUtil.getInstance().getInfo("config", "user.sign_point_jiange"));
						int sign_point =Integer.valueOf(InfoUtil.getInstance().getInfo("config", "user.sign_point"));
						if(singList!=null&&singList.size()>0){
							tomorrowPoint=String.valueOf(singList.get(0).getIntegral()+sign_point_jiange);
							if(singList.get(0).getIntegral()==(sign_point+sign_point_jiange*6)){
								tomorrowPoint=String.valueOf(singList.get(0).getIntegral());
							}
						}
						statusCode="1";
					}else{
						statusCode = "3";// 非法用户
					}
				}else{
					statusCode = "3";// 非法用户
				}
			} catch (SQLException e) {
				statusCode = "0";// 
				e.printStackTrace();
			}
		}else{
			statusCode = "2";// sessionId 不能为空
		}
		map.put("onlinePoint", onlinePoint);//线上积分
		map.put("offlinePoint", offlinePoint);//线下积分
		map.put("pointSum", String.valueOf(Integer.valueOf(onlinePoint)+Integer.valueOf(offlinePoint)));
		map.put("statusCode", statusCode);
		//map.put("tomorrowPoint", tomorrowPoint);
		map.put("tomorrowPoint", "1");
		map.put("list", list);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
}
