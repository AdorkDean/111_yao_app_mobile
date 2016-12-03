package com.rc.openapi.serviceuriV2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rc.openapi.service.HuanxinLogManager;
import com.rc.openapi.service.HuanxinUserManager;
import com.rc.openapi.serviceuri.BaseURIService;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.HuanxinLog;
import com.rc.openapi.vo.HuanxinUser;

import eaasemob.ClientSecretCredential;
import eaasemob.Constants;
import eaasemob.Credential;
import eaasemob.EasemobIMUsers;
import eaasemob.EndPoints;
import eaasemob.HTTPClientUtils;
import eaasemob.HTTPMethod;
import eaasemob.Roles;

public class HuanXinURIService extends BaseURIService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EasemobIMUsers.class);
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);
	
	private HuanxinUserManager huanxinusermanager;
	private HuanxinLogManager huanxinlogmanager;
	
	
	  // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);
    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String user = "tomcat1";
		
		System.out.println("====");
		
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",user);
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
      if (null != createNewIMUserSingleNode) {
          LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
      }
      JsonNode jn =   createNewIMUserSingleNode.get("statusCode");
      System.out.println(jn.toString());

        
        
        /**
         * 获取IM用户[主键查询]
         */
       
        /*ObjectNode getIMUsersByUserNameNode = getIMUsersByUserName(user);
        if (null != getIMUsersByUserNameNode) {
            LOGGER.info("获取IM用户[主键查询]: " + getIMUsersByUserNameNode.toString());
        }*/
        

	}
	
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		huanxinusermanager=(HuanxinUserManager)getSpringBean("huanxinusermanager");
		huanxinlogmanager=(HuanxinLogManager)getSpringBean("huanxinlogmanager");
		
		String user = request.getParameter("name");
		
		
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username",user);
        datanode.put("password", Constants.DEFAULT_PASSWORD);
        
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode);
        if (null != createNewIMUserSingleNode) {
            LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
        }
      
        try {
        	JsonNode jn =   createNewIMUserSingleNode.get("statusCode");
        	System.out.println("jn==========="+jn.toString());
        	
        	if("200".equals(jn.toString()))
        	{
        		
                HuanxinUser hUser = new HuanxinUser();
                hUser.setName(user);
                hUser.setCreateTime(new Date());
    			huanxinusermanager.insert(hUser);
    			
    			HuanxinLog log = new HuanxinLog();
    			log.setLog(createNewIMUserSingleNode.toString());
    			log.setCreateTime(new Date());
    			log.setComment("注册");
    			huanxinlogmanager.insert(log);
    			
        	}
        	
        	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /**
         * 获取IM用户[主键查询]
         */
       
//        ObjectNode getIMUsersByUserNameNode = getIMUsersByUserName(user);
//        if (null != getIMUsersByUserNameNode) {
//            LOGGER.info("获取IM用户[主键查询]: " + getIMUsersByUserNameNode.toString());
//        }
        
        JsonNode jn =   createNewIMUserSingleNode.get("statusCode");
        System.out.println(jn);
        
//        ConstantUtil.reJSON(createNewIMUserSingleNode.toString(), request, response);
        ConstantUtil.reJSON("{\"code\":\""+jn+"\"}", request, response);
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	    /**
		 * 注册IM用户[单个]
		 * 
		 * 给指定Constants.APPKEY创建一个新的用户
		 * 
		 * @param dataNode
		 * @return
		 */
		public static ObjectNode createNewIMUserSingle(ObjectNode dataNode) {

			ObjectNode objectNode = factory.objectNode();

			// check Constants.APPKEY format
			if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
				LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

				objectNode.put("message", "Bad format of Constants.APPKEY");

				return objectNode;
			}

			objectNode.removeAll();

			// check properties that must be provided
			if (null != dataNode && !dataNode.has("username")) {
				LOGGER.error("Property that named username must be provided .");

				objectNode.put("message", "Property that named username must be provided .");

				return objectNode;
			}
			if (null != dataNode && !dataNode.has("password")) {
				LOGGER.error("Property that named password must be provided .");

				objectNode.put("message", "Property that named password must be provided .");

				return objectNode;
			}

			try {

			    objectNode = HTTPClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode,
						HTTPMethod.METHOD_POST);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return objectNode;
		}
		
	
	/**
	 * 获取IM用户
	 * 
	 * @param userName
	 *            用户主键：username或者uuid
	 * @return
	 */
	public static ObjectNode getIMUsersByUserName(String userName) {
		ObjectNode objectNode = factory.objectNode();

		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			LOGGER.error("Bad format of Constants.APPKEY: " + Constants.APPKEY);

			objectNode.put("message", "Bad format of Constants.APPKEY");

			return objectNode;
		}

		// check properties that must be provided
		if (StringUtils.isEmpty(userName)) {
			LOGGER.error("The userName that will be used to query must be provided .");

			objectNode.put("message", "The userName that will be used to query must be provided .");

			return objectNode;
		}

		try {

			URL userPrimaryUrl = HTTPClientUtils
					.getURL(Constants.APPKEY.replace("#", "/") + "/users/" + userName);
			objectNode = HTTPClientUtils.sendHTTPRequest(userPrimaryUrl, credential, null, HTTPMethod.METHOD_GET);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectNode;
	}
	
	

}
