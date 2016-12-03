package com.rc.openapi.serviceuriV5.receiver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rc.openapi.cache.ACacheClient;
import com.rc.openapi.config.CacheKeysConfigProperties;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.StringUtil;

/**
 * 根据地区id当作parentId获取下一级区域
 * @author ws
 *
 */
public class GetDistrictsInfoByIdService extends BaseURIService {
	private static final long serialVersionUID = 3955056266546895690L;
	private final Logger log = Logger.getLogger(getClass());
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");//id
		String type = request.getParameter("type");//type

		String statusCode = "";
		String errorMessage ="";
		String value = "";
		try {

			if( StringUtil.isNull( id ) )
			{
				id = "-1";
			}

			String key = CacheKeysConfigProperties.getKey_sub_districts_of_parentId( id );

			if( "sub_districts_of_parentId".equalsIgnoreCase( type ) )
			{
				key = CacheKeysConfigProperties.getKey_sub_districts_of_parentId( id );

			}else if( "all_sub_districts_of_parentId".equalsIgnoreCase( type)) {

				key = CacheKeysConfigProperties.getKey_all_sub_districts_of_parentId( id);

			}else if( "district_self_value_without_subs".equalsIgnoreCase( type ) ){

				key = CacheKeysConfigProperties.getKey_district_self_value_without_subs( id );

			}

			Object valueObj = ACacheClient.get( key );

			value = "" + valueObj;
			if( StringUtil.isNull( value ) )
			{
				value = "{}";
			}
			statusCode = "1";
			errorMessage = "ok";

		} catch (Exception e) {
			statusCode="0";
			errorMessage="服务器异常,请稍后再试";
			e.printStackTrace();
		}


		String retJson = "{\"statusCode\":\"" + statusCode + "\", \"errorMessage\":\"" + errorMessage + "\", \"value:\":" + value + " }";
		ConstantUtil.reJSON( retJson, request, response );



	}
	
}
