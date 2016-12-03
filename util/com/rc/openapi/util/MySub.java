package com.rc.openapi.util;

public class MySub {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String s = new MySub().mySubString("lijainlong");
		System.out.println(s);

	}
	
	
	public static String mySubString(String email){
		

		int at = email.length();
		StringBuilder sb = new StringBuilder(email);
		String mail = "";
		int n = 2; 
		if(at>=1 && at<=3) n=1;
		if(at>3 && at<=5) n=2;
		if(at>5 && at<=10) n=3;
		if(at>10 && at<=20) n = 5;
		if(at>20) n=10;
		for(int i=(at-n);i<=at-1;i++){
		StringBuilder sb2 = sb.replace(i,i+1,"*");
		mail=sb2.toString();
		}

		
		return mail;
	}

}
