package com.rc.openapi.model;

import java.util.List;



/**
 * @author 作者 尹滨  E-mail:yinbinhome@163.com
 * @version 创建时间：2010-4-27 上午11:53:00
 * 类说明
 */
public class ReMessage {
	private String errorcode;
	private String chargno;
	private Account account;
	private Drawaccunt drawaccunt;
	private String chargevalue;
	private List list;
	
	//username,realname,mobile,regsource,unionlogin_id,userid
	private String username;
	private String realname;
	private String mobile;
	private String regsource;
	private String unionlogin_id;
	private String userid;
	private String nickname;
	private String personIcon;
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPersonIcon() {
		return personIcon;
	}
	public void setPersonIcon(String personIcon) {
		this.personIcon = personIcon;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRegsource() {
		return regsource;
	}
	public void setRegsource(String regsource) {
		this.regsource = regsource;
	}
	public String getUnionlogin_id() {
		return unionlogin_id;
	}
	public void setUnionlogin_id(String unionlogin_id) {
		this.unionlogin_id = unionlogin_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

	public String getChargevalue() {
		return chargevalue;
	}
	public void setChargevalue(String chargevalue) {
		this.chargevalue = chargevalue;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getChargno() {
		return chargno;
	}
	public void setChargno(String chargno) {
		this.chargno = chargno;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Drawaccunt getDrawaccunt() {
		return drawaccunt;
	}
	public void setDrawaccunt(Drawaccunt drawaccunt) {
		this.drawaccunt = drawaccunt;
	}
	
}
