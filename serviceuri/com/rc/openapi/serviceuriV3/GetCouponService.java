package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;

/**
 * 获取领劵中心的优惠劵
 */
public class GetCouponService extends BaseURIService {

	private static final long serialVersionUID = -7309725851221418605L;
	private final Logger log = Logger.getLogger(getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("1=========="+new Date());
		TSysParameterManager tsysparametermanager = (TSysParameterManager) getSpringBean("tsysparametermanager");
		Long memberId = null ;
		try {
			String sessionId = request.getParameter("sessionId");// 用户Key
			if(null !=sessionId && !("").equals(sessionId)){   //判断用户是否存在
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				memberId =  tmembermanager.getMemberId(sessionId);  //获取用户id
			}
           //String url = "http://localhost:8082/111_yao_common/sltRouter?method=";
		   //String url = "http://test88.111yao.cn/111_yao_common/sltRouter?method=";
           TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
           String url = sys.getKeys("public_service_url");
           Map<String, String> publicMap = new HashMap<String, String>();
           publicMap.put("member_id", String.valueOf(memberId));
           System.out.println("2=========="+new Date());
			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, url
					+ "couponListService");
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			System.out.println("3=========="+new Date());
			String str = "1";
			if(null != jsonObject){
				 System.out.println("resultJsonstr=="+resultJsonstr);
				 str = jsonObject.get("list").toString();
			}
			System.out.println("str=="+str);
			ConstantUtil.reJSON(str, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return;
	}

}
