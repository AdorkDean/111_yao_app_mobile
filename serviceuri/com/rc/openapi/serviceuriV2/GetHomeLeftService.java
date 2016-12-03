package com.rc.openapi.serviceuriV2;

import java.io.IOException;
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

import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CPositionManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;
/**
 * 获取首页侧边栏
 *
 */
public class GetHomeLeftService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974362242L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String client_type = request.getParameter("client_type"); //客户端类型
		String resolution  = request.getParameter("resolution");//分辨率
		String str = null;
		if(null != client_type && !("").equals(client_type) && null != resolution && !("").equals(resolution)){
			try {
				try {
					str = (String) MemCached.getmcc().get("leftfence"+resolution);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(null==str||"".equals(str)){
					if(client_type.equals("iOS") || client_type.equals("Android")){
					 str = getHomeData(client_type,resolution);
					}else{
						map.put("statusCode", "2"); //客户端类型不匹配
						JSONObject json4 = JSONObject.fromObject(map);
						str=json4.toString();
					}
					try {
						MemCached.getmcc().add("leftfence"+resolution,str,new Date(1000*60*30));
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				map.put("statusCode", "0");
				JSONObject json4 = JSONObject.fromObject(map);
				str=json4.toString();
				e.printStackTrace();
			}
		}
		ConstantUtil.reJSON(str, request, response);
	}
	@SuppressWarnings("unchecked")
	private String getHomeData(String client_type,String resolution) throws Exception{
		TSysParameterManager tsysparametermanager = (TSysParameterManager)getSpringBean("tsysparametermanager");
		    String leftfenceId=tsysparametermanager.getKeys("leftfence");
		    Map map = new HashMap();
			//首页头部导航
			CPositionManagerImpl cpositionmanager = (CPositionManagerImpl) getSpringBean("cpositionmanager");
			CPositionExample example = new CPositionExample();
			int id = Integer.parseInt(leftfenceId);
			String type = "1" ; //默认是iOS
			if(client_type.equals("Android")){
				type = "2" ;
			}
			example.setOrderByClause("weight asc");
			example.createCriteria().andChannelEqualTo(id).andImgLinkEqualTo(type).andTitleAllEqualTo(resolution);
			List<CPosition> list = cpositionmanager.selectByExample(example);
			List list1 = new ArrayList() ;
			if(null != list && list.size()>0){
				for(int i=0 ;i<list.size();i++){
					CPosition np = list.get(i);
					if(1==np.getIssue()){
						Map leftmap = new HashMap();
						leftmap.put("imgUrl", np.getImgUrl());
						leftmap.put("name", np.getTitle());
						leftmap.put("id", np.getTitleUrl());
						list1.add(leftmap);
					}
				}
				map.put("statusCode", "1");
				map.put("left", list1);
			}else{
				example = new CPositionExample();
				example.setOrderByClause("weight asc");
				example.createCriteria().andChannelEqualTo(id).andImgLinkEqualTo(type).andTitleAllEqualTo("2");
				List<CPosition> leftlist = cpositionmanager.selectByExample(example);
				for(int i=0 ;i<leftlist.size();i++){
					CPosition np = leftlist.get(i);
					if(1==np.getIssue()){
						Map leftmap = new HashMap();
						leftmap.put("imgUrl", np.getImgUrl());
						leftmap.put("name", np.getTitle());
						leftmap.put("id", np.getTitleUrl());
						list1.add(leftmap);
					}
				}
				map.put("statusCode", "1");
				map.put("left", list1);				
			}
			String headid = InfoUtil.getInstance().getInfo("config", "headid");
			CPositionExample ne = new CPositionExample();
			ne.setOrderByClause("weight asc");
			ne.createCriteria().andChannelEqualTo(new Integer(headid));
			List neList = cpositionmanager.selectByExample(ne);
			List list2 = new ArrayList() ;
			for(int i =0;i<neList.size();i++){
				CPosition np = (CPosition) neList.get(i);
				Map headmap = new HashMap();
				if(null !=np.getTitleUrl()){
					headmap.put("url", np.getTitleUrl());
				}else{
					headmap.put("url", "");
				}
				headmap.put("txt", np.getTitleAll());
				
				list2.add(headmap);
			}
			map.put("head", list2);	
			JSONObject json4 = JSONObject.fromObject(map);
		return json4.toString();
	}
	public static void main(String[] args) {
		
	}
}
