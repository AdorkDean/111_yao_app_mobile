package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;

public class CartAddURIService extends BaseURIService {

	private static final long serialVersionUID = -4168850585857226210L;
	
	private ICartManager cartmanager;
	public static String cartKey = "111yao";
	private TMemberManager tmembermanager;

	

	@Override
	public synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {

		resp.setContentType("text/html;charset=utf-8");
		tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
		cartmanager = (ICartManager) getSpringBean("cartmanager");
		String msg = "";
		String statusCode = "0";
		String sessionId = req.getParameter("sessionid");
		String uuid = req.getParameter("uuid");
		try {
			String productId = req.getParameter("productid");
			int count = Integer.parseInt(req.getParameter("count") == null ? "1" : req.getParameter("count"));
			CartParam arg = new CartParam();
			if(productId==null||"".equals(productId.trim())){
				msg = "商品不能为空";
				ConstantUtil.reJSON("{\"statusCode\":\"" + statusCode + "\",\"msg\":\"" + msg + "\"}", req, resp);
				return;
			}
			if((sessionId==null||"".equals(sessionId.trim()))&&(uuid==null||"".equals(uuid.trim()))){
				msg = "用户或游客信息有误";
				ConstantUtil.reJSON("{\"statusCode\":\"" + statusCode + "\",\"msg\":\"" + msg + "\"}", req, resp);
				return;
			}
			
			
			if (sessionId != null && !"".equals(sessionId.trim())) {
				long userId = tmembermanager.getMemberId(sessionId);
				arg.setUserId(userId);
				arg.setIslogin(true);
			} else {
				arg.setIslogin(false);
				arg.setCartkey(uuid);
			}
			arg.setGoodsId(Long.parseLong(productId));
			long rs = cartmanager.addCart(arg, count);
			//rs 为返回状态码:-1:处方药;-2:库存不足;-3:已下架;-4:您已购买该促销商品的数量超过限购数量;-5:促销,超过购买数量
			if(rs==-1){
				statusCode = "0";
				msg = "该商品为处方药，请咨询购买,111医药馆提示您";
			}else if(rs==-2){
				statusCode = "0";
				msg = "库存不足";
			}else if(rs==-3){
				statusCode = "0";
				msg = "该商品为下架商品";
			}else if(rs==-4){
				statusCode = "0";
				msg = "该商品正促销中，购物车里该商品超出了购买数量";
			}else if(rs==-5){
				statusCode = "0";
				msg = "您已经购买该促销商品的限购数量";
			}else if(rs==-7){
				statusCode = "0";
				msg = "购物车单品不能超过50000个";
			}else if(rs>0){
				statusCode = "1";
				msg = "加入成功";
			}else{
				statusCode = "0";
				msg = "操作失败";
			}
//			if (rs > 0) {
//				statusCode = "1";
//				msg = "加入成功";
//			} else if (rs == -2 || rs == -1) {
//				statusCode = "-1";
//				msg = "库存不足";
//			} else if (rs == -3) {
//				statusCode = "-1";
//				msg = "该商品正促销中，超出了购买数量";
//			} else if (rs == -100) {
//				statusCode = "-1";
//				msg = "该商品为处方药，请咨询购买,111医药馆提示您";
//			} else if (rs == -200) {
//				statusCode = "-1";
//				msg = "该商品为下架商品";
//			} else if (rs == -300) {
//				statusCode = "-1";
//				msg = "您已经购买过该促销商品了";
//			} else if (rs == 0) {
//				msg = "操作失败";
//			}

		} catch (Exception e) {
			msg = "网络异常";
			e.printStackTrace();
			statusCode = "0";

		}
		ConstantUtil.reJSON("{\"statusCode\":\"" + statusCode + "\",\"msg\":\"" + msg + "\"}", req, resp);

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public static void main(String[] args) throws SQLException {

		// AddCartURIService o = new AddCartURIService();
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext*.xml");
		// o.opensqlmanage = (OpenSqlManage) context.getBean("opensqlmanage");
		// o.dstproductmanager = (DstProductManager)
		// context.getBean("dstproductmanager");
		// o.dstcartitemmanager = (DstCartItemManager)
		// context.getBean("dstcartitemmanager");
		// o.dstmembermanager = (DstMemberManager)
		// context.getBean("dstmembermanager");
		// o.dstcartmanager = (DstCartManager)
		// context.getBean("dstcartmanager");
		// o.dstproductmanager = (DstProductManager)
		// context.getBean("dstproductmanager");
		// o.dstcartmanager = (DstCartManager)
		// context.getBean("dstcartmanager");

		BigDecimal b = new BigDecimal(1.000000);
		BigDecimal a = new BigDecimal(1);
		System.out.println(b.compareTo(a));

	}
	
}
