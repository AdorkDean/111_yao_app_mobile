package com.rc.openapi.serviceuri;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rc.commons.util.InfoUtil;

public class CartUtil {

	private Logger log = Logger.getLogger(getClass());
	
	public static void main(String[] args) {
		List<Long> la = new ArrayList<Long>();
		List<Long> lb = new ArrayList<Long>();
		la.add(1L);
		la.add(2L);
		
		lb.add(2L);
		lb.add(3L);
		
	
		
	}
	
	
	public static boolean isContain(long id){
		String goods = InfoUtil.getInstance().getInfo("zero_buy", "goods");
		String[] arry = goods.split(",");
		
		for(String e : arry){
			if((id+"").equals(e)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isSync(String uuid){
		
		
		return true;
	}
	
	

}
