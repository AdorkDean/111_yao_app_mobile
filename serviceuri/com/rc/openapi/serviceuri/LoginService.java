package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.CustomDigestUtils;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.util.NetworkUtil;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberExample;

/**
 * 登录
 * @author Administrator
 *
 */
public class LoginService extends BaseURIService {

	private static final long serialVersionUID = 8312396122974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String newpassword = null;
		if(null  != mobile && !("").equals(mobile) && null != password && !("").equals(password) ){
			String stop = (String)MemCached.getmcc().get(MD5.MD5(mobile+"stop"));
			if(null != stop){
				map.put("statusCode", "2");
				map.put("reminder","密码输入5次错误，账号冻结20分钟！");
			}else{
				
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
				Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$"); 
				Matcher m = p.matcher(mobile);   //判断用户名是否是手机号码
				TMember member = new TMember();
				TMemberExample example = null;
				try {
				    if(m.matches()){
					    example = new TMemberExample();
						example.createCriteria().andMobileEqualTo(mobile).andIsMobileCheckEqualTo(1);
						List mlist = tmembermanager.selectByExample(example);
						if(null != mlist && mlist.size()==1){
							member = (TMember) mlist.get(0);
							BASE64Decoder decoder = new BASE64Decoder();  
							byte[] b = decoder.decodeBuffer(password); 
							password = CustomDigestUtils.md5Hex(new String(b),member);
							newpassword = MD5.MD5("111"+new String(b)+"yao");
	//						password = CustomDigestUtils.md5Hex(password,member);
							if(member.getStatus()==1){
								map.put("statusCode", "4");
							}else if(!member.getPassword().equals(password)&& !member.getPassword().equals(newpassword)){
								//用户名或密码不正确
								String key = MD5.MD5(mobile+"err");
								Integer  err=	(Integer) MemCached.getmcc().get(key);
								if(null != err ){
									if(err>4){
										MemCached.getmcc().set(MD5.MD5(mobile+"stop"),"stop",new Date(1000*1200));
									}else{
										MemCached.getmcc().set(key,err+1,new Date(1000*100));
									}
								}else{
									//MemCached.getmcc().set(key,1,new Date(1000*100));
								}
								map.put("statusCode", "2");
								map.put("reminder", "");
							}else{
								member.setLastIp(NetworkUtil.getIpAddress(request));
								if(member.getPassword().equals(password)){  //判断用户是否使用的是旧版密码规则加密，如是旧版换成新版
									member.setPassword(newpassword);       
									tmembermanager.updateMember(member,example,map);
								}else{
									tmembermanager.updateMemberToSession(member,example,map);
								}
								map.put("statusCode", "1");
							}
						}else{   //判断手机号码不存在，在判断一下是否是用户名登录
							example = new TMemberExample();
							example.createCriteria().andUserNameEqualTo(mobile);
							List list  = tmembermanager.selectByExample(example);
							if(null != list && list.size()==1){
								member = (TMember) list.get(0);
								BASE64Decoder decoder = new BASE64Decoder();  
								byte[] b = decoder.decodeBuffer(password); 
								password = CustomDigestUtils.md5Hex(new String(b),member);
								newpassword = MD5.MD5("111"+new String(b)+"yao");
//								password = CustomDigestUtils.md5Hex(password,member);
								if(member.getStatus()==1){
									map.put("statusCode", "4");
								}else if(!member.getPassword().equals(password)&& !member.getPassword().equals(newpassword)){
									//用户名或密码不正确
									String key = MD5.MD5(mobile+"err");
									Integer  err=	(Integer) MemCached.getmcc().get(key);
									if(null != err ){
										if(err>4){
											MemCached.getmcc().set(MD5.MD5(mobile+"stop"),"stop",new Date(1000*1200));
										}else{
											MemCached.getmcc().set(key,err+1,new Date(1000*100));
										}
									}else{
										MemCached.getmcc().set(key,1,new Date(1000*100));
									}
									map.put("statusCode", "2");
									map.put("reminder", "");
								}else{
									member.setLastIp(NetworkUtil.getIpAddress(request));
									if(member.getPassword().equals(password)){  //判断用户是否使用的是旧版密码规则加密，如是旧版换成新版
										member.setPassword(newpassword);       
										tmembermanager.updateMember(member,example,map);
									}else{
										tmembermanager.updateMemberToSession(member,example,map);
									}
									map.put("statusCode", "1");
								}
							}else{
								map.put("statusCode", "5"); //用户未注册
							}
						}
				    }else{  //用户名登录
				    	example = new TMemberExample();
						example.createCriteria().andUserNameEqualTo(mobile);
						List list  = tmembermanager.selectByExample(example);
						if(null != list && list.size()==1){
							member = (TMember) list.get(0);
							BASE64Decoder decoder = new BASE64Decoder();  
							byte[] b = decoder.decodeBuffer(password); 
							password = CustomDigestUtils.md5Hex(new String(b),member);
							newpassword = MD5.MD5("111"+new String(b)+"yao");
//							password = CustomDigestUtils.md5Hex(password,member);
							if(member.getStatus()==1){
								map.put("statusCode", "4");
							}else if(!member.getPassword().equals(password)&& !member.getPassword().equals(newpassword)){
								//用户名或密码不正确
								String key = MD5.MD5(mobile+"err");
								Integer  err=	(Integer) MemCached.getmcc().get(key);
								if(null != err ){
									if(err>4){
										MemCached.getmcc().set(MD5.MD5(mobile+"stop"),"stop",new Date(1000*1200));
									}else{
										MemCached.getmcc().set(key,err+1,new Date(1000*100));
									}
								}else{
									MemCached.getmcc().set(key,1,new Date(1000*100));
								}
								map.put("statusCode", "2");
								map.put("reminder", "");
							}else{
								member.setLastIp(NetworkUtil.getIpAddress(request));
								if(member.getPassword().equals(password)){  //判断用户是否使用的是旧版密码规则加密，如是旧版换成新版
									member.setPassword(newpassword);       
									tmembermanager.updateMember(member,example,map);
								}else{
									tmembermanager.updateMemberToSession(member,example,map);
								}
								map.put("statusCode", "1");
							}
						}else{
							map.put("statusCode", "5"); //用户未注册
						}
				    }
				} catch (Exception e) {
					e.printStackTrace();
					map.put("statusCode", "0");
				}
			}
		}else{  //用户名 或密码为空时返回 用户名或密码错误
			map.put("statusCode", "3");
		}
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}
	
	public static void main(String[] args) {
		boolean s = MemCached.getmcc().delete(MD5.MD5("18610806042stop"));
		//System.out.println(s);
		
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$"); 
		Matcher m = p.matcher("14912312311"); 
		System.out.println(m.matches());
		
//		String s = "id=123&name=wangming&age=30";
//		String s1 = new sun.misc.BASE64Encoder().encode(s.getBytes());
//		System.out.println("s1=="+s1);
//		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//	     try {  
//	            byte[] b = decoder.decodeBuffer(s1);  
//	            System.out.println("b==="+new String(b));
//	        } catch (Exception e) {  
//	     }
//		
//		DstMember member = new DstMember();
//		member.setUsername("15210606202");
//		String password = "d3d3dzExMTE=";
//		BASE64Decoder decoder = new BASE64Decoder();  
//		byte[] b;
//		try {
//			b = decoder.decodeBuffer(password);
//			password = new String(b);
//			System.out.println(password);
//			System.out.println(CustomDigestUtils.md5Hex(new String(b),member));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
	}
}
