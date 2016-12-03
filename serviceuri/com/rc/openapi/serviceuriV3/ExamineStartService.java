package com.rc.openapi.serviceuriV3;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberSessionManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.vo.CVersion;
import com.rc.openapi.vo.TMemberSessionExample;
/**
 * 版本控制
 * @author Administrator
 *
 */
public class ExamineStartService extends BaseURIService {
	
	
	private final Logger log = Logger.getLogger(getClass());
	
	private OpenSqlManage opensqlmanage;

	public void doPost(HttpServletRequest request, HttpServletResponse response){
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		response.setContentType("text/html;charset=utf-8");
		  
		opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
		PrintWriter out = null;
		String statusCode = "1";
		Map map = new HashMap();
		try{
			out = response.getWriter();
			String versionId = request.getParameter("version");
			String client_type = request.getParameter("client_type");
			String type = request.getParameter("type");
			
			if("Android".equals(type)){
				type = "0";
			}
			
			int cnt = checkVersion(versionId,type);
			int  isnew = cnt>0?1:0;
			String comment = "";
			String url ="";
			if(isnew>0){
				
				Map m = new HashMap();
				m.put("vers", versionId);
		    	m.put("t", type);
		    	List<CVersion> l  = (List)opensqlmanage.selectForListByMap(m, "c_version.get-new-record");
		    	if(l.size()>0){
		    		CVersion version = l.get(0);
		    		comment = version.getComment();
		    		url = version.getUrl();
		    	}
			}
			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			if ("iOS" == client_type || "iOS".equals(client_type)) {
				String isUpgrade = sys.getKeys("iOSUpgrade");
				if(null !=isUpgrade && !("").equals(isUpgrade)){
					map.put("isupgrade", isUpgrade);
				}else{
					map.put("isupgrade", "0");
				}
			} else if ("Android" == client_type || "Android".equals(client_type)) {
				String isUpgrade = sys.getKeys("AndroidUpgrade");
				if(null !=isUpgrade && !("").equals(isUpgrade)){
					map.put("isupgrade", isUpgrade);
				}else{
					map.put("isupgrade", "0");
				}
			}
			map.put("Isnew", isnew);
			map.put("info", comment);
			map.put("url", url);
			String sessionId = request.getParameter("sessionId");
			String flag = checkSessionId(sessionId);
			map.put("flag", flag);
		}catch(Exception e){
			e.printStackTrace();
			statusCode = "0";
		}
		map.put("statusCode", statusCode);
		out.print(JSONObject.fromObject(map).toString());
		out.close();
	}
	
	 /**
     * 检查版本号是否为最新的
     * @param version
     * return false 不是最新的
     * return true 是最新的
     */
    public int checkVersion(String version,String type){
    	opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
    	Map map = new HashMap();
    	map.put("vers", version);
    	map.put("t", type);
    	int v = (Integer)opensqlmanage.selectForObjectByMap(map, "c_version.check_version");
    	System.out.println("v---------->"+v);
    	return v;
    }
    
 
    //检查用户sessionId是否可用
    public String  checkSessionId(String sessionId) throws SQLException{
    	String flag ="-1";
    	if(null != sessionId && !("").equals(sessionId)){
	    	TMemberSessionManager tmembersessionmanager=(TMemberSessionManager)getSpringBean("tmembersessionmanager");
	    	TMemberSessionExample example = new TMemberSessionExample();
	    	example.createCriteria().andSessionidEqualTo(sessionId);
	    	List list = tmembersessionmanager.selectByExample(example);
	    	if(null != list && list.size()==1){	
	    		flag = "1";
	    	}else{
	    		flag = "-1";
	    	}
    	}
    	return flag;
    }
}
