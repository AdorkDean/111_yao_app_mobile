package com.rc.openapi.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.util.PageResult;

public class PageDemo {

	private OpenSqlManage opensqlmanage;
	
	private PageWraper pw = new PageWraper();
	private PageResult rs = new PageResult();
	
	
	
    public static void main(String[] args) throws SQLException{
        
	 PageDemo o = new PageDemo();
     ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
     o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
     o.page();
     System.exit(0);
     
    }
    
    
    public void page(){
	     Map map = new HashMap();
	     map.put("title", "中国"); //  查询条件
	     rs.setP_curPage(1);	// 第一页数据
	     rs.setP_pageSize(5); //  每页显示5条，这里不设置，默认为20条
	     pw = opensqlmanage.selectForPageByMap(map, "c_position.page_demo_count", "c_position.page_demo", rs.getP_curPage(), rs.getP_pageSize());
	    List<Map> result = pw.getResult();
	    for(Map m : result){
	    	System.out.println(m.get("id")+"---"+m.get("title"));
	    }
    }
    

}
