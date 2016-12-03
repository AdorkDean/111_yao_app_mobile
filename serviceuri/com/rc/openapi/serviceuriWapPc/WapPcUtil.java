package com.rc.openapi.serviceuriWapPc;

import java.util.Map;

/**
 * 功能：工具类
 * 作者：LGP
 * 时间：2016年8月18日
 */
public class WapPcUtil 
{
	/**封装MAP参数*/
	public static Map<String, Object> setParamInMap(Map<String, Object> map, String param)
	{
		if(param.equals("wap"))
		{
			map.put("isWap", 1);
		}
		else
		{
			map.put("isPc", 1);
		}
		return map;
	}
}
