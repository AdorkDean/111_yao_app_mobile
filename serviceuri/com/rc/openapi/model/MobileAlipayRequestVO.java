package com.rc.openapi.model;

import java.io.Serializable;

/**
 * 移动支付宝 返回参数
 * @author user3
 *
 */
public class MobileAlipayRequestVO implements Serializable{

	private static final long serialVersionUID = 3316867456478054154L;
	
	//支付宝
	private String out_trade_no;//商户网站 唯一订单 号 
	private String subject;// 商品的标题/交易标题/订单标 题/订单关键字等。 该参数最长为 128 个汉字。 
	private String payment_type = "1" ;// 支付类型。默认值为：1（商 品购买）。 
	private String seller_id;//卖家支付 宝账号 
	private String total_fee; //该笔订单的资金总额，单位为 RMB-Yuan。取值范围为 [0.01，100000000.00]，精确 到小数点后两位。 
	private String  body;// 商品详情 对一笔交易的具体描述信息。 如果是多种商品，请将商品描 述字符串累加传给 body。
	private String it_b_pay="30m";  //未付款交 易的超时 时间 设置未付款交易的超时时间， 一旦超时，该笔交易就会自动 被关闭。 当用户输入支付密码、点击确 认付款后（即创建支付宝交易 后）开始计时。 取值范围：1m～15d，或者使 用绝对时间（示例格式： 2014-06-13 16:00:00）。 m-分钟，h-小时，d-天，1c- 当天（无论交易何时创建，都 在 0 点关闭）。 该参数数值不接受小数点，如 1.5h，可转换为 90m。 可空 30m 
	private String extern_token;// 授权令牌 开放平台返回的包含账户信 息的 token（授权令牌，商户 在一定时间内对支付宝某些 服务的访问权限）。通过授权 登录后获取的 alipay_open_id，作为该参数 的 value，登录授权账户即会 为支付账户。
	private String notify_url;  // 服务器异 步通知页 面路径  支付宝服务器主动通知商户 网站里指定的页面 http 路径。
	//订单id
	private String orderId;
	
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIt_b_pay() {
		return it_b_pay;
	}
	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}
	public String getExtern_token() {
		return extern_token;
	}
	public void setExtern_token(String extern_token) {
		this.extern_token = extern_token;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
}
