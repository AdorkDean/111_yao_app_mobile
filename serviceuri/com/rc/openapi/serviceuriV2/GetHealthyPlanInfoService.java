package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
/**
 * 查询某品牌下的商品
 *
 */
public class GetHealthyPlanInfoService extends BaseURIService {

	private static final long serialVersionUID = 1110296124974369342L;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		Map<String, String> mapv = new HashMap<String, String>();
		mapv.put("id", id);
		String josns = "";
		try {
			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String healthyplaninfo = sys.getKeys("healthyplaninfo");
			josns = ClientSubmitPublic.getHealthyPlanInfo(mapv,healthyplaninfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ConstantUtil.reJSON(josns, request, response);
	}

}
