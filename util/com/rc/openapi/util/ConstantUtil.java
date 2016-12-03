package com.rc.openapi.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.rc.openapi.model.ReMessage;
import com.thoughtworks.xstream.XStream;

/**
 * 
*    
* 类描述：  常量工具类 
* 创建人：hexiaoliang  
* 创建时间：2010-3-18 下午02:06:01  
* 修改人：  
* 修改时间：
* 修改备注：  
* @version   1.0
*
 */
public class ConstantUtil {
	/**
	 * 接口返回代码定义
	 */	
	/**
	 * 对外统一code含义
	 * 200：调用成功
	 * 500：服务端异常
	 * 400：请求参数有错
	 * 403：非法请求
	 * 404：资源不存在
	 */
	public static final String SUCCESS="200";					//调用成功
	public static final String EXCEPTION="500";     			//调用发生异常
	public static final String BEANISNULL="404";				//springBean为空
	public static final String APPKEYISNULL="400";				//appkey为空
	public static final String APPKEYNOTDEFINE="403";			//appkey未定义
	public static final String METHODISNULL="400";				//method为空
	public static final String METHODNOTDEFINE="403";			//method未定义
	public static final String CALLMETHODISNULL="403";			//不支持的callmethod
	public static final String VERSIONISNULL="400";				//version为空
	public static final String VERSIONERR="403";				//version错误
	public static final String SIGERR="403";					//sig错误
	public static final String USERIDERR="400";					//userid必须是数字
	public static final String ACCOUNTERR="400";				//金额必须是数字
	public static final String PROCEDUREERR="400";				//存储过程出错
	public static final String ORDERNOERR="400";				//订单号错误
	public static final String CASHERR="400";					//现金金额错误
	public static final String CARDERR="400";					//彩金金额错误
	public static final String TICKETERR="400";					//红包金额错误
	public static final String REQUESTSAME="403";				//请求重复
	public static final String PARAMERR="400";					//请求参数错误
	
	public static final String BANKCALLCHARGECHANNEL="5001";	//银联回呼充值方式
	
	public final static String PWD_KEY="jkr99.com";//密码加密值
	/**
	 * 
	*    
	* 方法描述：  返回接口调用结果 
	* 创建人：hexiaoliang  
	* 创建时间：2010-3-18 下午05:14:20    
	* @param error_code
	* @param req
	* @param res
	* @throws ServletException
	* @throws IOException
	* @version   1.0
	*
	 */
	public static void doErrorCode(String error_code, ServletRequest req,
			ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		if("xml".equals(ParamUtils.getRequestAPIFormat(request)))
			ConstantUtil.doErrorCodeXML(error_code, req, res);
		else if("json".equals(ParamUtils.getRequestAPIFormat(request)))
			ConstantUtil.doErrorCodeJson(error_code, req, res);
		else
			ConstantUtil.doErrorCodeString(error_code, req, res);
	}
	
	
	
