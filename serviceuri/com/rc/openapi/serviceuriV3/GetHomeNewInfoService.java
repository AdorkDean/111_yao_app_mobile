package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.sql.SQLException;
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

import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CPositionManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;

/**
 * 获取wap首页,轮播图片/分类等
 * 
 * @author user00
 * @createTime 2015-12-28 下午4:23:19
 */
public class GetHomeNewInfoService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultJson = null;
		
		try {
			String client_type = request.getParameter("client_type");// 客户端系统
			String type = "1";
			if(client_type!=null && "ios".equals(client_type.toLowerCase())){
				type = "2";
			}else if(client_type!=null && "android".equals(client_type.toLowerCase())){
				type = "3";
			}
			TSysParameterManager sysManager = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String homePageConfigId = sysManager.getKeys("homePageConfigId_Lunbo_Fenlei");
			if (!StringUtils.hasText(homePageConfigId)) {
				// FIXME WWF(新接口查询不到处理)
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("statusCode", "0");
				JSONObject json4 = JSONObject.fromObject(map);
				resultJson = json4.toString();
				return;
			}
			try {
				resultJson = "";//(String) MemCached.getmcc().get("homeDatahd"+client_type);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(resultJson==null || "".equals(resultJson)){
				String[] hids = homePageConfigId.split(",");
				CPositionManagerImpl manager = (CPositionManagerImpl) getSpringBean("cpositionmanager");
				CPositionExample lunboExample = new CPositionExample();
				
				
				// 轮播图
				
				int id = Integer.parseInt(hids[0]);
				lunboExample.setOrderByClause("weight");
				lunboExample.createCriteria().andChannelEqualTo(id).andTitleUrlEqualTo(type);
				List<CPosition> lunboList = manager.selectByExample(lunboExample);
				List<Lunbo> resultLunboList = new ArrayList<GetHomeNewInfoService.Lunbo>();
				Lunbo _Lunbo = null;
				for (CPosition cPosition : lunboList) {
					_Lunbo = new Lunbo(cPosition.getImgLink(), cPosition.getImgUrl(), cPosition.getTitle(),
							cPosition.getWeight() + "", cPosition.getTxtLink());
					resultLunboList.add(_Lunbo);
				}
				
				
				//专区
				id = Integer.parseInt(hids[1]);
				CPositionExample zhuanquExample = new CPositionExample();
				zhuanquExample.setOrderByClause("weight");
				zhuanquExample.createCriteria().andChannelEqualTo(id).andTitleAllGreaterThanOrEqualTo("1")
						.andTitleAllLessThanOrEqualTo("6").andTxtLinkEqualTo(type);
				List<CPosition> zhuanquList = manager.selectByExample(zhuanquExample);
				List<Zhuanqu> resultZhuanquList = new ArrayList<Zhuanqu>();
				Zhuanqu _Zhuanqu = null;
				int fourSize = 0;
				for (CPosition cPosition : zhuanquList) {
					if(fourSize!=4){
						_Zhuanqu = new Zhuanqu(cPosition.getImgLink(), cPosition.getImgUrl(), cPosition.getTitleAll(),
								cPosition.getTitle(), cPosition.getWeight() + "", cPosition.getTitleUrl());
						resultZhuanquList.add(_Zhuanqu);
						fourSize = fourSize + 1;
					}
				}
				
				
				// 六个分类
				id = Integer.parseInt(hids[2]);
				// 六个分类
				CPositionExample sixTypeExample = new CPositionExample();
				sixTypeExample.setOrderByClause("weight");
				sixTypeExample.createCriteria().andIssueEqualTo(1).andChannelEqualTo(id).andTxtLinkEqualTo(type);
				List<CPosition> sixTypeList = manager.selectByExample(sixTypeExample);
				List<SixType> resultSixTypesList = new ArrayList<GetHomeNewInfoService.SixType>();
				SixType _SixType = null;
				int sixSize = 0;
				for (CPosition cPosition : sixTypeList) {
					if(sixSize!=6){
						_SixType = new SixType(cPosition.getImgUrl(), cPosition.getImgLink(), cPosition.getTitle(),
								cPosition.getTitleAll(), cPosition.getWeight() + "", cPosition.getTitleUrl());
						resultSixTypesList.add(_SixType);
						sixSize = sixSize + 1;
					}
				}
				
				
				//2016-07-04新加轮播消息List
				String messageid = sysManager.getKeys("app_rotate_message_id");
				List<Map<String, Object>> messageList = new ArrayList<Map<String, Object>>();
				if(messageid!=null||!"".equals(messageid)){
					int _messageid = Integer.valueOf(messageid);
					CPositionExample cPositionExample = new CPositionExample();
					cPositionExample.setOrderByClause(" weight desc ");
					cPositionExample.createCriteria().andChannelEqualTo(_messageid).andIssueEqualTo(1);
					List<CPosition> list = manager.selectByExample(cPositionExample);
					Map<String, Object> map = null;
					for (CPosition cPosition : list) {
						map = new HashMap<String, Object>();
						map.put("id", cPosition.getId());// id
						map.put("title", cPosition.getTitle() == null ? "" : cPosition.getTitle());// 标题
						map.put("message", cPosition.getTitleAll() == null ? "" : cPosition.getTitleAll());// 内容信息
						map.put("type", cPosition.getPrice() == null ? "" : cPosition.getPrice());//类型:1:ftl页面;2:h5页;
						map.put("url", cPosition.getTitleUrl() == null ? "" : cPosition.getTitleUrl());// 链接地址
						messageList.add(map);
					}
				}
				if(messageList==null||messageList.size()<=0){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", "0");// id
					map.put("title", "");// 标题
					map.put("message", "");// 内容信息
					map.put("type", "");//类型:1:ftl页面;2:h5页;
					map.put("url", "");// 链接地址
					messageList.add(map);
				}
				
				Map<String, Object> listMap = new HashMap<String, Object>();
				listMap.put("cfList", resultLunboList);// 轮播
				listMap.put("typeList", resultSixTypesList);// 六个分类
				listMap.put("zqList", resultZhuanquList);// 专区
				listMap.put("mList", messageList);// 消息轮播
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("hp", listMap);
				resultMap.put("statusCode", "1");
				JSONObject json4 = JSONObject.fromObject(resultMap);
				resultJson = json4.toString();
				try {
					MemCached.getmcc().add("homeDatahd"+client_type,resultJson,new Date(1000*60*30));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			resultJson = json4.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			resultJson = json4.toString();
		}
		ConstantUtil.reJSON(resultJson, request, response);
	}

	public static void main(String[] args) {
		MemCached.getmcc().delete("homeDatahdAndroid");
	}

	/**
	 * 轮播图片返回modal
	 * 
	 * @author user00
	 * @createTime 2015-12-29 上午11:53:41
	 */
	public class Lunbo {
		private String name;// 名称
		private String imgUrl;// 图片链接
		private String webUrl;// h5链接
		private String weight;// 权重
		private String type;// 类型

		public Lunbo(String name, String imgUrl, String webUrl, String weight, String type) {
			this.name = name;
			this.imgUrl = imgUrl;
			this.webUrl = webUrl;
			this.weight = weight;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getWebUrl() {
			return webUrl;
		}

		public void setWebUrl(String webUrl) {
			this.webUrl = webUrl;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	/**
	 * 六个分类返回modal
	 * 
	 * @author user00
	 * @createTime 2015-12-29 上午11:54:18
	 */
	public class SixType {
		private String img;// 分类图片地址
		private String imgUrl;// 分类链接
		private String title;// 分类名称
		private String titleAll;// 背景颜色
		private String weight;// 权重
		private String titleUrl;// 类型(1:分类ID,2:H5URL,3:商品ID,4:更多)

		public SixType(String img, String imgUrl, String title, String titleAll, String weight, String titleUrl) {
			this.img = img;
			this.imgUrl = imgUrl;
			this.title = title;
			this.titleAll = titleAll;
			this.weight = weight;
			this.titleUrl = titleUrl;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitleAll() {
			return titleAll;
		}

		public void setTitleAll(String titleAll) {
			this.titleAll = titleAll;
		}

		public String getTitleUrl() {
			return titleUrl;
		}

		public void setTitleUrl(String titleUrl) {
			this.titleUrl = titleUrl;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

	}

	/**
	 * 专区返回modal
	 * 
	 * @author user00
	 * @createTime 2015-12-29 上午11:55:15
	 */
	public class Zhuanqu {
		private String name;
		private String imgUrl;
		private String type;
		private String aid;
		private String weight;
		private String html5url;

		public Zhuanqu(String name, String imgUrl, String type, String aid, String weight, String html5url) {
			this.name = name;
			this.imgUrl = imgUrl;
			this.type = type;
			this.aid = aid;
			this.weight = weight;
			this.html5url = html5url;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getAid() {
			return aid;
		}

		public void setAid(String aid) {
			this.aid = aid;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getHtml5url() {
			return html5url;
		}

		public void setHtml5url(String html5url) {
			this.html5url = html5url;
		}

	}

}
