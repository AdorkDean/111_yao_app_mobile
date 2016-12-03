package com.rc.openapi.model;

import java.util.List;

/**
 * 接口返回数据实体
 * 
 * @author user00
 * @createTime 2015-7-27 上午11:43:47
 */
public class Prescription {
	/**
	 * 主键ID
	 */
	private String id;

	private String diseaseDescrip;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 图片地址
	 */
	private String imageUrl;

	private List<String> imgUrlList;

	private String createDate;

	private String status;// 状态: 1:已回访 0:已上传

	private String ifhelp;// 是否需要医师帮助(0:不需要,1:需要)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取手机号
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置手机号
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取图片地址
	 * 
	 * @return
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置图片地址
	 * 
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 获取病情描述
	 * 
	 * @return
	 */
	public String getDiseaseDescrip() {
		return diseaseDescrip;
	}

	/**
	 * 设置病情描述
	 * 
	 * @param diseaseDescrip
	 */
	public void setDiseaseDescrip(String diseaseDescrip) {
		this.diseaseDescrip = diseaseDescrip;
	}

	public List<String> getImgUrlList() {
		return imgUrlList;
	}

	public void setImgUrlList(List<String> imgUrlList) {
		this.imgUrlList = imgUrlList;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIfhelp() {
		return ifhelp;
	}

	public void setIfhelp(String ifhelp) {
		this.ifhelp = ifhelp;
	}

}
