package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.rc.openapi.service.AppMemberTraceManager;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.AppMemberTrace;
import com.rc.openapi.vo.AppMemberTraceExample;

/**
 * 装机使用率
 * @author user3
 *
 */
public class InstallUseService extends BaseURIService{
	private static final long serialVersionUID = -4237459128629991832L;
	private final Logger log = Logger.getLogger(getClass());
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uuid = request.getParameter("uuid");//设备uuid
		String net_type = request.getParameter("nettype");//网络类型
		String system_type = request.getParameter("systemtype");//系统版本号
		String client_type = request.getParameter("clienttype");//客户端类型
		String channel_sn = request.getParameter("channelsn");//渠道号
		String channel_name = request.getParameter("channelname");//渠道名称
		String ratio = request.getParameter("ratio");//分辨率
		String client_version = request.getParameter("clientversion");//客户端版本号
		Map<String,String> map = new HashMap<String,String>();
		String statusCode = "";
        if(StringUtils.hasText(uuid)){
        	try{
        		AppMemberTraceExample traceExample = new AppMemberTraceExample();
        		AppMemberTraceManager tracemanager = (AppMemberTraceManager) getSpringBean("appmembertracemanager");
        		traceExample.createCriteria().andUuidEqualTo(uuid);
        		List<AppMemberTrace> traceList =tracemanager.selectByExample(traceExample);
        		if(traceList!=null&&traceList.size()>0){
        			AppMemberTrace trace  = traceList.get(0);
        			traceExample = new AppMemberTraceExample();
        			traceExample.createCriteria().andIdEqualTo(trace.getId());
        			trace.setCreateDate(null);
        			trace.setLastLoginDate(new Date());
        			trace.setNetType(net_type);
        			trace.setSystemType(system_type);
        			trace.setClientType(client_type);
        			trace.setChannelSn(channel_sn);
        			trace.setChannelName(channel_name);
        			trace.setRatio(ratio);
        			trace.setUuid(null);
        			trace.setClientVersion(client_version);
        			tracemanager.updateByExampleSelective(trace, traceExample);
        		}else{
        			AppMemberTrace trace = new  AppMemberTrace(new Date(),
        					new Date(), net_type, system_type,
        					client_type, channel_sn, channel_name,
        					ratio, uuid, client_version);
        			tracemanager.insert(trace);
        		}
        		statusCode = "1";// 成功
        	}catch(Exception e){
        		statusCode = "0";// 失败
        		e.printStackTrace();
        	}
        }else{
        	statusCode="2";//参数uuid 不能为空
        }
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}
	
}
