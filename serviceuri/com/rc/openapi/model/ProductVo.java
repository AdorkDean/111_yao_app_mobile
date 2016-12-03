package com.rc.openapi.model;


public class ProductVo {
    private String pid;

    private String name;

    private String image;
    
    private String price;
    
    private String marketPrice;
    
    private String discount;
    
    private String type;
    
    

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getDiscount() {
		if(null != discount){
			String s[] = discount.split("\\.");
			if(s.length==2){
				String d = s[1].substring(0, 1);
				discount=d;
			}
		}
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
    
	public static void main(String[] args) {
		String s  = "0.";
		String s1 [] = s.split("\\.");
		System.out.println(s1.length);
	}
     
}