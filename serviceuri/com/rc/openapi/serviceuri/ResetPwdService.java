package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MD5;
import com.rc.openapi.util.MemCached;
import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberExample;

/**
 * 重置密码
 * @author Administrator
 *
 */
public class ResetPwdService extends BaseURIService {

	private static final long serialVersionUID = 8300296124974369342L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map map = new HashMap();
		String vcode = request.getParameter("vcode");
		String mobile = request.getParameter("mobile");
		String newpwd = request.getParameter("newpwd");
		if(null != mobile  && !("").equals(mobile) && null != newpwd && !("").equals(newpwd)){
			String  code=	(String) MemCached.getmcc().get(MD5.MD5(mobile));
		   	if( null != code && !("").equals(code) && code.equals(vcode)){
		   			TMemberManagerImpl tmembermanager = (TMemberManagerImpl)getSpringBean("tmembermanager");
		   			TMemberExample dme = new TMemberExample();
					dme.createCriteria().andMobileEqualTo(mobile).andIsMobileCheckEqualTo(1);
					List list = null;
					try {
						list = tmembermanager.selectByExample(dme);
						if(null != list && list.size()==1){
							TMember mb = (TMember) list.get(0);
							TMember member = new TMember();
							member.setUserName(mb.getUserName());
							BASE64Decoder decoder = new BASE64Decoder();  
							byte[] b = decoder.decodeBuffer(newpwd); 
							newpwd = new String(b);
							mb.setPassword(MD5.MD5("111"+newpwd+"yao"));
							int i = tmembermanager.updateMemberPassword(mb, dme,map);
						}else{
							//手机号码不存在
							map.put("statusCode", "3");
						}
					} catch (SQLException e) {
						e.printStackTrace();
						map.put("statusCode", "0");
					}
			}else{  //验证码不匹配时 返回验证码错误
		    		map.put("statusCode", "2");
			}	
		}else{    //用户名或密码为空时返回错误提示
			map.put("statusCode", "4");
		}
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}
}
