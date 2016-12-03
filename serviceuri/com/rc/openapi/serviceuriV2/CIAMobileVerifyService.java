package com.rc.openapi.serviceuriV2;

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

import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TMemberExample;
/**
 * CIA用户判断是否注册
 *
 */
public class CIAMobileVerifyService extends BaseURIService {
	private static final long serialVersionUID = 2L;
	private final Logger log = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
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
					 map.put("statusCode", "1");
				}else{
					map.put("statusCode", "2");
				}
			} catch (SQLException e) {
				map.put("statusCode", "0");
				e.printStackTrace();
			}
		}else{
			map.put("statusCode", "3");
		}
		JSON json = JSONSerializer.toJSON(map);
		ConstantUtil.reJSON(json.toString(), request, response);
	}
}
