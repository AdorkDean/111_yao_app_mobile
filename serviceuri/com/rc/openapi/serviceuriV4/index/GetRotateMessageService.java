package com.rc.openapi.serviceuriV4.index;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.service.TSysParameterManager;
import com.rc.openapi.service.impl.CPositionManagerImpl;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;

/**
 * app首页消息轮播
 * 
 * @author WWF
 * @createTime 2016-7-4 上午9:30:14
 */
public class GetRotateMessageService extends BaseURIService {

	private static final long serialVersionUID = -7244436813879054581L;

	private final Logger log = Logger.getLogger(getClass());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String statusCode = "0";
		String message = "";
		TSysParameterManager sys = (TSysParameterManager) getSpringBean("tsysparametermanager");
		CPositionManagerImpl cPositionManagerImpl = (CPositionManagerImpl) getSpringBean("cpositionmanager");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		try {
			String cid = sys.getKeys("app_rotate_message_id");
			if (cid == null || "".equals(cid)) {// 系统文件未配置;
				log.error("位置管理id未配置");
				message = "服务器忙,请稍后重试";
				try {
					throw new Exception("位置管理id未配置");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				int id = Integer.valueOf(cid);
				CPositionExample cPositionExample = new CPositionExample();
				cPositionExample.setOrderByClause(" weight desc ");
				cPositionExample.createCriteria().andChannelEqualTo(id).andIssueEqualTo(1);
				List<CPosition> list = cPositionManagerImpl.selectByExample(cPositionExample);
				Map<String, Object> map = null;
				for (CPosition cPosition : list) {
					map = new HashMap<String, Object>();
					map.put("id", cPosition.getId());// id
					map.put("title", cPosition.getTitle() == null ? "" : cPosition.getTitle());// 标题
					map.put("message", cPosition.getTitleAll() == null ? "" : cPosition.getTitleAll());// 内容信息
					map.put("type", cPosition.getPrice() == null ? "" : cPosition.getPrice());//类型:1:ftl页面;2:h5页;
					map.put("url", cPosition.getTitleUrl() == null ? "" : cPosition.getTitleUrl());// 链接地址
					resultList.add(map);
				}
				statusCode = "1";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			message = "服务器忙,请稍后重试";
		}
		if ("0".equals(statusCode)) {
			resultMap.put("message", message);
		}
		resultMap.put("statusCode", statusCode);
		resultMap.put("list", resultList);
		JSONObject json4 = JSONObject.fromObject(resultMap);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
