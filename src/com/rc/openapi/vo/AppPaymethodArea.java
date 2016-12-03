package com.rc.openapi.vo;

public class AppPaymethodArea {
    private Long id;

    private String imgurl;

    private Long isonline;

    private String name;

    private Long paymethodId;

    private String paypluginId;

    private Long area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public Long getIsonline() {
        return isonline;
    }

    public void setIsonline(Long isonline) {
        this.isonline = isonline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPaymethodId() {
        return paymethodId;
    }

    public void setPaymethodId(Long paymethodId) {
        this.paymethodId = paymethodId;
    }

    public String getPaypluginId() {
        return paypluginId;
    }

    public void setPaypluginId(String paypluginId) {
        this.paypluginId = paypluginId;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }
}