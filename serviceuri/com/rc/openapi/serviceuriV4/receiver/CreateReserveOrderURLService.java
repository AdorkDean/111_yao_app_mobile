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
import com.rc.openapi.util.ValidateUtil;

public class CreateReserveOrderURLService   extends BaseURIService {

	private static final long serialVersionUID = -92899100104281772L;
	private final Logger log = Logger.getLogger(getClass());
	
	
	//localhost:8080/111_app_mobile/sltRouter?method=createReserveOrderURLService&parnterid=1001&sessionId=8d93f2553996fa27a7efd259882217ef&goodid=1000&num=1&replyMobile=13788888888&source=3&receiverId=575&remark=remark&imgData=/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCAErAcIDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD0VoFQ55yORVeS4bdgDPrgV0LWwPXAphskz3zXW7HCrmLHM3eM81OJSuMIea1PsS96UWCmp5S
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
			String sessionId = request.getParameter("sessionId");
			if(null ==sessionId || sessionId.trim().length() == 0){
				DataUtil.returnResult("0","sessionId 不正确!", request, response,log);
				return;
			}
			
			TMemberManager tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
			Long memberId = tmembermanager.getMemberId(sessionId);
			if(memberId == null || memberId <= 0){
				DataUtil.returnStateResult("0","没有查到memberId!", request, response,log);
				return;
			}
			
			//接收参数
			Long receiverId = DataUtil.getLongVal(request.getParameter("receiverId"));
			Long goodid = DataUtil.getLongVal(request.getParameter("goodid")); 
			int num = DataUtil.getIntVal(request.getParameter("num"));
			String replyMobile = request.getParameter("replyMobile");
			String remark = request.getParameter("remark");
			String rxImgUrl = DataUtil.getTrimValue(request.getParameter("rxImgUrl"));
			String imgData = DataUtil.getTrimValue(request.getParameter("imgData"));
			int source = DataUtil.getIntVal( request.getParameter("source"));//来源  1PC 2 WAP
			
			
			//校验参数
			if(receiverId == 0){
				DataUtil.returnResult("0","请选择收获地址", request, response,log);
				return;
			}
			
			
			if(goodid <= 0){
				DataUtil.returnResult("0","goodid 不正确!", request, response,log);
				return;
			}
			
			if(num <= 0){
				DataUtil.returnResult("0","num 不正确!", request, response,log);
				return;
			}
			if(!ValidateUtil.validateMobile(replyMobile)){
				DataUtil.returnResult("0","回拨电话不正确!", request, response,log);
				return;
			}
			if(source <= 0){
				DataUtil.returnResult("0","订单来源不能为null", request, response,log);
				return;
			}
			
			TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
			String homePageConfigId = sys.getKeys("public_service_url");
//			String homePageConfigId = "http://localhost:8080/111_yao_public/sltRouter?method=";
			Map<String, String> publicMap = new HashMap<String, String>();
			publicMap.put("receiverId", receiverId.toString());
			publicMap.put("goodid", goodid.toString());
			publicMap.put("num", num+"");
			publicMap.put("memberId", memberId+"");
			publicMap.put("source", source+"");
			publicMap.put("replyMobile", replyMobile);
			publicMap.put("remark", remark);
			publicMap.put("rxImgUrl", rxImgUrl);//处方药图片路径 wap pc端使用
			publicMap.put("imgData", imgData);//处方药图片 app传送
			String resultJsonstr = ClientSubmitPublic.getPublicService(publicMap, homePageConfigId
					+ "createReserveOrderURLService");
			JSONObject jsonObject = JSONObject.fromObject(resultJsonstr);
			
			if(jsonObject.getString("rxTReserveOrderId") != null ){
				if(jsonObject.getString("rxTReserveOrderId").equals("-1000")){
					DataUtil.returnResult("2","库存不足", request, response,log);
					jsonObject.put("rxTReserveOrderId", null);
				}else if(jsonObject.getString("rxTReserveOrderId").equals("-1001")){
					DataUtil.returnResult("3","商品不存在", request, response,log);
					jsonObject.put("rxTReserveOrderId", null);
				}else if(jsonObject.getString("rxTReserveOrderId").equals("-1002")){
					DataUtil.returnResult("4","商品已经下架", request, response,log);
					jsonObject.put("rxTReserveOrderId", null);
				}
			}
			
			ConstantUtil.reJSON(jsonObject.toString(), request, response);
			
			
		}catch(Exception e){
			e.printStackTrace();
			DataUtil.returnResult("-1","系统异常", request, response,log);
			return;
		}
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
