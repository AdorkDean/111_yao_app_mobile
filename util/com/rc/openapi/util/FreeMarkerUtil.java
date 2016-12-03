package com.rc.openapi.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {

	
	
	
	/**
	 *
	 *	计算公式工具 
	 *
	 */
	public static String  process(String template,Map<String,Object> root) throws TemplateException, IOException {
		Configuration freemarkerCfg = new Configuration();
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), freemarkerCfg).process(root, out);
		return out.toString();
	}

	public static void main(String[] args) throws TemplateException, IOException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("quantity", 10);
		model.put("price", 100);
		BigDecimal result = new BigDecimal(FreeMarkerUtil.process("#{(price*0.3);M50}",model));
		System.out.println(result);
	}

}
