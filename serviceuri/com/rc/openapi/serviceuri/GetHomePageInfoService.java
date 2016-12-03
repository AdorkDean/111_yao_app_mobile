package com.rc.openapi.serviceuri;

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

import com.rc.openapi.model.HomePageVO;
import com.rc.openapi.model.HomePageVO.CarouselFigure;
import com.rc.openapi.model.HomePageVO.ZhuanQu;
import com.rc.openapi.service.CPositionManager;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;
/**
 * 获取首页信息
 *
 */
public class GetHomePageInfoService extends BaseURIService {

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
			try {
				str = (String) MemCached.getmcc().get("homeData");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(null==str||"".equals(str)){
				str = getHomeData();
				try {
					MemCached.getmcc().add("homeData",str,new Date(1000*60*30));
				}catch (Exception e) {
					e.printStackTrace();
				}
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
	private String getHomeData() throws Exception{
		//67,68,      轮播图 专区
		String homePageConfigId=InfoUtil.getInstance().getInfo("config", "homePageConfigId");
		String[] hids = homePageConfigId.split(",");
		HomePageVO hp = new HomePageVO();
			//首页头部导航
			CPositionManager manager = (CPositionManager) getSpringBean("cpositionmanager");
			CPositionExample example = new CPositionExample();
			int id = Integer.parseInt(hids[0]);
//			example.createCriteria().andChannelEqualTo(id);
//			List<NPosition> list0 = manager.selectByExample(example);
//			List<TopNav> topNavList = new ArrayList<TopNav>();
//			for (NPosition obj : list0) {
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
			example.createCriteria().andChannelEqualTo(id).andTitleUrlEqualTo("1");
			List<CPosition> list1 = manager.selectByExample(example);
			List<CarouselFigure> cfList = new ArrayList<CarouselFigure>();
			for (CPosition obj : list1) {
				Integer iss = obj.getIssue();//是否启用
				if(1==iss){
					HomePageVO.CarouselFigure cf = hp.new CarouselFigure();
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
			example.setOrderByClause("weight");
			example.createCriteria().andChannelEqualTo(id).andTitleAllIn(list).andTxtLinkEqualTo("1");
			List<CPosition> list2 = manager.selectByExample(example);
			List<ZhuanQu> zqList = new ArrayList<ZhuanQu>();
			for (CPosition obj : list2) {
				Integer iss = obj.getIssue();//是否启用
				if(1==iss){
					HomePageVO.ZhuanQu cf = hp.new ZhuanQu();
					cf.setImgUrl(obj.getImgUrl());//图片路径
					cf.setAid(obj.getTitle());//活动id
					cf.setType(obj.getTitleAll());//活动类型
					cf.setName(obj.getImgLink());//导航名称
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
}
