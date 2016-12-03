package com.rc.openapi.model;

import java.util.List;

public class BigTableVO {
    String cId ;
    String name;
    String type;
    List bigList;
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List getBigList() {
		return bigList;
	}
	public void setBigList(List bigList) {
		this.bigList = bigList;
	}
    
}
