package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TGoodsCommentManager;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.vo.TGoodsComment;

public class AddPingLunURIService extends BaseURIService {

	private static final long serialVersionUID = 7790298542817115418L;

	private OpenSqlManage opensqlmanage;
	int pageSize = 10;

	private TMemberManager tmembermanager;
	private TGoodsCommentManager tgoodscommentmanager;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String statusCode = "1";
		try {
			opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
			tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
			tgoodscommentmanager = (TGoodsCommentManager) getSpringBean("tgoodscommentmanager");
			String content = req.getParameter("content");
			String sessionId = req.getParameter("sessionid");
			String productId = req.getParameter("productid");
			String orderid = req.getParameter("orderid");
			String score = req.getParameter("score"); // 商品评价分数
			String score1 = req.getParameter("score1"); // 客服分数(数据库卖家好评)
			String score2 = req.getParameter("score2"); // 邮寄分数
			String score3 = req.getParameter("score3"); // 配送人员态度
			long userId = tmembermanager.getMemberId(sessionId);
			int zong = Integer.parseInt(score) + Integer.parseInt(score1) + Integer.parseInt(score2)
					+ Integer.parseInt(score3);
			if (content == null || "".equals(content)) {
				content = "东西很好哦，极力向大家推荐！";
			}
			int grade = 1;
			if (zong > 0 && zong <= 4) {
				grade = 3;
			} else if (zong >= 5 && zong < 13) {
				grade = 2;
			} else {
				grade = 1;
			}

			TGoodsComment record = new TGoodsComment();
			record.setSumFraction(zong);
			record.setType(grade);
			record.setSeller(Integer.parseInt(score1));
			record.setFastMail(Integer.parseInt(score2));
			record.setGoods(Integer.parseInt(score));
			record.setFastMailPeople(Integer.parseInt(score3));
			record.setIsShow(0);
			record.setComment(content);
			record.setMemberId(userId);
			record.setGoodsId(Long.parseLong(productId));
			record.setOrderId(Long.parseLong(orderid));
			record.setCreateTime(new Date());
			long id = tgoodscommentmanager.insertSelective(record);

			if (id > 0) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderid", orderid);
				map.put("goodsid", productId);
				opensqlmanage.updateByMap_drug(map, "t_goods_comment.update-order-item");
			}
			if (id <= 0){
				statusCode = "0";
			}
		} catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		ConstantUtil.reJSON("{\"statusCode\":\"" + statusCode + "\"}", req, resp);

	}

	public String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
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

}
