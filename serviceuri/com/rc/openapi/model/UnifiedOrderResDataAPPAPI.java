package com.rc.openapi.model;


/**
 * Created by ws on 2015/4/29.
 */
public class UnifiedOrderResDataAPPAPI {

    //返回结果
    private String appid;  //公众账号ID.  微信分配的公众账号ID.  (必填)
    private String partnerid;  //商户号.  微信支付分配的商户号.  (必填)
    private String prepayid;  //预支付交易会话ID.  微信返回的支付交易会话ID.  (必填)
    private String package_str = "Sign=WXPay";  //扩展字段.  暂填写固定值Sign=WXPay.  (必填)
    private String noncestr;  //随机字符串.  随机字符串，不长于32位。推荐随机数生成算法.  (必填)
    private String timestamp;  //时间戳.  时间戳，请见接口规则-参数规定.  (必填)
    private String sign;  //签名.  签名，详见签名生成算法.  (必填)
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getPackage_str() {
		return package_str;
	}
	public void setPackage_str(String package_str) {
		this.package_str = package_str;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}


}
