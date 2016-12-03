package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.CPositionManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;

/**
 * 获取上下导航
 *
 */
public class GetUpAndDownNavService_3 extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	String str = null;
		try {
			String client_type = request.getParameter("client_type");// 客户端系统
			String resolution  = request.getParameter("resolution");//分辨率
			String client_version = request.getParameter("client_version"); //版本号
			
			String type = "1";
			if(StringUtils.hasText(client_type)&&StringUtils.hasText(resolution)){
				if("iOS"==client_type||"iOS".equals(client_type)){
					type = "2";
				}else if ("Android"==client_type||"Android".equals(client_type)) {
					type = "3";
				}
				try {
					str = ""; //(String) MemCached.getmcc().get("homeDatahdbn"+client_type+resolution);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(null==str||"".equals(str)){
					str = getHomeData(type,resolution,client_version);
//					try {
//						MemCached.getmcc().add("homeDatahdbn"+client_type+resolution,str,new Date(1000*60*30));
//					}catch (Exception e) {
//						e.printStackTrace();
//					}
				}
			}else {
				Map map = new HashMap();
				map.put("statusCode", "0");
				JSONObject json4 = JSONObject.fromObject(map);
				str=json4.toString();
			}
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			str=json4.toString();
			e.printStackTrace();
		}
		ConstantUtil.reJSON(str, request, response);
	}
	@SuppressWarnings("unchecked")
	private String getHomeData(String type,String resolution,String client_version) throws Exception{
		//底部
//		String bnid=InfoUtil.getInstance().getInfo("config", "homePageConfigBNId");
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		String bnid = sys.getKeys("homePageConfigBNId");
		CPositionManager manager = (CPositionManager) getSpringBean("cpositionmanager");
		CPositionExample example = new CPositionExample();
		List list1 = new ArrayList();
		if(StringUtils.hasText(bnid)){
			int id = Integer.parseInt(bnid);
			example.setOrderByClause("weight asc");
			example.createCriteria().andChannelEqualTo(id).andTxtLinkEqualTo(type).andPriceEqualTo(resolution);
			List<CPosition> list = manager.selectByExample(example);
			if(null!=list&&0<list.size()){
				for (CPosition obj : list) {
					Integer iss = obj.getIssue();//是否启用
					if(1==iss){
						Map<String, String> map = new HashMap<String, String>();
						map.put("name", obj.getImgLink()==null?"":obj.getImgLink());//选中图片地址
						map.put("selImgUrl", obj.getImgUrl()==null?"":obj.getImgUrl());//导航名称
						map.put("notSelImgUrl", obj.getTitle()==null?"":obj.getTitle());//未选中图片地址
						map.put("selTypeface", obj.getTitleAll()==null?"":obj.getTitleAll());//选中字体
						map.put("notSelTypeface", obj.getTitleUrl()==null?"":obj.getTitleUrl());//未选中字体
						map.put("html5url", obj.getMarketPrice()==null?"":obj.getMarketPrice());//html5地址
						list1.add(map);
					}
				}
			}else {
				example.clear();
				example.setOrderByClause("weight asc");
				example.createCriteria().andChannelEqualTo(id).andTxtLinkEqualTo(type).andPriceEqualTo("2");
				list = manager.selectByExample(example);
				for (CPosition obj : list) {
					Integer iss = obj.getIssue();//是否启用
					if(1==iss){
						Map<String, String> map = new HashMap<String, String>();
						map.put("name", obj.getImgLink()==null?"":obj.getImgLink());//选中图片地址
						map.put("selImgUrl", obj.getImgUrl()==null?"":obj.getImgUrl());//导航名称
						map.put("notSelImgUrl", obj.getTitle()==null?"":obj.getTitle());//未选中图片地址
						map.put("selTypeface", obj.getTitleAll()==null?"":obj.getTitleAll());//选中字体
						map.put("notSelTypeface", obj.getTitleUrl()==null?"":obj.getTitleUrl());//未选中字体
						map.put("html5url", obj.getMarketPrice()==null?"":obj.getMarketPrice());//html5地址
						list1.add(map);
					}
				}
			}
		}
		
		//头部
		String headid = sys.getKeys("headid_3");
		//System.out.println("============="+client_version);
//		String headid = InfoUtil.getInstance().getInfo("config", "headid");
		List list2 = new ArrayList() ;
		if(StringUtils.hasText(headid)){
			example.clear();
			example.setOrderByClause("weight asc");
			example.createCriteria().andChannelEqualTo(new Integer(headid));
			List neList = manager.selectByExample(example);
			for(int i =0;i<neList.size();i++){
				CPosition np = (CPosition) neList.get(i);
				Map headmap = new HashMap();
				if(null !=np.getTitleUrl()){
					headmap.put("url", np.getTitleUrl());
				}else{
					headmap.put("url", "");
				}
				if(null != client_version && (client_version.equals("2.1.3") || client_version.equals("2.1.1") || client_version.equals("2.1.2")) && i==1){
					headmap.put("txt", "品牌特卖");
				}else if(null != client_version && (client_version.equals("2.1.3")|| client_version.equals("2.1.1") || client_version.equals("2.1.2")) && i==2){
					headmap.put("txt", "包邮专区");
				}else{
					headmap.put("txt", np.getTitleAll());
				}
				list2.add(headmap);
			}
		}
		Map map = new HashMap();
		map.put("down", list1);
		map.put("head", list2);
		map.put("statusCode", "1");
		JSONObject json4 = JSONObject.fromObject(map);
		System.out.println("json4===="+json4);
		return json4.toString();
	}
	
}
