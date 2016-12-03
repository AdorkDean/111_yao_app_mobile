package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.model.ReviewModel;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TGoodsCommentManager;
import com.rc.openapi.util.MySub;
import com.rc.openapi.util.PageResult;
import com.rc.openapi.vo.TGoodsComment;
import com.rc.openapi.vo.TGoodsCommentExample;

public class GetPingLunAjaxURIService extends BaseURIService {

	private OpenSqlManage opensqlmanage;
	private TGoodsCommentManager tgoodscommentmanager;

	private PageWraper pw = new PageWraper();
	private PageResult rs = new PageResult();
	
	int pageSize=10;
	
	
	public static void main(String[] args) throws SQLException {
//		GetPingLunAjaxURIService o = new GetPingLunAjaxURIService();
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
//		o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
//		o.dstreviewmanager = (DstReviewManager) context.getBean("dstreviewmanager");
		
		double fenzi = 0;
		  String percent = (int)((fenzi==0?1:fenzi)*100)+"%";
		  
		  System.out.println(percent);
		  
		  
	}
	
	
	


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		doGet(req, resp);
	}
	
	
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		
		opensqlmanage=(OpenSqlManage)getSpringBean("opensqlmanage");
		tgoodscommentmanager=(TGoodsCommentManager)getSpringBean("tgoodscommentmanager");
		
		resp.setContentType("text/html;charset=utf-8");
		 PrintWriter out = null;
		 Map m = new HashMap(); 
		 String statusCode = "1";
		try {
			out = resp.getWriter();
			String productId = req.getParameter("productid");
			String flag = req.getParameter("flag");
			
			System.out.println("productId==============="+productId);
			
			String page = req.getParameter("page");
			rs.setP_curPage(Integer.parseInt(page!=null?page:"1"));
				
			Map map = new HashMap();
			map.put("productid", Long.parseLong(productId));
			map.put("flag", flag);
			map.put("page",page);
			
			rs.setP_pageSize(10);
		
			pw  = opensqlmanage.selectForPageByMap(map,"t_goods_comment.get-product-pinglun-count","t_goods_comment.get-product-pinglun",rs.getP_curPage(), rs.getP_pageSize());
		    
			List<ReviewModel> temp = pw.getResult();
			
			// 加*过程 
			String str = "";
			for(ReviewModel mp : temp){
				str = MySub.mySubString(mp.getUsername());
				mp.setUsername(str);
			}
			
			List<ReviewModel> list = pw.getResult();
			
			
		    float  peopleCount = 0;//(Integer) opensqlmanage.selectForObjectByMap(map, "n_pinglun.review_people"); 评论人数
		    float  badCount = 0;//(Integer) opensqlmanage.selectForObjectByMap(map, "n_pinglun.bad-count");  差评
		    float  goodCount = 0;//(Integer) opensqlmanage.selectForObjectByMap(map, "n_pinglun.good-count"); 中评
		    float  bestCount = 0;//(Integer) opensqlmanage.selectForObjectByMap(map, "n_pinglun.best-count");  好评
		    
		    TGoodsCommentExample example = new TGoodsCommentExample();
		    example.createCriteria().andGoodsIdEqualTo(Long.parseLong(productId));
		    List<TGoodsComment> TGoodsCommentList = tgoodscommentmanager.selectByExample(example);
		    for(TGoodsComment com : TGoodsCommentList)
		    {
		    	peopleCount ++ ;
		    	if(com.getType()==1){
		    		bestCount++;
		    	}
		    	if(com.getType()==2){
		    		goodCount++;
		    	}
		    	if(com.getType()==3){
		    		badCount++;
		    	}
		    }
		    
			// 好评率
		    Float fenzi = 1f;
		    try{
			 fenzi =(float)((bestCount+goodCount)/peopleCount); //(Float)opensqlmanage.selectForObjectByMap(map, "t_goods_comment.score-percent");
			if(fenzi==null){
				fenzi=1f;
			}
			System.out.println("11111111111=="+(bestCount+goodCount));
			System.out.println("22222222=="+peopleCount);
			
			System.out.println("=========>"+(2/3));
			
			
		    }catch(java.lang.ArithmeticException e){
		    	fenzi = 1f;
		    	e.printStackTrace();
		    }
			
			String percent = (int)(fenzi*100)+"%";
		    
		    System.out.println("list size="+list.size());
		    
		    m.put("list", list);
		    m.put("fenzi", percent);
		    m.put("page", page);
		    m.put("people", peopleCount);
		    m.put("badcount", ((int)badCount)+"");
		    m.put("goodcount", ((int)goodCount)+"");
		    m.put("bestcount", ((int)bestCount)+"");
		    
		} catch (IOException e1) {
			statusCode = "0";
			e1.printStackTrace();
		} catch (SQLException e) {
			statusCode = "0";
			e.printStackTrace();
		}
		
		m.put("statusCode", statusCode);
		JSONObject json =  JSONObject.fromObject(m);
		out.print(json.toString());
		out.close();
		
	}


	public OpenSqlManage getOpensqlmanage() {
		return opensqlmanage;
	}


	public void setOpensqlmanage(OpenSqlManage opensqlmanage) {
		this.opensqlmanage = opensqlmanage;
	}


	
	

}
