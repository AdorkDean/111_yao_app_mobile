package com.rc.openapi.util;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.rc.openapi.model.Method;
import com.rc.openapi.model.Parnter;
import com.rc.openapi.model.Root;
import com.thoughtworks.xstream.XStream;

public class ConfigureUitl {
	public static final String PARNTERROOT = "parnterRoot";

	/**
	 * 加载配置文件到全局
	 * 
	 * @param merID
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public void loadConfigureRoot(ServletContext servletContext) {
		Root root = getRootObject();
		servletContext.setAttribute(ConfigureUitl.PARNTERROOT, root);
	}

	/**
	 * 获取商户信息,访问uri信息类
	 * 
	 * @return
	 */
	public Root getRootObject() {
		XStream xstream = new XStream();
		xstream.alias("parnter", Parnter.class);
		xstream.alias("method", Method.class);
		xstream.alias("root", Root.class);

		String filepath =this.getClass().getResource("/").getPath();
		File file = new File(filepath);
		List<Parnter> list1 = new ArrayList<Parnter>();
		List<Method> list2 = new ArrayList<Method>();
		Root root = new Root();
        if (!file.isDirectory()) {
        	 root = (Root) xstream.fromXML(this.getClass().getResourceAsStream("/parnter.xml"));
    		 list1 = root.getParnters();
    		 list2 = root.getMethods();
        } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                        File readfile = new File(filepath + "/" + filelist[i]);
                        if (!readfile.isDirectory()) {
                                if(readfile.getName().startsWith("parnter")&&readfile.getName().endsWith(".xml")){
                                	 Root  root1 = (Root) xstream.fromXML(this.getClass().getResourceAsStream("/"+readfile.getName()));
                                	 if(!(list1!=null&&list1.size()>0)){
                                		 if(root1.getParnters()!=null&&root1.getParnters().size()>0){
                                			 list1.addAll(root1.getParnters());
                                		 }
                                	 }
                                	 list2.addAll(root1.getMethods());
                                }

                        } 
                }
        }
		System.out.println(list1.size());
		System.out.println(list2.size());
		root.setMethods(list2);
		root.setParnters(list1);
		return root;
	}

	/**
	 * 根据商户id,获取秘钥
	 * 
	 * @param servletContext
	 * @param id
	 * @return
	 */
	public static String getParnterKey(ServletContext servletContext, String id) {
		Root root = (Root) servletContext.getAttribute(PARNTERROOT);
		List<Parnter> list = root.getParnters();
		String key = "";
		for (Parnter p : list) {
			if (id != null && id.trim().length() > 0 && p.getId().equals(id)) {
				key = p.getKey();
			}
		}

		return key;
	}

	/**
	 * 根据method那么获取URI
	 * 
	 * @param servletContext
	 * @param name
	 * @return
	 */
	public static String getMethodURI(ServletContext servletContext, String name) {
		Root root = (Root) servletContext.getAttribute(PARNTERROOT);
		List<Method> list = root.getMethods();
		String uri = "";
		for (Method m : list) {
			if (name != null && name.trim().length() > 0 && m.getName().equals(name)) {
				uri = m.getUri();
			}
		}

		return uri;
	}

	public static void main(String[] args) {
		new ConfigureUitl().getRootObject();
	}
}
