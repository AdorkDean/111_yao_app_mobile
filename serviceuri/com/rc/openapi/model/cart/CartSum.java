package com.rc.openapi.model.cart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class CartSum {

	private int cnt;
	private List<HashMap> list;
	private BigDecimal zongjia;
	private BigDecimal youhui;
	
	
	public BigDecimal getZongjia() {
		return zongjia;
	}
	public void setZongjia(BigDecimal zongjia) {
		this.zongjia = zongjia;
	}
	public BigDecimal getYouhui() {
		return youhui;
	}
	public void setYouhui(BigDecimal youhui) {
		this.youhui = youhui;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public List<HashMap> getList() {
		return list;
	}
	public void setList(List<HashMap> list) {
		this.list = list;
	}

}
