package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.openapi.service.TChannelManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.vo.TChannel;

/**
 * 统计IP
 * 
 * @author user00
 * @createTime 2016-4-15 上午11:23:53
 */
public class StatisticsIPURIService extends BaseURIService {

	private static final long serialVersionUID = 3810393917175631043L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ip = getIpAddr(request);
		TChannel channel=new TChannel();
		channel.setSource(1);
		channel.setSourceIp(ip);
		channel.setCreateDate(new Date());
		TChannelManager tchannelmanager = (TChannelManager)getSpringBean("tchannelmanager");
		try {
			tchannelmanager.insertSelective(channel);
			System.out.println("入库成功=================================="+ip);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	   }

}
