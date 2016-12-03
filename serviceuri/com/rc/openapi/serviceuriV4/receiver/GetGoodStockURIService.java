package com.rc.openapi.serviceuriV4.receiver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmitPublic;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.DataUtil;
import com.rc.openapi.vo.TMemberExample;

public class GetGoodStockURIService   extends BaseURIService {

		//http://localhost:8080/111_app_mobile/sltRouter?method=getGoodStockURIService&sessionId=8d93f2553996fa27a7efd259882217ef&goodid=1000&source=3&parnterid=1001
	
		private static final long serialVersionUID = 2774804583229896360L;
		private final Logger log = Logger.getLogger(getClass());
		
		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			try {
				String sessionId = request.getParameter("sessionId");
				if(null ==sessionId || sessionId.trim().length() == 0){
					DataUtil.returnResult("0","sessionId 不正确!", request, response,log);
					return;
				}
				TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
				Long memberId = tmembermanager.getMemberId(sessionId);
				if(memberId == null || memberId <= 0){
					DataUtil.returnResult("0","没有查到memberId!", request, response,log);
					return;
				}
				//接收参数
				Long goodid = DataUtil.getLongVal(request.getParameter("goodid")); 
				int source = DataUtil.getIntVal( request.getParameter("source"));//来源  1PC 2 WAP 3 app
				
				
				
				if(goodid <= 0){
					DataUtil.returnResult("0","goodid 不正确!", request, response,log);
					return;
				}
				
				//需要的service
				TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
				TMemberExample tmemberExample = new TMemberExample();
				tmemberExample.createCriteria().andIdEqualTo(memberId);
				int memberCnt;
				memberCnt = tmembermanager.countByExample(tmemberExample);
				if(memberCnt <= 0){
					DataUtil.returnResult("0","会员 memberId 不存在!", request, response,log);
					return;
				}
				if(source <= 0){
					DataUtil.returnResult("0","订单来源不能为null", request, response,log);
					return;
				}
				
				
				String homePageConfigId = sys.getKeys("public_service_url");
//				String homePageConfigId = "http://localhost:8080/111_yao_public/sltRouter?method=";
				Map<String, String> publicMap = new HashMap<String, String>();
				publicMap.put("memberId", memberId.toString());
				publicMap.put("goodid", goodid.toString());
				publicMap.put("source", source+"");
				String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
						+ "getGoodStockURIService");
				JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
				ConstantUtil.reJSON(jsonObject.toString(), request, response);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				DataUtil.returnResult("-1","系统异常", request, response,log);
			}
		}
		
		@Override
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			doGet(request, response);
		}
}
