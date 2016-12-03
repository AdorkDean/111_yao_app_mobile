package com.rc.openapi.serviceuriV4.category;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.MemCached;

/**
 * 开发测试（手动清分类缓存,清V4版本全部分类的缓存）
 * 
 * @author WWF
 * @createTime 2016-7-25 上午10:59:18
 */
public class TestUtilService extends BaseURIService {
	private static final long serialVersionUID = -6726191295157971181L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String keyName = request.getParameter("keyName");
		if(type!=null&&"V4".equals(type)){
//			MemCached.getmcc().add("New_ProductCategory", "", new Date(1000 * 60 * 5));
			MemCached.getmcc().delete("New_ProductCategory");
			if(keyName!=null&&!"".equals(keyName)){
				MemCached.getmcc().delete(keyName);
			}
			ConstantUtil.reJSON("清空成功", request, response);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
