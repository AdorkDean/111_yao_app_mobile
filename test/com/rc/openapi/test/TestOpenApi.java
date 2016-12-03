package com.rc.openapi.test;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;


public class TestOpenApi {

	

	/**  
	 *    
	 * 方法描述：  
	 * 创建人：hexiaoliang  
	 * 创建时间：2010-3-22 上午10:43:19    
	 * @param args
	 * @version   1.0
	 * @throws IOException 
	 * @throws HttpException 
	 *   
	 */
	public static void main(String[] args) throws HttpException, IOException {
		// TODO Auto-generated method stub
		
		
		for(int i=101 ;i<121;i++){
			Thread t = new MultiThread();
			t.start();
		}
		
	}
	
	static class MultiThread extends Thread{
		
		public void run(){
			HttpClient client = new HttpClient();
			GetMethod get = null;
			try{	
				String v = "1.0";
				String appKey="a89b491d62090a65319864aa68ae2151";
				String method="wzl.account.orderBuyPayTwo";
				String account="1";
				String format="xml";
			
				for(int i=101 ;i<121;i++){
					long timestamp = System.currentTimeMillis();
					String userId = "100";
					String orderNo = String.valueOf(i)+"abc";
					String sig=md5("account=1appKey=a89b491d62090a65319864aa68ae2151format=xmlmethod=wzl.account.orderBuyPayTwo"
							+"orderNo="+orderNo+"timestamp="+timestamp+"userId="+userId+"v="+v+"1F3H0261");					
					String url="http://localhost:8080/cbservice/sltRouter?v="+v+"&appKey="+appKey+"&method="+method+"&account="
							+account+"&format="+format+"&userId="+userId+"&orderNo="+orderNo+"&sig="+sig+"&timestamp="+timestamp;					
				
					System.out.println("url="+url);
					get = new GetMethod(url);
					int return_Code = client.executeMethod(get);
					System.out.println("thread-id="+this.getId()+"i="+i+"  recode="+return_Code);
					System.out.println(" content:"+get.getResponseBodyAsString());
					get.releaseConnection();
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
				get.releaseConnection();
			}
			
			
		}
	}
	
	private static String md5(String str) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			try {
				for (byte b : md.digest(str.getBytes("UTF-8"))) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			} catch (UnsupportedEncodingException e) {
				for (byte b : md.digest(str.getBytes())) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			}
			return result.toString();
		} catch (java.security.NoSuchAlgorithmException ex) {
			System.err.println("MD5 does not appear to be supported" + ex);
			return "";
		}
	}

}
