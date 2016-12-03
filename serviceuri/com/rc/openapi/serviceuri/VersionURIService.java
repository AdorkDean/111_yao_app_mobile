package com.rc.openapi.serviceuri;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.OpenSqlManage;
/**
 * 版本控制
 * @author Administrator
 *
 */
public class VersionURIService extends BaseURIService {
	
	
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
			String type = request.getParameter("type");
			
			if("Android".equals(type)){
				type = "0";
			}
			
			int cnt = checkVersion(versionId,type);
			System.out.println("cnt==="+cnt);
			int  isnew = cnt>0?1:0;
			System.out.println("isnew===="+isnew);
			String comment = "";
			if(isnew>0){
				
				Map m = new HashMap();
				m.put("vers", versionId);
		    	m.put("t", type);
//		        comment = (String)opensqlmanage.selectForObjectByMap(m, "c_version.get-comment");
		    	
		    	List<com.rc.openapi.vo.CVersion> l  = (List)opensqlmanage.selectForListByMap(m, "c_version.get-new-record");
		    	if(l.size()>0){
		    		comment = l.get(0).getComment();
		    	}
		        	
			}
			
			map.put("Isnew", isnew);
			map.put("info", comment);
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
    

}
