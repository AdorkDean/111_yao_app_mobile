package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.PageResult;

public class DaiPinglunURIService extends BaseURIService {

	private static final long serialVersionUID = -6744444109004464429L;
	private Logger log = Logger.getLogger(getClass());
	private OpenSqlManage opensqlmanage;
	private TMemberManager tmembermanager;

	private PageWraper pw = new PageWraper();
	private PageResult rs = new PageResult();

	public static void main(String[] args) throws SQLException {

		DaiPinglunURIService o = new DaiPinglunURIService();
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
		o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");

		List<Map> list = o.opensqlmanage.selectForListByMap(null, "dst_order.dai_pinglun");
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
		tmembermanager = (TMemberManager) getSpringBean("tmembermanager");

		String sessionId = req.getParameter("sessionid");
		String page = req.getParameter("page");
		System.out.println("page1---->" + page);
		rs.setP_curPage(Integer.parseInt(page != null ? page : "1"));

		System.out.println("page2---->" + page);

		String statusCode = "1";
		try {
			long userId = tmembermanager.getMemberId(sessionId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", userId);
			rs.setP_pageSize(10);
			pw = opensqlmanage.selectForPageByMap(map, "t_goods_comment.dai_pinglun_count",
					"t_goods_comment.dai_pinglun", rs.getP_curPage(), rs.getP_pageSize());
		} catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		List<ResultData> ll = pw.getResult();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("statusCode", statusCode);
		m.put("list", ll);

		JSONObject json4 = JSONObject.fromObject(m);
		ConstantUtil.reJSON(json4.toString(), req, resp);

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public OpenSqlManage getOpensqlmanage() {
		return opensqlmanage;
	}

	public void setOpensqlmanage(OpenSqlManage opensqlmanage) {
		this.opensqlmanage = opensqlmanage;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	class ResultData {
		private String member;
		private String id;
		private String sn;
		private String create_date;
		private String order_price;
		private String imgurl;

		public String getMember() {
			return member;
		}

		public void setMember(String member) {
			this.member = member;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSn() {
			return sn;
		}

		public void setSn(String sn) {
			this.sn = sn;
		}

		public String getCreate_date() {
			return create_date;
		}

		public void setCreate_date(String create_date) {
			this.create_date = create_date;
		}

		public String getOrder_price() {
			return order_price;
		}

		public void setOrder_price(String order_price) {
			this.order_price = order_price;
		}

		public String getImgurl() {
			return imgurl;
		}

		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}

	}

}
