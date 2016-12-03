package com.rc.openapi.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.rc.openapi.dao.OpenSqlDAO;
import com.rc.openapi.dao.TCouponCardDAO;
import com.rc.openapi.service.TCouponCardManager;
import com.rc.openapi.util.DateUtils;
import com.rc.openapi.vo.TCoupon;
import com.rc.openapi.vo.TCouponCard;
import com.rc.openapi.vo.TCouponCardExample;

public class TCouponCardManagerImpl  implements TCouponCardManager {

    private TCouponCardDAO tcouponcarddao;
    private OpenSqlDAO opensqldao;
    public OpenSqlDAO getOpensqldao() {
		return opensqldao;
	}
	public void setOpensqldao(OpenSqlDAO opensqldao) {
		this.opensqldao = opensqldao;
	}
    public TCouponCardManagerImpl() {
        super();
    }
    public void  setTcouponcarddao(TCouponCardDAO tcouponcarddao){
        this.tcouponcarddao=tcouponcarddao;
    }
    public TCouponCardDAO getTcouponcarddao(){
        return this.tcouponcarddao;
    }
    public     int countByExample(TCouponCardExample example) throws SQLException{
        return tcouponcarddao. countByExample( example);
    }

    public     int deleteByExample(TCouponCardExample example) throws SQLException{
        return tcouponcarddao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tcouponcarddao. deleteByPrimaryKey( id);
    }

    public     Long insert(TCouponCard record) throws SQLException{
        return tcouponcarddao. insert( record);
    }

    public     Long insertSelective(TCouponCard record) throws SQLException{
        return tcouponcarddao. insertSelective( record);
    }

    public     List selectByExample(TCouponCardExample example) throws SQLException{
        return tcouponcarddao. selectByExample( example);
    }

    public     TCouponCard selectByPrimaryKey(Long id) throws SQLException{
        return tcouponcarddao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TCouponCard record, TCouponCardExample example) throws SQLException{
        return tcouponcarddao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TCouponCard record, TCouponCardExample example) throws SQLException{
        return tcouponcarddao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TCouponCard record) throws SQLException{
    	int statusCode = 0;
    	Map<String, String> map = new HashMap<String, String>();
    	DateUtils du = new DateUtils();
    	String dq = du.getCurrentTime();
    	map.put("code", record.getCardNo());
    	Map<String,Object> objmap = (Map<String,Object>) opensqldao.selectForObjectByMap(map, "t_coupon_card.ibatorgenerated_selectByCode");
    	System.out.println(objmap);
//    	{id=1741577, ticket_id=23, end_time=2015-09-24 00:00:00.0, status=1, is_repeat=0, start_time=2015-09-10 00:00:00.0, card_no=1115376754472, is_use=0, member_id=null}
    	if(objmap!=null){
    		Long id = (Long) objmap.get("id");
    		record.setId(id);
    		Integer isenabled = (Integer) objmap.get("status");
    		//是否启用
    		if(0==isenabled){//优惠劵未启用
    			return -3;
    		}
    		Long member = (Long) objmap.get("member_id");
    		if(null==member){
    			Object obdate = objmap.get("start_time");
    			Object oedate =  objmap.get("end_time");
    			if(null!=obdate){
    				String bdate = obdate.toString();
    				//当前日期大于等于开始日期
    				if(1==du.timeCompare(bdate.split("\\.")[0], dq)){
    					return -4;
    				}
    			}
    			if(null!=oedate){
    				String edate = oedate.toString();
    				//当前日期小于等于结束日期
    				edate = edate.split("\\.")[0];
    				if(-1==du.timeCompare(edate, dq)){
    					return -5;
    				}
    			}
    			//是否可以重复使用
    			Integer isenab = (Integer) objmap.get("is_repeat");
    			if(1==isenab){
    				tcouponcarddao. updateByPrimaryKeySelective( record);
    				statusCode = 1;
    			}else {
    				Integer isused = (Integer) objmap.get("is_use");
    				if(0==isused){
    					tcouponcarddao. updateByPrimaryKeySelective( record);
    					statusCode = 1;
    				}else {
    					//优惠劵已使用
    					return -2;
    				}
    			}
    		}else {
    			//优惠劵已使用
				return -2;
			}
    	}else {
    		//优惠劵不存在
    		return -1;
		}
        return statusCode;
    }

