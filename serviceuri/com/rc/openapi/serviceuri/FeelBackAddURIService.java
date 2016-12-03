package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.openapi.service.CFeedbackManager;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.vo.CFeedback;
/**
* 意见反馈接口
 *
 */
public class FeelBackAddURIService extends BaseURIService {

	private static final long serialVersionUID = 8300296124974369342L;
	private final Logger log = Logger.getLogger(getClass());
	
	private CFeedbackManager cfeedbackmanager;
	private TMemberManager tmembermanager;
	
	public static void main(String[] args) throws Exception {
			
		FeelBackAddURIService o = new FeelBackAddURIService();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
		o.cfeedbackmanager = (CFeedbackManager) context.getBean("cfeedbackmanager");
		
		CFeedback feed = new CFeedback();
		feed.setUserId(1);
		String s = "据库开发优化与管理\\xF0\\x9F\\x98\\x81\\xE4\\xBD";
		feed.setMessage(s);
		feed.setCreateDate(new Date());
		feed.setFromPhone("15910905145");
		
		int n = o.cfeedbackmanager.insert(feed);
		
		CFeedback f = o.cfeedbackmanager.selectByPrimaryKey(n);
		
	
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = null;
//		String sessionId = request.getParameter("sessionid");
		String content = request.getParameter("content");
		String fromPhone = request.getParameter("from_phone");
		String  net_type= request.getParameter("net_type");
		String system_type = request.getParameter("system_type");
		String client_type = request.getParameter("client_type");
		String  qudao_sn= request.getParameter("qudao_sn");
		String  qudao_name= request.getParameter("qudao_name");
		String  fenbielv= request.getParameter("fenbielv");
		String  equipment= request.getParameter("equipment");
		String  client_version = request.getParameter("client_version");
		
//		dstmembermanager=(DstMemberManager)getSpringBean("dstmembermanager");
		
//		long userId = dstmembermanager.getMemberId(sessionId);
		
		CFeedback feed = new CFeedback();
//		feed.setUserId(userId);
		feed.setMessage(content);
		feed.setCreateDate(new Date());
		feed.setFromPhone(fromPhone);
		
		feed.setNetType(net_type);
		feed.setSystemType(system_type);
		feed.setClientType(client_type);
		feed.setQudaoSn(qudao_sn);
		feed.setQudaoName(qudao_name);
		feed.setFenbielv(fenbielv);
		feed.setEquipment(equipment);
		feed.setClientVersion(client_version);
		feed.setModifyDate( new Date());
		
		long statusCode = 0;
		try {
			out = response.getWriter();
			statusCode = feedback(feed);
		} catch (Exception e) {
			statusCode = 0;
			e.printStackTrace();
		}
		if(statusCode>0) statusCode = 1;
		out.print("{\"statusCode\":"+"\""+statusCode+"\""+"}");
		if(out!=null)
		out.close();
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		try {
			doPost(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	 /**
     * 意见反馈
     * @param feed
     * @return
     * @throws SQLException
     */
    public long feedback(CFeedback feed) throws SQLException{
    	CFeedbackManager  cfeedbackmanager=(CFeedbackManager) getSpringBean("cfeedbackmanager");
    	return cfeedbackmanager.insertSelective(feed);
    }
    
    
    

}
