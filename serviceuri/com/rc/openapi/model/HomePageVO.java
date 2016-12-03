package com.rc.openapi.model;

import java.util.List;

/**
 * 首页信息
 *
 */
public class HomePageVO {
	/**
	 * 头部导航集合
	 *
	 */
//	private List<TopNav> topNavList;
	/**
	 * 左侧导航集合
	 *
	 */
//	private List<LeftNav> leftNavList;
	/**
	 * 轮播图集合
	 */
	private List<CarouselFigure> cfList;
	/**
	 * 专区集合
	 */
	private List<ZhuanQu> zqList;
	
//	public List<LeftNav> getLeftNavList() {
//		return leftNavList;
//	}
//	public void setLeftNavList(List<LeftNav> leftNavList) {
//		this.leftNavList = leftNavList;
//	}
//	public List<TopNav> getTopNavList() {
//		return topNavList;
//	}
//	public void setTopNavList(List<TopNav> topNavList) {
//		this.topNavList = topNavList;
//	}
	public List<CarouselFigure> getCfList() {
		return cfList;
	}
	public void setCfList(List<CarouselFigure> cfList) {
		this.cfList = cfList;
	}
	public List<ZhuanQu> getZqList() {
		return zqList;
	}
	public void setZqList(List<ZhuanQu> zqList) {
		this.zqList = zqList;
	}
	/**
	 * 专区
	 */
	public class ZhuanQu{
		private String name;
		private String imgUrl;
		private String type;
		private String aid;
		private String weight;
		
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getAid() {
			return aid;
		}
		public void setAid(String aid) {
			this.aid = aid;
		}
	}
	/**
	 * 图片对象
	 *
	 */
	public class CarouselFigure{
		private String name;
		private String imgUrl;
		private String webUrl;
		private String weight;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public String getWeight() {
			return weight;
		}
		public void setWeight(String weight) {
			this.weight = weight;
		}
		public String getWebUrl() {
			return webUrl;
		}
		public void setWebUrl(String webUrl) {
			this.webUrl = webUrl;
		}
		
	}
	/**
	 * 左侧导航对象
	 *
	 */
	public class LeftNav{
		private String name;
		private String pid;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		
	}
	/**
	 * 头部导航对象
	 *
	 */
	public class TopNav{
		private String name;
		private String aurl;
		private String iurl;
		private String order;
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAurl() {
			return aurl;
		}
		public void setAurl(String aurl) {
			this.aurl = aurl;
		}
		public String getIurl() {
			return iurl;
		}
		public void setIurl(String iurl) {
			this.iurl = iurl;
		}
	}
}
