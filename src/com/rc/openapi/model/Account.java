package com.rc.openapi.model;
/**
 * @author 作者 尹滨  E-mail:yinbinhome@163.com
 * @version 创建时间：2010-4-27 上午11:54:01
 * 类说明
 */
public class Account {
	private String cash;
	private String card;
	private String ticket;
	private String score;
	private String drawpwd;
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getDrawpwd() {
		return drawpwd;
	}
	public void setDrawpwd(String drawpwd) {
		this.drawpwd = drawpwd;
	}
	
}
