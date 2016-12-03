package com.rc.openapi.util;

import java.util.ArrayList;
import java.util.List;

import com.rc.openapi.model.ReMessage;
import com.thoughtworks.xstream.XStream;

/**
 * @author 作者 尹滨  E-mail:yinbinhome@163.com
 * @version 创建时间：2010-4-27 下午07:41:56
 * 类说明
 */
public class XmlUtil {
	
	/**
	 * @param remsg
	 * @return 对象转XML
	 */
	public  static String getXML(ReMessage remsg){
		XStream xstream = new XStream(); 
        xstream.alias("body", ReMessage.class);

        String xml = xstream.toXML(remsg);
		return xml;
	}
	
	/**
	 * @param XML
	 * @return  XML转对象
	 */
	public static Object getObjectFromXML(String XML){
		XStream xstream = new XStream(); 
		xstream.alias("body", ReMessage.class);
		ReMessage t=(ReMessage)xstream.fromXML(XML);
		return t;
	}

	public static void main(String[] a){
		//String aa="<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg v=\"1.0\" id=\"1273482280750\"><body><response errorcode=\"0\"/></body></msg>";
		ReMessage r=new ReMessage();
		r.setErrorcode("200");
		Person p1=new Person("yinbin","20","zhongguo");
		Person p2=new Person("gaoshou","40","xianggang");
		List list=new ArrayList();
		list.add(p1);
		list.add(p2);
		//r.setList(list);
		//PageInfo pageInfo=new PageInfo();
//		pageInfo.setCount(100);
//		pageInfo.setPage(2);
//		pageInfo.setPages(20);
//		pageInfo.setPageSize(10);
		//r.setPageInfo(pageInfo);
		System.out.println(XmlUtil.getXML(r));
		ReMessage rr=(ReMessage)getObjectFromXML(XmlUtil.getXML(r));
		System.out.println(rr.getErrorcode());
	}
}
