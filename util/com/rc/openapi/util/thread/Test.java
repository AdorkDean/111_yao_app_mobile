package com.rc.openapi.util.thread;

import java.util.concurrent.ThreadPoolExecutor;



/**
 * @author 作者 尹滨  E-mail:yinbinhome@163.com
 * @version 创建时间：2010-9-13 下午03:25:06
 * 类说明
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadPoolExecutor threadpool = ThreadPoolFactory.getInstance();
		String url="http://localhost/MyHtml.html";
		String reStr="返回传";
		String[] ordernos={"ZFB2010090300142868","ZFB2010090200142866","ZFB2010083100142855","ZFB2010083100142843","ZFB2010083100142842","ZFB2010083100142836",
				"ZFB2010083100142832","ZFB2010083100142830","ZFB2010082300142822","ZFB2010082300142802","ZFB2010082300142689","ZFB2010071900142678","ZFB2010062900142655",
				"ZFB2010062900142653","ZFB2010062900142652","ZFB2010062400142624","ZFB2010062400142623","ZFB2010062400142622","ZFB2010062400142621","ZFB2010062400142615"};
		String thirdNo="第三方号";
		int i=0;
		for(String orderno:ordernos){
			//url+=i;
			reStr+=i;
			thirdNo+=i;
			//CallTask callTask=new CallTask(url,reStr,orderno,thirdNo);
			//threadpool.execute(callTask);
		}
		
	}

}
