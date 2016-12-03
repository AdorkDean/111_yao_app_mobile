package com.rc.openapi.model;

import java.math.BigDecimal;

public class UserScoreDetail {
	
	private long id;
	private long userid;
	private int score_class;
	private BigDecimal score;
	private String memo;
	private int relative_id;
	private String operater;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	public int getRelative_id() {
		return relative_id;
	}
	public void setRelative_id(int relative_id) {
		this.relative_id = relative_id;
	}
	
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	public int getScore_class() {
		return score_class;
	}
	public void setScore_class(int score_class) {
		this.score_class = score_class;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}

	//--int sorceOper(long userid,int type ,long sorce,String memo) 

}
