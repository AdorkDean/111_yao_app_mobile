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
 * 全部分类(六个分类)
 * 
 * @author user00
 * @createTime 2015-12-31 上午11:42:10
 */
public class MoreClassifyURIService extends BaseURIService {

	private static final long serialVersionUID = -8627452547593142156L;
	private final Logger log = Logger.getLogger(getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String resultJson = null;

		try {
			String client_type = request.getParameter("client_type");// 客户端系统
			String type = "1";
			if ("iOS" == client_type || "iOS".equals(client_type)) {
				type = "2";
			} else if ("Android" == client_type || "Android".equals(client_type)) {
				type = "3";
			}
			TSysParameterManager sysManager = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String homePageConfigId = sysManager.getKeys("homePageConfigId_Lunbo_Fenlei");
			if (!StringUtils.hasText(homePageConfigId)) {
				// FIXME WWF(新接口查询不到处理)
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("statusCode", "0");
				JSONObject json4 = JSONObject.fromObject(map);
				resultJson = json4.toString();
				return;
			}
			
			// 取缓存
			try {
				resultJson = (String) MemCached.getmcc().get("MoreClassify" + client_type);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (resultJson == null || "".equals(resultJson)) {
				String[] hids = homePageConfigId.split(",");
				CPositionManagerImpl manager = (CPositionManagerImpl) getSpringBean("cpositionmanager");

				// 更多分类ID
				int id = Integer.parseInt(hids[3]);

				// 更多分类
				CPositionExample moreTypeExample = new CPositionExample();
				moreTypeExample.setOrderByClause("weight");
				moreTypeExample.createCriteria().andChannelEqualTo(id).andTxtLinkEqualTo(type);
				
				
				List<CPosition> moreTypeList = manager.selectByExample(moreTypeExample);
				List<MoreType> resultMoreTypeList = new ArrayList<MoreType>();
				MoreType _MoreType = null;
				for (CPosition cPosition : moreTypeList) {
					_MoreType = new MoreType(cPosition.getImgUrl(), cPosition.getImgLink(), cPosition.getTitle(),
								cPosition.getTitleAll(), cPosition.getWeight() + "", cPosition.getTitleUrl());
					resultMoreTypeList.add(_MoreType);
				}
				
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("moreList", resultMoreTypeList);
				resultMap.put("statusCode", "1");
				JSONObject json4 = JSONObject.fromObject(resultMap);
				resultJson = json4.toString();
				try {
					MemCached.getmcc().add("MoreClassify"+client_type,resultJson,new Date(1000*60*30));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			resultJson = json4.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			resultJson = json4.toString();
		}
		ConstantUtil.reJSON(resultJson, request, response);
	}
	
	
	
	
	/**
	 * 六个分类返回modal
	 * 
	 * @author user00
	 * @createTime 2015-12-29 上午11:54:18
	 */
	public class MoreType {
		private String img;// 分类图片地址
		private String imgUrl;// 分类链接
		private String title;// 分类名称
		private String titleAll;// 背景颜色
		private String weight;// 权重
		private String titleUrl;// 类型(1:分类ID,2:H5URL,3:商品ID,4:更多)

		public MoreType(String img, String imgUrl, String title, String titleAll, String weight, String titleUrl) {
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
	
	public static void main(String[] args) {
		
	}

}