	/**
	 * 
	*    
	* 方法描述：  接口调用结果返回string格式 
	* 创建人：hexiaoliang  
	* 创建时间：2010-3-18 下午05:14:20    
	* @param error_code
	* @param req
	* @param res
	* @throws ServletException
	* @throws IOException
	* @version   1.0
	*
	 */
	public static void doErrorCodeString(String error_code, ServletRequest req,
			ServletResponse res) throws ServletException, IOException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = null;
		out = response.getWriter();
		response.setStatus(Integer.valueOf(error_code));
		out.print("errorcode="+error_code);
		out.flush();
		out.close();
	}
	
	/**
	 * 
	*    
	* 方法描述：接口调用结果返回json格式  
	* 创建人：hexiaoliang  
	* 创建时间：2010-3-19 下午02:52:21    
	* @param error_code
	* @param req
	* @param res
	* @throws ServletException
	* @throws IOException
	* @version   1.0
	*
	 */
	public static void doErrorCodeJson(String error_code, ServletRequest req,
			ServletResponse res) throws ServletException, IOException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("errorcode", error_code);
		response.setStatus(Integer.valueOf(error_code));
		
		out.print(JSONObject.fromObject(map));
		out.flush();
		out.close();
	}
	
	/**
	 * 
	*    
	* 方法描述：接口调用结果返回xml格式
	* 创建人：hexiaoliang  
	* 创建时间：2010-3-19 下午02:52:21    
	* @param error_code
	* @param req
	* @param res
	* @throws ServletException
	* @throws IOException
	* @version   1.0
	*
	 */
	public static void doErrorCodeXML(String error_code, ServletRequest req,
			ServletResponse res) throws ServletException, IOException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		
		Document document = DocumentHelper.createDocument();
		Element roote = document.addElement("body");
		roote.addElement("errorcode").addText(error_code);
		response.setStatus(Integer.valueOf(error_code));
		
		out.print(document.asXML());
		out.flush();
		out.close();
	}
	
	public static void doErrorMsg(String msg, ServletRequest req,
			ServletResponse res) throws ServletException, IOException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();
		
		out.print(msg);
		out.flush();
		out.close();
	}
	
	
	
	/**
	 * @param errorCode
	 * @return 返回单一的错误码XML
	 * 
	 */
	public static String getXMLCode(String errorCode){
		ReMessage remsg=new ReMessage();
		remsg.setErrorcode(errorCode);
		XStream xstream = new XStream();
        xstream.alias("body", ReMessage.class);
        String xml = xstream.toXML(remsg); 
        return xml;
	}
	
	
	/**
	 * @param txt
	 * @param request
	 * @param response
	 * @throws IOException
	 * 给客户端返回xml
	 */
	public static void reXML(String txt,ServletRequest request,ServletResponse response) throws IOException{
		response.setContentType("application/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();		
		out.print(txt);
		out.flush();
		out.close();
	}
	/**
	 * @param txt
	 * @param request
	 * @param response
	 * @throws IOException
	 * 给客户端返回JSON
	 */
	public static void reJSON(String txt,ServletRequest request,ServletResponse response) throws IOException{
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		out = response.getWriter();		
		out.print(txt);
		out.flush();
		out.close();
	}


	/**
	 * JSON字符串转换成对象
	 *
	 * @param jsonString
	 *            需要转换的字符串
	 * @param type
	 *            需要转换的对象类型
	 * @return 对象
	 * 创建人：ws
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, Class<T> type)
	{
		if( StringUtil.isNull(jsonString) )
		{
			return  null;
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return (T) JSONObject.toBean(jsonObject, type);

	}

	/**
	 *
	 *
	 * 方法描述：接口调用结果返回json格式
	 * 创建人：ws
	 * @version   1.0
	 *
	 */
	public static String getJsonString(Object object)
	{
		String json = "";
		if( object == null )
		{
			return "";
		}
		if( object instanceof Collection)
		{
			json = JSONArray.fromObject(object).toString();
		}else {
			json = JSONObject.fromObject(object).toString();
		}

		return json;
	}


	/**
	 *
	 *
	 * 方法描述：接口调用结果返回json格式
	 * 创建人：ws
	 * @param req
	 * @param res
	 * @throws javax.servlet.ServletException
	 * @throws IOException
	 * @version   1.0
	 *
	 */
	public static void doResponseJson(Object object, ServletRequest req, ServletResponse res) throws ServletException, IOException
	{
		HttpServletResponse response = (HttpServletResponse) res;
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		if( object instanceof Collection)
		{
			out.print(JSONArray.fromObject(object));
		}else {
			out.print(JSONObject.fromObject(object));
		}
		out.flush();
		out.close();
	}

	/**
	 * 将json字符串转换成map对象
	 *
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String json) {
		JSONObject obj = JSONObject.fromObject(json);
		return jsonToMap(obj);
	}

	/**
	 * 将JSONObject转换成map对象
	 *
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> jsonToMap(JSONObject obj) {
		Set<?> set = obj.keySet();
		Map<String, Object> map = new HashMap<String, Object>(set.size());
		for (Object key : obj.keySet()) {
			Object value = obj.get(key);
			if (value instanceof JSONArray) {
				map.put(key.toString(), jsonToList((JSONArray) value));
			} else if (value instanceof JSONObject) {
				map.put(key.toString(), jsonToMap((JSONObject) value));
			} else {
				map.put(key.toString(), obj.get(key));
			}

		}
		return map;
	}


	/**
	 * 将JSONArray对象转换成list集合
	 *
	 * @param jsonArr
	 * @return
	 */
	public static List<Object> jsonToList(JSONArray jsonArr) {
		List<Object> list = new ArrayList<Object>();
		for (Object obj : jsonArr) {
			if (obj instanceof JSONArray) {
				list.add(jsonToList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				list.add(jsonToMap((JSONObject) obj));
			} else {
				list.add(obj);
			}
		}
		return list;
	}

	public static void main(String[] args){
//		boolean[] newArray = new boolean[]{true,false,true};
//		JSONArray json = JSONArray.fromObject(newArray);
//		System.out.println(json);
//		
//		List<String> list = new ArrayList<String>();          
//		list.add( "first" );          
//		list.add( "second" );          
//		JSONArray jsonArray2 = JSONArray.fromObject( list );          
//		System.out.println( jsonArray2 ); 
//		
//		JSONArray jsonArray3 = JSONArray.fromObject( "['json','is','easy']" );          
//		System.out.println( jsonArray3 );
//		
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("key","json");
//		map.put("test", "test");
//		
//		JSONObject json4 = JSONObject.fromObject(map);
//		System.out.println(json4);
//		
//		OpenApiForm form = new OpenApiForm();
//		form.setUserId("100");
//		form.setOrderNo("123ABC");
//		
//		JSONObject json5 = JSONObject.fromObject(form);
//		System.out.println(json5);
//		
//		OpenApiForm form1 = (OpenApiForm)JSONObject.toBean(json5,OpenApiForm.class);
//		System.out.println(form1.getAccount());
//		System.out.println(form1.getUserId());
		
	}

}
