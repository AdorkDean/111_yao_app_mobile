package com.rc.openapi.serviceuriV5.order;

/**
 * 返回信息
 * 
 * @author WWF
 * @createTime 2016-8-25 上午10:08:19
 */
public class ResultData {

	private String statusCode;
	private String message;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
