package com.rc.openapi.filter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.rc.openapi.model.Parnter;
import com.rc.openapi.util.ConfigureUitl;
import com.rc.openapi.util.sign.SignKit;
import com.thoughtworks.xstream.XStream;

/**
 * 第二个过滤器,过滤统一入口，加密
 * @author yinbin
 * 
 */

public class RouterFilter implements Filter {
	protected final Logger log = Logger.getLogger(getClass());
	private FilterConfig filterConfig;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		log.info("【SERVICE接口过滤器】进入过滤器！");
		Map<String, String> map = SignKit.getRequestMap(request);
		String parnterid = map.get("parnterid");
		String secretKey = ConfigureUitl.getParnterKey(request.getSession().getServletContext(), parnterid);

		boolean flag = SignKit.verifySign(map, secretKey);
		if (flag) {
			chain.doFilter(req, res);
		}

	}

	/*
	 * private void doErrorCode(String error_code, ServletRequest req,
	 * ServletResponse res) throws ServletException, IOException {
	 * HttpServletResponse response = (HttpServletResponse) res;
	 * response.setContentType("text/html"); PrintWriter out = null; out =
	 * response.getWriter(); out.print("error_code = " + error_code);
	 * out.flush(); out.close(); }
	 */

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	/**
	 * 取商户的秘钥
	 * 
	 * @param merID
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public String getmerKey(String merID) throws IOException {

		String secretKey = getParnterId(merID);
		return secretKey;
	}

	public String getParnterId(String parnterid) throws FileNotFoundException {
		String key = "";
		XStream xstream = new XStream();
		xstream.alias("parnter", Parnter.class);
		xstream.alias("root", List.class);
		List<Parnter> list = (List<Parnter>) xstream.fromXML(this.getClass().getResourceAsStream("/parnter.xml"));
		for (Parnter p : list) {
			if (p.getId().equals(parnterid)) {
				key = p.getKey();
			}
		}
		return key;
	}

	public void destroy() {
	}

}