    public     int updateByPrimaryKey(TCouponCard record) throws SQLException{
        return tcouponcarddao. updateByPrimaryKey( record);
    }
	@Override
	public Long bindingCoupon(List couponList, Long memberId, int quantity)
			throws SQLException {
		for(int j=0;j<couponList.size();j++){
			TCoupon coupon = (TCoupon) couponList.get(j);
			for(int i=0;i<quantity;i++){
		      	TCouponCard card = new TCouponCard();
		      	String uuid = UUID.randomUUID().toString().toUpperCase();
		      	String code = null;
		      	Integer number = coupon.getCodeLength() != null ? coupon
							.getCodeLength() : 10;    //获取位数
					if (coupon.getRule() != null && coupon.getRule() == 0) { //生成优惠券码
						code = coupon.getPrefix()
								+ BigDecimal
										.valueOf(
												Math.round((Math.random() + new Random(
														10).nextDouble())
														* Math.pow(10.0D,
																number)))
										.toString().substring(0, number);
					} else {
						code = coupon.getPrefix()
								+ (uuid + uuid).replaceAll("-", "")
										.replaceAll("0|O", "K")
										.substring(0, number);
					}
					card.setCreateTime(new Date());
					card.setCardNo(code);
					card.setIsUse(0);
					card.setTicketId(coupon.getId());
					card.setMemberId(memberId);
					tcouponcarddao.insertSelective(card);
		      }
			
		}
		return null;
	}

	public Long bindingCouponCard(TCoupon coupon, Long memberId,int quantity) throws SQLException {
        Long id = 0L;
		for(int i=0;i<quantity;i++){
      	TCouponCard card = new TCouponCard();
      	String uuid = UUID.randomUUID().toString().toUpperCase();
      	String code = null;
      	Integer number = coupon.getCodeLength() != null ? coupon
					.getCodeLength() : 10;    //获取位数
			if (coupon.getRule() != null && coupon.getRule() == 0) { //生成优惠券码
				code = coupon.getPrefix()
						+ BigDecimal
								.valueOf(
										Math.round((Math.random() + new Random(
												10).nextDouble())
												* Math.pow(10.0D,
														number)))
								.toString().substring(0, number);
			} else {
				code = coupon.getPrefix()
						+ (uuid + uuid).replaceAll("-", "")
								.replaceAll("0|O", "K")
								.substring(0, number);
			}
			card.setCreateTime(new Date());
			card.setCardNo(code);
			card.setIsUse(0);
			card.setTicketId(coupon.getId());
			card.setMemberId(memberId);
			id = tcouponcarddao.insertSelective(card);
      }
		return id;
	}
	@Override
	public Long bindingCouponLimit(TCoupon coupon, Long memberId, int quantity)
			throws SQLException {
		TCouponCardExample tcce = new TCouponCardExample();
		tcce.createCriteria().andMemberIdIsNull().andTicketIdEqualTo(coupon.getId()).andIsUseEqualTo(0);
		List list = tcouponcarddao.selectByExample(tcce);
		if(null != list && list.size()>0){
			TCouponCard card = (TCouponCard) list.get(0);
			card.setMemberId(memberId);
			tcouponcarddao.updateByPrimaryKeySelective(card);
			return 1L;
		}else{
			return -1L;
		}
	}
	/**
	 * 用户注册发放优惠劵
	 */
	public Long bindingCouponCard(List couponList, Long memberId, int quantity)
			throws SQLException {
		for(int j=0;j<couponList.size();j++){
			TCoupon coupon = (TCoupon) couponList.get(j);
			for(int i=0;i<quantity;i++){
		      	TCouponCard card = new TCouponCard();
		      	String uuid = UUID.randomUUID().toString().toUpperCase();
		      	String code = null;
		      	Integer number = coupon.getCodeLength() != null ? coupon
							.getCodeLength() : 10;    //获取位数
					if (coupon.getRule() != null && coupon.getRule() == 0) { //生成优惠券码
						code = coupon.getPrefix()
								+ BigDecimal
										.valueOf(
												Math.round((Math.random() + new Random(
														10).nextDouble())
														* Math.pow(10.0D,
																number)))
										.toString().substring(0, number);
					} else {
						code = coupon.getPrefix()
								+ (uuid + uuid).replaceAll("-", "")
										.replaceAll("0|O", "K")
										.substring(0, number);
					}
					card.setCreateTime(new Date());
					card.setCardNo(code);
					card.setIsUse(0);
					card.setTicketId(coupon.getId());
					card.setMemberId(memberId);
					tcouponcarddao.insertSelective(card);
		      }
			
		}
		return null;
	}

}
