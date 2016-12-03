package com.rc.openapi.util;

public class Distance{
	private static final double EARTH_RADIUS = 6378137;
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2){
		 double radLat1 = rad(lat1);
		 double radLat2 = rad(lat2);
		 double a = radLat1 - radLat2;
		 double b = rad(lng1) - rad(lng2);
		 double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		 s = s * EARTH_RADIUS;
		 s = Math.round(s * 10000) / 10000;
		 return s;
	}
	public static void main(String[] args) {
		double distance = GetDistance(116.4755160000,40.0195770000,116.4605110000,40.0020520000);
		System.out.println("Distance is:"+distance);
	}
}