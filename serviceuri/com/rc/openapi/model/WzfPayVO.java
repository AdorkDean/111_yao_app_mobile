package com.rc.openapi.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/**
 * 微信去支付 VO
 * @author user3
 *
 */
public class WzfPayVO implements Serializable{
	private static final long serialVersionUID = 3434501951113942688L;
    /**
     * 公众账号ID
     */
	private String appid;
	/**
	 * 商户号
	 */
	private String partnerid;
	/**
	 * 预支付交易会话ID
	 */
	private String prepayid;
	/**
	 * 扩展字段  暂填写固定值Sign=WXPay
	 */
	private String packages;
	/**
	 * 随机字符串
	 */
	private String noncestr;
	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 订单id
	 */
	private String orderId;
	
	
	public WzfPayVO() {
		super();
	}
	public WzfPayVO(UnifiedOrderResDataAPPAPI vo) {
		super();
		this.appid = vo.getAppid();
		this.partnerid = vo.getPartnerid();
		this.prepayid = vo.getPrepayid();
		this.packages = vo.getPackage_str();
		this.noncestr = vo.getNoncestr();
		this.timestamp = vo.getTimestamp();
		this.sign = vo.getSign();
	}
	
	
	public WzfPayVO(String appid, String partnerid, String prepayid,
			String packages, String noncestr, String timestamp, String sign,String orderId) {
		super();
		this.appid = appid;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this.packages = packages;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.sign = sign;
		this.orderId = orderId;
	}
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
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
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
	
	
	
	 public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Map<String,String> toMap(){
	        Map<String,String> map = new HashMap<String, String>();
	        Field[] fields = this.getClass().getDeclaredFields();
	        for (Field field : fields) {
	            Object obj;
	            try {
	                obj = field.get(this);
	                if(obj!=null){
	                    map.put(field.getName(), (String)obj);
	                }
	            } catch (IllegalArgumentException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	        }
	        return map;
	    }
	
}
