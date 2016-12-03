package com.rc.openapi.serviceuri;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.service.ICartManager;
import com.rc.openapi.service.OpenSqlManage;
import com.rc.openapi.service.TMemberManager;
import com.rc.openapi.util.ConstantUtil;

public class CartAlterURIService extends BaseURIService {

	private static final long serialVersionUID = -6984473971049766938L;
	private Logger log = Logger.getLogger(getClass());
	private OpenSqlManage opensqlmanage;
	private ICartManager cartmanager;
	private TMemberManager tmembermanager;

	public static void main(String[] args) throws SQLException {

		// AlterCartURIService o = new AlterCartURIService();
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

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String statusCode = "1";
		Map<String, Object> rs = new HashMap<String, Object>();
		try {
			opensqlmanage = (OpenSqlManage) getSpringBean("opensqlmanage");
			cartmanager = (ICartManager) getSpringBean("cartmanager");
			tmembermanager = (TMemberManager) getSpringBean("tmembermanager");
			String flag = req.getParameter("flag");
			String sessionId = req.getParameter("sessionid");
			String uuid = req.getParameter("uuid");
			String productId = req.getParameter("productid");
			if ("".equals(sessionId)) {
				sessionId = null;
			}
			CartParam arg = new CartParam();
			arg.setCartkey(uuid);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", flag);
			map.put("uuid", uuid);
			map.put("gid", productId);
			long userId = -1;
			if (sessionId != null) {
				userId = tmembermanager.getMemberId(sessionId);
				arg.setUserId(userId);
				arg.setIslogin(true);
				map.put("userid", userId);
			}
			// 查询cartid
			Long itemid = (Long) opensqlmanage.selectForObjectByMap(map, "mycart.get-item-id");
			int n = cartmanager.updateGoodsCount(itemid + "", productId, 1, flag);
			if (n == -1) { // 购物车中只剩下一个商品了，不能做再减动作
				statusCode = "-2";
			} else if (n == -2) { // 库存不足
				statusCode = "-3";
			} else if (n == -3) { // 该商品正在做单品促销，超过了限制数量
				statusCode = "-1";
			}
			BigDecimal heji = new BigDecimal(0);
			if (n > 0) {
				heji = (BigDecimal) cartmanager.accounts(arg).get("money");
			}
			rs.put("statusCode", statusCode);
			rs.put("heji", CartGetListURIService.df.format(heji));
			JSONObject json = JSONObject.fromObject(rs);
			ConstantUtil.reJSON(json.toString(), req, resp);
		} catch (Exception e) {
			statusCode = "0";
			ConstantUtil.reJSON("{\"statusCode\":\"" + statusCode + "\"}", req, resp);
			e.printStackTrace();
		}

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
