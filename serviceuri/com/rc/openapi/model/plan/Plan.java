package com.rc.openapi.model.plan;

public class Plan {

 
	private long id;	//  方案ID
	private String name;	// 方案名称
	private String img;	  // 方案列表图片
	private String url;  	// app方案的详情页面
	private int weight;	
	private String wap_url;	// wap方案详情页面
	private String share_url;  // 分享详情页面（WAP详情的页面）
	private short sort;  // android打前3标识使用
	private String share_img; // 分享到微信的图片
	private String share_discribe;	// 分享到微信和描述
	
	
	public String getShare_discribe() {
		return share_discribe;
	}
	public void setShare_discribe(String share_discribe) {
		this.share_discribe = share_discribe;
	}
	public String getShare_img() {
		return share_img;
	}
	public void setShare_img(String share_img) {
		this.share_img = share_img;
	}
	public short getSort() {
		return sort;
	}
	public void setSort(short sort) {
		this.sort = sort;
	}
	public String getShare_url() {
		return share_url;
	}
	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public Plan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Plan(long id, String name, String img,String url) {
		this.id = id;
		this.name = name;
		this.img = img;
		this.url = url;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWap_url() {
		return wap_url;
	}
	public void setWap_url(String wap_url) {
		this.wap_url = wap_url;
	}
	
	
	
	

}
