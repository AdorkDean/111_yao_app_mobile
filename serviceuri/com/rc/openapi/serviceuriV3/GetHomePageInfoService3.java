package com.rc.openapi.serviceuriV3;

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
import org.springframework.util.StringUtils;

import com.rc.openapi.model.HomePageVO2;
import com.rc.openapi.model.HomePageVO2.CarouselFigure;
import com.rc.openapi.model.HomePageVO2.ZhuanQu;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CPositionManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;
/**
 * 获取首页信息3
 *
 */
public class GetHomePageInfoService3 extends BaseURIService {

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
			String type = "1";
			if("iOS"==client_type||"iOS".equals(client_type)){
				type = "2";
			}else if ("Android"==client_type||"Android".equals(client_type)) {
				type = "3";
			}
			try {
				str = (String) MemCached.getmcc().get("homeDatahd3"+client_type);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(null==str||"".equals(str)){
				str = getHomeData(type);
				try {
					MemCached.getmcc().add("homeDatahd3"+client_type,str,new Date(1000*60*30));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
//			String client_version = request.getParameter("client_version");
//			if(StringUtils.hasText(client_version)){
//				String cv = client_version.replaceAll("\\.","");
//				if(Integer.parseInt(cv)<203&&"Android".equals(client_type)){
//					str = str.replace("/static/extend2/extend.html", "/static/extend/extend.html");
//				}
//			}
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
	private String getHomeData(String type) throws Exception{
		//67,68,      轮播图 专区
//		String homePageConfigId=InfoUtil.getInstance().getInfo("config", "homePageConfigId");
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		String homePageConfigId = sys.getKeys("homePageConfigIdNEW");
		if(!StringUtils.hasText(homePageConfigId)){
			return null;
		}
		String[] hids = homePageConfigId.split(",");
		HomePageVO2 hp = new HomePageVO2();
			//首页头部导航
			CPositionManagerImpl manager = (CPositionManagerImpl) getSpringBean("cpositionmanager");
			CPositionExample example = new CPositionExample();
			int id = Integer.parseInt(hids[0]);
//			example.createCriteria().andChannelEqualTo(id);
//			List<CPosition> list0 = manager.selectByExample(example);
//			List<TopNav> topNavList = new ArrayList<TopNav>();
//			for (CPosition obj : list0) {
//				Integer iss = obj.getIssue();//是否启用
//				if(1==iss){
//					HomePageVO.TopNav top = hp.new TopNav();
//					System.out.println(iss);
//					top.setAurl(obj.getImgLink());//安卓路径
//					top.setIurl(obj.getTitle());//IOS路径
//					top.setName(obj.getImgUrl());//导航名称
//					top.setOrder(obj.getWeight()+"");//排序
//					topNavList.add(top);
//				}
//			}
			//首页轮播图
//			example.clear();
//			id = Integer.parseInt(hids[1]);
			example.setOrderByClause("weight");
			example.createCriteria().andChannelEqualTo(id).andTitleUrlEqualTo(type);
			List<CPosition> list1 = manager.selectByExample(example);
			List<CarouselFigure> cfList = new ArrayList<CarouselFigure>();
			for (CPosition obj : list1) {
				Integer iss = obj.getIssue();//是否启用
				if(1==iss){
					HomePageVO2.CarouselFigure cf = hp.new CarouselFigure();
					cf.setWebUrl(obj.getTitle());//weburl
					cf.setImgUrl(obj.getImgUrl());//图片路径
					cf.setName(obj.getImgLink());//导航名称
					cf.setWeight(obj.getWeight()+"");//排序
					cfList.add(cf);
				}
			}
			//专区   活动类型(1：分类,2：品牌,3：活动)
			example.clear();
			id = Integer.parseInt(hids[1]);
			List<String> list = new ArrayList<String>();
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("4");
			list.add("5");
			list.add("6");
			example.setOrderByClause("weight");
			example.createCriteria().andChannelEqualTo(id).andTitleAllIn(list).andTxtLinkEqualTo(type);
			List<CPosition> list2 = manager.selectByExample(example);
			List<ZhuanQu> zqList = new ArrayList<ZhuanQu>();
			for (CPosition obj : list2) {
				Integer iss = obj.getIssue();//是否启用
				if(1==iss){
					HomePageVO2.ZhuanQu cf = hp.new ZhuanQu();
					cf.setImgUrl(obj.getImgUrl());//图片路径
					cf.setAid(obj.getTitle());//活动id
					cf.setType(obj.getTitleAll());//活动类型
					cf.setName(obj.getImgLink());//导航名称
					cf.setHtml5url(obj.getTitleUrl());//html5url
					cf.setWeight(obj.getWeight()+"");
					zqList.add(cf);
				}
			}
			
//			OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
//			Map map = new HashMap();
//			id = Integer.parseInt(hids[2]);
//			map.put("tp", id);
//			List<Map<String,Object>> list = om.selectForListByMap(map, "n_position.selectLeftNavByType");
//			for (Map<String, Object> obj : list) {
//				
//			}
//			List<LeftNav> leftNavList = new ArrayList<LeftNav>();
//			hp.setTopNavList(topNavList);
			hp.setCfList(cfList);
			hp.setZqList(zqList);
			Map map = new HashMap();
			map.put("hp", hp);
			map.put("statusCode", "1");
			JSONObject json4 = JSONObject.fromObject(map);
		return json4.toString();
	}
	
	
	public static void main(String[] args) {
		MemCached.getmcc().delete("homeDatahdAndroid");
	}
}
