package com.rc.openapi.serviceuriV3;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmit;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.sms.SingletonClient;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;

/**
 * 快捷登录手机验证
 * @author Administrator
 *
 */
public class ShortcutMobileCheckoutService extends BaseURIService {

	private static final long serialVersionUID = 8300296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String mobile = request.getParameter("mobile");
		String uip = getRemoteHost(request);
		System.out.println("用户请求ip=="+uip);
		if(null != mobile && !("").equals(mobile)){
			try {
			  String ip = (String) MemCached.getmcc().get(MD5.MD5("ip"+uip));  //ip  通过用户传递的ID判断用户请求服务器的次数
			  if(null != ip && !("").equals(ip)){
				  if(ip.length()>30){
					  map.put("statusCode", "2");  //5分钟内请求10次 过于频繁
				  }else{
					  ip +="1";
					  MemCached.getmcc().set(MD5.MD5("ip"+uip),ip,new Date(1000*600));
					  String code =	getCode();
					  System.out.println("c==="+code);
					  MemCached.getmcc().set(MD5.MD5("shortcut"+mobile),code,new Date(1000*600));
					  Map<String, String> _map = new HashMap<String, String>();
					  _map.put("mobiles", mobile);
					  _map.put("smsContent", "您的111医药馆验证码："+code+"。影视明星何政军先生推荐的中国好药房@111医药馆！");
					  
					  
					  String YAO_GATEWAY_URL ="http://service.111yao.com/sltRouter?method=sms";
					  ClientSubmit.buildRequestBySMS(_map,YAO_GATEWAY_URL);
					  System.out.println("忘记密码，短信验证码：\t\t"+code);
					  map.put("statusCode", "1");
				  }
			  }else{
				  MemCached.getmcc().set(MD5.MD5("ip"+uip),"1",new Date(1000*600));
				  String code =	getCode();
				  System.out.println("c==="+code);
				  MemCached.getmcc().set(MD5.MD5("shortcut"+mobile),code,new Date(1000*600));
				  System.out.println("------------------"+new Date(1000*600));
				  Map<String, String> _map = new HashMap<String, String>();
				  _map.put("mobiles", mobile);
				  _map.put("smsContent", "您的111医药馆验证码："+code+"。影视明星何政军先生推荐的中国好药房@111医药馆！");
				  String YAO_GATEWAY_URL ="http://service.111yao.com/sltRouter?method=sms";
				  ClientSubmit.buildRequestBySMS(_map,YAO_GATEWAY_URL);
				  System.out.println("忘记密码，短信验证码：\t\t"+code);
				  map.put("statusCode", "1");
			  }
			} catch (Exception e) {
				map.put("statusCode", "0");
				e.printStackTrace();
			}
		}else{
			map.put("statusCode", "0");
		}
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}

	public String getCode(){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int random = (int) (Math.floor(Math.random() * 10));
			sb.append(String.valueOf(random));
		}
		String  code = sb.toString();
		return code;
	}
	
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
		   String ip = request.getHeader("x-forwarded-for");
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("Proxy-Client-IP");
				}
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getHeader("WL-Proxy-Client-IP");
				}
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
				ip = request.getRemoteAddr();
				}
		 return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	public static void main(String[] args) {
		int i=SingletonClient.getClient().sendSMS(new String[] {"15801313717"}, "您的密  码:11221。111医药馆,专做药品特卖的网站!","",5);//带扩展码
	    System.out.println(i);
	}
}
