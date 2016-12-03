package com.rc.openapi.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.rc.openapi.util.InfoUtil;

/**
 * @author 作者 尹滨  E-mail:yinbinhome@163.com
 * @version 创建时间：2010-9-13 下午02:36:12
 * 类说明
 */
public class ThreadPoolFactory {
	//private ThreadPoolExecutor threadpool=
	private  static int corePoolSize=Integer.parseInt(InfoUtil.getInstance().getInfo("threadpool", "corePoolSize"));
	private static  int maximumPoolSize=Integer.parseInt(InfoUtil.getInstance().getInfo("threadpool", "maximumPoolSize"));
	private static  int  keepAliveTime=Integer.parseInt(InfoUtil.getInstance().getInfo("threadpool", "keepAliveTime"));
	private static  int  waiQueue=Integer.parseInt(InfoUtil.getInstance().getInfo("threadpool", "Queue"));

	private  static ThreadPoolExecutor threadpool=null;
	
	
	public static synchronized ThreadPoolExecutor getInstance(){
		if(threadpool==null){
			 threadpool=new ThreadPoolExecutor((corePoolSize),(maximumPoolSize),(keepAliveTime),
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>((waiQueue)),new ThreadPoolExecutor.CallerRunsPolicy());
			 return threadpool;
		}else{
			return threadpool;
		}
	}
	
	public void addTask(Runnable task){
		try {
			threadpool.execute(task);
		} catch (Exception e) {
			System.out.println("添加任务异常！");
			e.printStackTrace();
		}
	}	
}
