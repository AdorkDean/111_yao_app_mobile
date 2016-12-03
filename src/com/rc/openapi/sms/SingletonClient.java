package com.rc.openapi.sms;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import cn.ld.sdk.client.api.Client;


public class SingletonClient {
	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		ResourceBundle bundle=PropertyResourceBundle.getBundle("sms");
		if(client==null){
			try {
				System.out.print("key:"+bundle.getString("key"));
				System.out.println("序列号："+bundle.getString("softwareSerialNo"));
				
				client=new Client(bundle.getString("softwareSerialNo"),bundle.getString("key"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public static void main(String str[]){
		SingletonClient.getClient();
	}
}
