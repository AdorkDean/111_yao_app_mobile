package com.rc.openapi.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

public class DataUtil {

	public static double getDoubleVal(Object data){
		double doubleVal = 0;
		try{
			doubleVal = Double.valueOf((String)data);
		}catch(Exception e){
			return 0;
		}
		
		return doubleVal;
		
	}
	
	public static int getIntVal(Object data){
		int intVal = -1;
		try{
			intVal = Integer.valueOf((String)data);
		}catch(Exception e){
			return -1;
		}
		
		return intVal;
		
	}
	
	public static long getLongVal(Object data){
		long val = 0;
		try{
			val = Long.valueOf((String)data);
		}catch(Exception e){
			return 0;
		}
		
		return val;
		
	}
	
	public static BigDecimal getBigDecimalVal(BigDecimal data){
		long val = 0;
		try{
			if(data == null){
				return new BigDecimal(0);
			}
			return data;
		}catch(Exception e){
			return new BigDecimal(0);
		}
		
		
	}
	
	public static BigDecimal getBigDecimalFromString(String data){
		try{
			if(data == null || data.trim().length() == 0){
				return new BigDecimal(-1);
			}else{
				return new BigDecimal(data);
			}
		}catch(Exception e){
			return new BigDecimal(-1);
		}
		
		
	}
	
	/*
	 * 把以逗号拼接的ids转化为long数组,如果包含非正常参数，返回null
	 * ids 如 1,2,3
	 * return Long[]  [1,2,3]
	 */
	public static Long[] getLongArrayFromString(String ids){
		if(ids == null){
			return null;
		}
		String[] idsStrArray = ids.split(",");
		Long[] idsLongArray = new Long[idsStrArray.length];
		for(int i=0; i<idsStrArray.length; i++){
			String temp = idsStrArray[i].trim();
			Long a = getLongVal(temp);
			if(a == 0){
				return null;
			}else{
				idsLongArray[i] = a;
			}
		}
		
		return idsLongArray;
	}
	
	public static void returnJsonStrFromObject(Object obj,HttpServletRequest request, HttpServletResponse response){
		JSONObject json4 = JSONObject.fromObject(obj);
		try {
			ConstantUtil.reJSON(json4.toString(), request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void returnResult(String statusCode,String message,HttpServletRequest request, HttpServletResponse response,Logger log){
		log.debug(message);
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("statusCode", statusCode);
		resultMap.put("message", message);
		DataUtil.returnJsonStrFromObject(resultMap, request, response);
	}
	public static void returnStateResult(String statusCode,String message,HttpServletRequest request, HttpServletResponse response,Logger log){
		log.debug(message);
		Map<String,Object> resultMap =new HashMap<String,Object>();
		resultMap.put("state", statusCode);
		resultMap.put("message", message);
		DataUtil.returnJsonStrFromObject(resultMap, request, response);
	}
	public static String getTrimValue(String str){
		if(str == null){
			return null;
		}
		return str.trim();
	}
	
	public static Date getDateByString(String dateStr){
		if(dateStr == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
		
		return date;
		
	}
	
	public static String getDateStrByObject(Object dateObj){
		if(dateObj == null){
			return null;
		}
		String dateStr;
		try {
			dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateObj);
		} catch (Exception e) {
			return null;
		}
		
		return dateStr;
		
	}
	/**
	 * 
	 * @param sex 男  or 女
	 * @return 1 男 2 女
	 */
	public static Integer parseSexFromStringToInt(String sex){
		if(sex == null){
			return null;
		}
		
		if(sex.trim().equals("男")){
			return 1;
		}
		if(sex.trim().equals("女")){
			return 2;
		}
		return null;
	}
	
	public static String getRoundPrice(Object priceObj){
		java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
		
		try{
			String price = String.valueOf(priceObj);
			BigDecimal   b   =   new   BigDecimal(price);  
			double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
			return df.format(f1);
		} catch (Exception e){
			BigDecimal   b   =   new   BigDecimal("0");  
			double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
			return df.format(f1);
		}
	}
	
	/**
	 * 
	*    
	* 方法描述：验证手机号   
	* 创建人：hexiaoliang  
	* 创建时间：2010-4-9 下午08:41:50    
	* @param str
	* @return
	* @version   1.0
	*
	 */
	public static boolean validateMobile(String str){
		return str.matches("[1]\\d{10}");
	}
	public static void main(String[] args) {
		System.out.println(getDateByString("1986-03-05 21:11:11"));
		System.out.println(getRoundPrice("0"));
	}
}
