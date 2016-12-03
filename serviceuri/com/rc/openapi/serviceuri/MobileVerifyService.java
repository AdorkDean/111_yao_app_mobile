package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import com.rc.dst.client.util.ClientSubmit;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.sms.SingletonClient;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.TMemberExample;

/**
 * 手机验证
 * @author Administrator
 *
 */
public class MobileVerifyService extends BaseURIService {

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
		if(null != mobile && !("").equals(mobile)){
			
			TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
			TMemberExample dme = new TMemberExample();
			dme.createCriteria().andMobileEqualTo(mobile).andIsMobileCheckEqualTo(1);
			List list = null;
			
			try {
				list = tmembermanager.selectByExample(dme);
				if(null == list || list.size()==0){
					String code =	getCode();
					 System.out.println("c==="+code);
					 MemCached.getmcc().set(MD5.MD5(mobile),code,new Date(1000*300));
					 Map<String, String> _map = new HashMap<String, String>();
						_map.put("mobiles", mobile);
						_map.put("smsContent", "您的111医药馆验证码："+code+"。影视明星何政军先生推荐的中国好药房@111医药馆！");
						String YAO_GATEWAY_URL ="http://service.111yao.com/sltRouter?method=sms";
						try {
							ClientSubmit.buildRequestBySMS(_map,YAO_GATEWAY_URL);
							System.out.println("忘记密码，短信验证码：\t\t"+code);
						} catch (Exception e) {
							e.printStackTrace();
						}
					 map.put("statusCode", "1");
				}else{
					map.put("statusCode", "2");
				}
			} catch (SQLException e) {
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
	
	public static void main(String[] args) {
		int i=SingletonClient.getClient().sendSMS(new String[] {"15801313717"}, "您的密  码:11221。111医药馆,专做药品特卖的网站!","",5);//带扩展码
	    System.out.println(i);
	}
}
