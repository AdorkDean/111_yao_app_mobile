package com.rc.openapi.serviceuri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import sun.misc.BASE64Decoder;

import com.rc.openapi.service.TReturnManager;
import com.rc.openapi.service.impl.TMemberManagerImpl;
import com.rc.openapi.util.ConstantUtil;
import com.rc.openapi.util.InfoUtil;
import com.rc.openapi.vo.TReturn;
/**
 * 提交申请售后
 *
 */
public class AddReturnService extends BaseURIService {

	private static final long serialVersionUID = 1L;
	public AddReturnService() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Map map = new HashMap();
		String statusCode = null;
		try {
			String sessionid = request.getParameter("sessionId");
			//服务类型
			String serviceType = request.getParameter("serviceType");
			//描述内容
			String content = request.getParameter("content");
			//product,quantity,name     商品id,名称,数量,编号:商品id,名称,数量,编号:     
			String products = request.getParameter("products");
			//订单id
			String orderId = request.getParameter("orderId");
			final String img1 = request.getParameter("img1");// 图片1
			final String img2 = request.getParameter("img2");// 图片2
			final String img3 = request.getParameter("img3");// 图片3
			final String img4 = request.getParameter("img4");// 图片4
			//图片地址集合
			if(StringUtils.hasText(sessionid)&&StringUtils.hasText(products)){
				TMemberManagerImpl tmembermanager = (TMemberManagerImpl) getSpringBean("tmembermanager");
				Long userId = tmembermanager.getMemberId(sessionid);
				if(0<userId){
					List<String> imgData = new ArrayList<String>();
					if(null!=img1&&!"".equals(img1.trim())){
						imgData.add(img1);
					}
					if(null!=img2&&!"".equals(img2.trim())){
						imgData.add(img2);
					}
					if(null!=img3&&!"".equals(img3.trim())){
						imgData.add(img3);
					}
					if(null!=img4&&!"".equals(img4.trim())){
						imgData.add(img4);
					}
					List<String> imgPathList = openThread(imgData);
					TReturn bean = new TReturn();
					bean.setImgPathList(imgPathList);
					bean.setProducts(products);
					bean.setOldOrderId(Long.parseLong(orderId));
					bean.setRefundDescribe(content);
					bean.setServiceType(Integer.parseInt(serviceType));
					bean.setMemberId(userId);
					TReturnManager dstreturnsmanager = (TReturnManager) getSpringBean("treturnmanager");
					dstreturnsmanager.insertSelective(bean);
					statusCode = "1";
				}else {
					statusCode = "2";
				}
			}else {
				statusCode = "0";
			}
		}catch (Exception e) {
			statusCode = "0";
			e.printStackTrace();
		}
		map.put("statusCode", statusCode);
		JSONObject json4 = JSONObject.fromObject(map);
		ConstantUtil.reJSON(json4.toString(), request, response);
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	public static String getDiskName(){
		String diskname="";
		DateFormat df = new SimpleDateFormat("yyyyMM");
		diskname =df.format(new Date());
		return diskname;
		
	}
	/**
	 * 开启多线程上传
	 */
	@SuppressWarnings("unchecked")
	private List<String> openThread(List<String> imgs) throws InterruptedException, ExecutionException{
		List<String> imgPathList = null;
		int taskSize = imgs.size();
		if(taskSize>0){
			imgPathList = new ArrayList<String>();
			ExecutorService pool = Executors.newFixedThreadPool(taskSize);
			List<Future> list = new ArrayList<Future>();
			for (int i = 0; i < taskSize; i++) {
				final String img = imgs.get(i);
				final int j = i;
				Callable c = new Callable<Object>() {
					public Object call() throws Exception {
						return uploadFile(img,j);
					}
				};
				Future f = pool.submit(c);
				list.add(f);
			}
			pool.shutdown();
			for (Future f : list) {
				imgPathList.add(f.get().toString());
			}
		}
		return imgPathList;
	}
	/**
	 * 文件上传
	 */
	public String uploadFile(String fileData,int j){
		String imgPath = null;
		if (fileData != null&&!"".equals(fileData)){
			String diskPath=InfoUtil.getInstance().getInfo("config", "images.public.head.path");
			String savePath = this.getServletContext().getRealPath(diskPath)+"/"+getDiskName()+"/";
			BASE64Decoder decoder = new BASE64Decoder();  
			OutputStream out = null;
			try{  
				byte[] b = decoder.decodeBuffer(fileData);  
				for(int i=0;i<b.length;++i){  
					if(b[i]<0){//调整异常数据  
						b[i]+=256;  
					}  
				}  
				File f = new File(savePath);
				if (!f.exists() && !f.isDirectory()) {
					f.mkdirs();
				}
				String str = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis()+j)) + ".jpg";
				imgPath = diskPath+"/"+getDiskName()+"/"+str;
				out = new FileOutputStream(savePath+str);      
				out.write(b);  
			}catch (Exception e){  
				e.printStackTrace();
			}finally{
				if(null!=out){
					try {
						out.flush();
						out.close();  
					} catch (IOException e) {
						e.printStackTrace();
					}  
				}
			}
		}
		return imgPath;
	}
	    
}
