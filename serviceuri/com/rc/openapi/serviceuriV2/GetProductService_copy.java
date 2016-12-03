package com.rc.openapi.serviceuriV2;

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

import com.rc.openapi.model.ProductInfo;
import com.rc.openapi.service.TGoodsImagesManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.OpenSqlManageImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TGoodsImages;
import com.rc.openapi.vo.TGoodsImagesExample;
/**
 * 获取商品详情
 *
 */
public class GetProductService_copy extends BaseURIService {
	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String statusCode = null;
		try {
			String spid = request.getParameter("pid");
			if (StringUtils.hasText(spid)) {
				long pid = Long.parseLong(spid);
				map.put("goodsid", pid);
				OpenSqlManageImpl om = (OpenSqlManageImpl) getSpringBean("opensqlmanage");
				ProductInfo obj = (ProductInfo) om.selectForObjectByMap(map,"t_goods.select_product_Info");
				if(null!=obj){
					String goodsType = "1"; //1otc 2 处方药 3非药品
					String isp = obj.getIsPrescription();
					if("2".equals(isp)||"3".equals(isp)){
						obj.setIsPrescription("1");
						goodsType = "2" ; 
					}else {
						obj.setIsPrescription("0");
						if("4".equals(isp)){
							goodsType="3";
						}
					}
					map.put("goodsType", goodsType);
	//				List rlist = om.selectForListByMap(map,"dst_product.select_recommend_product");
					List rlist = om.selectForListByMap(map,"t_goods.select_recommend_product");
					List<Map<String, String>> clist = new ArrayList<Map<String,String>>();
					Map<String, Object> cxobj = (Map<String, Object>) om.selectForObjectByMap(map,"t_goods.select_goods_promotion");
					TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
					String cx = sys.getKeys("cx");
					if(StringUtils.hasText(cx)){
						String[] cxs = cx.split(",");
						for (String str : cxs) {
							if(StringUtils.hasText(str)){
								Map<String, String> cmap = new HashMap<String, String>();
								cmap.put("cx", str);
								clist.add(cmap);
							}
						}
					}
					if(null!=cxobj&&0<cxobj.size()){
						Map<String, String> cmap = new HashMap<String, String>();
						String name = (String)cxobj.get("name");
						if(StringUtils.hasText(name)){
							cmap.put("cx", name);
							clist.add(cmap);
						}
					}
					List<Map<String, String>> plist = new ArrayList<Map<String,String>>();
					TGoodsImagesManager imanager = (TGoodsImagesManager) getSpringBean("tgoodsimagesmanager");
					TGoodsImagesExample example = new TGoodsImagesExample();
					example.createCriteria().andGoodsidEqualTo(pid).andUserTypeEqualTo(1);
					List<TGoodsImages> nlist = imanager.selectByExample(example);
					for (TGoodsImages bean : nlist) {
						Map<String, String> pmap = new HashMap<String, String>();
						pmap.put("image", bean.getArtworkUrl());
						plist.add(pmap);
					}
//					String purl=InfoUtil.getInstance().getInfo("config", "purl");
					String purl = sys.getKeys("purl");
					if(StringUtils.hasText(purl)){
						obj.setPurl(purl.replace("id",spid));
					}
					map.put("obj", obj);
					if(null!=clist&&0<clist.size()){
						map.put("clist", clist);
					}
					if(null!=plist&&0<plist.size()){
						map.put("plist", plist);
					}
					if(null!=rlist&&0<rlist.size()){
						map.put("rlist", rlist);
					}
					statusCode = "1";
				}else {
					statusCode = "0";
				}
			}else {
				statusCode = "0";
			}
		} catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	public static void main(String[] args) {
		System.out.println("http://111test.111yao.com/a1/a_id.html".replace("id","11111"));
	}
}
