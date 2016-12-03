package com.rc.openapi.serviceuriV4.index;

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

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CPositionManagerImpl;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.PageResult;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;

/**
 * 新版app首页活动接口(20160627)
 * 
 * @author WWF
 * @createTime 2016-6-27 上午10:49:13
 */
public class GetHomePageActivityService extends BaseURIService {
	private static final long serialVersionUID = -1872857484672270743L;

	private final Logger log = Logger.getLogger(getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = null;
		String str = null;
		try {
			String pageNo = request.getParameter("pageNo");// 当前页数
			String pageSize = request.getParameter("pageSize");// 显示条数

			String client_type = request.getParameter("client_type");// 客户端系统
			String type = "1";
			if (client_type != null && "ios".equals(client_type.toLowerCase())) {
				type = "2";
			} else if (client_type != null && "android".equals(client_type.toLowerCase())) {
				type = "3";
			}
			if (pageNo == null || "".equals(pageNo)) {
				pageNo = "1";
			}
			if (pageSize == null || "".equals(pageSize)) {
				pageSize = "50";
			}
			if ("1".equals(pageNo)) {
				try {
					str = ""; // (String) MemCached.getmcc().get("homeDataxx3" +
								// client_type);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (null == str || "".equals(str)) {
				List<Map<String, String>> result = getHomeData(pageNo, pageSize, type);
				int size = result.size();
				if (size == 0) {
					map.put("moreurl", null);
				} else {
					map.put("moreurl", (Integer.parseInt(pageNo) + 1) + "");
				}
				statusCode = "1";
				map.put("list", result);
				map.put("statusCode", statusCode);
				JSONObject json4 = JSONObject.fromObject(map);
				str = json4.toString();
				if ("1".equals(pageNo)) {
					try {
						// MemCached.getmcc().add("homeDataxx3" + client_type,
						// str, new Date(1000 * 60 * 30));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			log.equals(e.getMessage());
			map.clear();
			map.put("statusCode", "0");
			JSONObject json4 = JSONObject.fromObject(map);
			str = json4.toString();
			e.printStackTrace();
		}
		ConstantUtil.reJSON(str, request, response);
	}

	private List<Map<String, String>> getHomeData(String pageNo, String pageSize, String type) throws Exception {
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		String cid = sys.getKeys("homePageActivityConfigIdNew");
		if (!StringUtils.hasText(cid)) {
			return null;
		}
		int id = Integer.parseInt(cid);
		OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
		CPositionManagerImpl manager = (CPositionManagerImpl) getSpringBean("cpositionmanager");
//		TGoodsManager tGoodsManager = (TGoodsManager) getSpringBean("tgoodsmanager");
		
		PageWraper pw = new PageWraper();
		PageResult rs = new PageResult();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel", id);
		map.put("type", type);
		rs.setP_curPage(Integer.parseInt(pageNo)); // 第一页数据
		rs.setP_pageSize(Integer.parseInt(pageSize)); // 第页显示2条，这里不设置，默认为20条
		pw = om.selectForPageByMap(map, "c_position.selectPositionByPageCount3", "c_position.selectPositionByPage3",
				rs.getP_curPage(), rs.getP_pageSize());
		List<Map<String, Object>> huodongList = pw.getResult();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		List<SimpleGoods> ll = null;
		for (Map<String, Object> map2 : huodongList) {
			String pid = map2.get("id") == null ? "" : map2.get("id").toString();
			if (StringUtils.hasText(pid)) {//
				CPositionExample goodsExample = new CPositionExample();
				goodsExample.createCriteria().andTitleUrlEqualTo(pid).andIssueEqualTo(1);
				goodsExample.setOrderByClause( " weight desc ");
				List<CPosition> goodsList = manager.selectByExample(goodsExample);
				SimpleGoods s = null;
				ll = new ArrayList<SimpleGoods>();
				Map<String, Object> param = null;
				for (CPosition cPosition2 : goodsList) {
					if(cPosition2!=null&&cPosition2.getTitle()!=null&&!"".equals(cPosition2.getTitle())){
						param = new HashMap<String, Object>();
						param.put("id", cPosition2.getTitle());
						System.out.println(cPosition2.getId());
						
						Map<String, Object> goodResult = (Map<String, Object>) om.selectForObjectByMap(param, "t_goods.selectGoodsSomeInfoById");
						if(goodResult!=null&& goodResult.get("id")!=null){
							s = new SimpleGoods();
							s.setGoodCode(goodResult.get("goodscode")==null?"":goodResult.get("goodscode").toString());//商品编码
							s.setGoodName(goodResult.get("goodsName")==null?"":goodResult.get("goodsName").toString());//商品名称
							s.setImgUrl(goodResult.get("imgUrl")==null?"":goodResult.get("imgUrl").toString());//商品图片地址
							s.setPrice(goodResult.get("price")==null?"":goodResult.get("price").toString());//价格
							s.setId(goodResult.get("id")==null?"":goodResult.get("id").toString());//id
							ll.add(s);
						}
					}
				}
				map2.put("goods", ll);
				resultList.add(map2);
			}
		}

		pw.setResult(resultList);
		return pw.getResult();
	}

	public class SimpleGoods {
		private String goodCode;// 商品编码
		private String imgUrl;
		private String goodName;
		private String price;
		
		private String id;//商品id

		public String getGoodCode() {
			return goodCode;
		}

		public void setGoodCode(String goodCode) {
			this.goodCode = goodCode;
		}

		public String getGoodName() {
			return goodName;
		}

		public void setGoodName(String goodName) {
			this.goodName = goodName;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}
}
