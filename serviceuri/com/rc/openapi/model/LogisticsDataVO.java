package com.rc.openapi.model;

import java.util.List;
/**
 * 运单详情
 *
 */
public class LogisticsDataVO {
	/**
	 * 发货详情
	 */
	private List<LogiData> llist;
	/**
	 * 发货订单商品
	 */
	private List<ProductData> plist;
	/**
	 * 运单编号
	 */
	private String expid;
	/**
	 * 快递公司
	 */
	private String name;



	/**
	 * 发货状态
	 */
	private String status;
	
	
	public List<LogiData> getLlist() {
		return llist;
	}
	public String getStatus() {
		return status;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public void setLlist(List<LogiData> llist) {
		this.llist = llist;
	}


	public List<ProductData> getPlist() {
		return plist;
	}


	public void setPlist(List<ProductData> plist) {
		this.plist = plist;
	}


	public String getExpid() {
		return expid;
	}


	public void setExpid(String expid) {
		this.expid = expid;
	}




	public class ProductData{
		/**
		 * 金额
		 */
		private String price;
		/**
		 * 图片
		 */
		private String thumbnail;
		/**
		 * 数量
		 */
		private int quantity;
		/**
		 * 名称
		 */
		private String name;
		/**
		 * id
		 */
		private long id;
		
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		public String getThumbnail() {
			return thumbnail;
		}
		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
