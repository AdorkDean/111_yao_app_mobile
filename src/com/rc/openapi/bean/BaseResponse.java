package com.rc.openapi.bean;


/**
 * Created by ws on 2016/6/21.
 */
public class BaseResponse {

    private String resCode;
    private String resInfo;
    private Object result;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
