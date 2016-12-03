package com.rc.openapi.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.openapi.dao.IDaoManager;
import com.rc.openapi.dao.TOrderDAO;
import com.rc.openapi.dao.TOrderItemDAO;
import com.rc.openapi.dao.TPromGiftDAO;
import com.rc.openapi.model.cart.CartGift;
import com.rc.openapi.model.cart.CartParam;
import com.rc.openapi.model.cart.CartPromModel;
import com.rc.openapi.model.cart.GoodsPrice;
import com.rc.openapi.vo.TPromItem;
import com.rc.openapi.vo.TPromItemExample;
import com.rc.openapi.vo.TPromotion;


public class CartDaoImpl implements IDaoManager {
	
	
	private SqlMapClient sqlMapClient;
	private TPromItemDAOImpl tpromitemdao;
	private TPromotionDAOImpl tpromotiondao;
	private TPromGiftDAO tpromgiftdao;
	
	private TOrderItemDAO torderitemdao;
	
	private TOrderDAO torderdao;
	

    public static void main(String[] args) throws SQLException {
        
    	CartDaoImpl o = new CartDaoImpl();
    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
    	o.sqlMapClient = (SqlMapClient) context.getBean("sqlMapClient");
    	o.tpromotiondao = (TPromotionDAOImpl) context.getBean("tpromotiondao");
    	o.tpromitemdao = (TPromItemDAOImpl) context.getBean("tpromitemdao");
    	
//    	String key = "F6CE1A34-A7E3-45E0-B742-DFE0D73BB2B6";
    	
//    	CartParam arg = new CartParam();
//    	arg.setCartkey(key);
    	
//    	Map map = o.accounts(arg);
    	
//    	System.out.println("应付金额----"+map.get("money"));
//    	System.out.println("优惠金额----"+map.get("youhui"));
    	
//    	o.synCart(100, "F6CE1A34-A7E3-45E0-B742-DFE0D73BB2B6");
    	
    	List<Long> list = new ArrayList<Long>();
    	
    	list.add(1L);
    	list.add(2L);
    	list.add(3L);
    	
    	list.remove(2L);
    	
    	for(Long l : list){
    		System.out.println("===>"+l);
    	}
    	
    }
    

	
	public TPromotion getPromByGoods(long goodsid) throws SQLException{
		
		TPromotion prom = (TPromotion) sqlMapClient.queryForObject("mycart.is-goods-promotion", goodsid);
		if(prom==null){		// 该商品的分类是否在搞促销
			 prom = (TPromotion) sqlMapClient.queryForObject("mycart.is-category-promotion", goodsid);
		}
		return prom;
		
	}

	
	
	public Map accounts(CartParam arg) throws SQLException {

//		System.out.println("dao accounts....");
		
		long ArgUserId = arg.getUserId();
		if(ArgUserId!=0){
			arg.setIslogin(true);
		}else{
			arg.setIslogin(false);
		}
		
		Map map = new HashMap();
		Map result = new HashMap();

		BigDecimal total = new BigDecimal(0); // 总数
		BigDecimal money = new BigDecimal(0); // 优惠完之后的数目
		BigDecimal minmoney = new BigDecimal(0); //满减的钱(所的类型促销所有的满减加起来的值 ，最后用最钱数减去该值)
		
//		List<CartGift> giftList = new ArrayList<CartGift>();
		Map<Long,List<CartGift>> giftMap = new HashMap<Long,List<CartGift>>();
		
		List<Long> giftList = new ArrayList<Long>();
		
		Map<Long,Integer> cnt = new HashMap<Long,Integer>(); // 数量记数 key=id value=数量 
		
		

	    if (arg.isIslogin()) {
			map.put("userid", arg.getUserId());
		} else {
			map.put("uuid", arg.getCartkey());
		}
		List<CartPromModel> cartlist = sqlMapClient.queryForList("mycart.prom_cart", map);

		
//		System.out.println("购物车大小--->"+cartlist.size());
		
		int count = 0;

		List<CartPromModel> normal = new ArrayList<CartPromModel>();
		List<CartPromModel> single = new ArrayList<CartPromModel>();
		Map<Long, List<CartPromModel>> MixMap = new HashMap<Long, List<CartPromModel>>();
//		Map<Long, List<CartPromModel>> CategoryMap = new HashMap<Long, List<CartPromModel>>();
		
		List<Long> category = new ArrayList<Long>();	// 存放父分类在促销的商品

		TPromotion prom = null;
		for (CartPromModel m : cartlist) 
		{
			cnt.put(m.getGoods_id(), m.getQuantity());
			if (m.getPromotion_id() == null) 	
			{ 
				
				// 商品不为单品，也没有满返，分类是否在促销
				prom = getPromByGoods(m.getGoods_id());
				
				if(prom==null){
					normal.add(m);	// 正常商品
				}else{	// 分类在促销
					if(prom.getType()==3)
					category.add(m.getGoods_id());
				}
				
			} else{
				
				// 如果在促销中，促销是否正在进行
				
				prom = tpromotiondao.selectByPrimaryKey(m.getPromotion_id());
				
				if(prom!=null)
				{
					if(prom.getStatus()!=2){	// 如果促销还没有开始
						normal.add(m); // 正常商品处理
					}else{
						if (m.getType() == 1) { // 单品
							single.add(m);
						} else if (m.getType() == 2) { // 满返
							boolean cont = MixMap.containsKey(m.getPromotion_id());
							if (cont) {
								MixMap.get(m.getPromotion_id()).add(m);
							} else {
								List<CartPromModel> list = new ArrayList<CartPromModel>();
								list.add(m);
								MixMap.put(m.getPromotion_id(), list);
							}
						} else if(m.getType()==3){ // 分类促销
							category.add(m.getGoods_id());
						}
					}
				}
				
			}
			count = count + m.getQuantity();
			total = total.add(m.getApp_price().multiply(new BigDecimal(m.getQuantity())));
//			map1.put(m.getGoods_id(), m.getQuantity());
		}

		// 正常商品
		/*for (CartPromModel cp : normal) {
			money = money.add(cp.getPc_price().multiply(new BigDecimal(cp.getQuantity())));
		}*/
		
		
		// 算单品
		TPromItemExample example = new TPromItemExample();
		for (CartPromModel cp : single)
		{
			example.createCriteria().andPidEqualTo(cp.getPromotion_id()).andTargetIdEqualTo(cp.getGoods_id());
			
			List<TPromItem> list = tpromitemdao.selectByExample(example);
			if(list.size()>0)
			{
				TPromItem item = list.get(0);
				if (item != null) {
					minmoney = minmoney.add((cp.getApp_price().subtract(item.getPrice())).multiply(new BigDecimal(cp.getQuantity())));
				}
			} 
			example.clear();

			// 查询赠品
			CartGift gift = (CartGift)sqlMapClient.queryForObject("mycart.cart-prom-gift", cp.getPromotion_id());
			if(gift!=null){
				// giftMap.put(cp.getPromotion_id(), gift);
			}
			
		}
		
		// 算满返
		Set<Long> keys = MixMap.keySet();
		List<CartPromModel> l = null;
		StringBuilder sb = null;
		Map mixmap = new HashMap();
		Map<Long,BigDecimal> mp = new HashMap<Long,BigDecimal>();
		GoodsPrice goodsPrice = null;
		BigDecimal bigdc = new BigDecimal(0);
		for (Long k : keys) {
			l = MixMap.get(k);

			for (CartPromModel cpm : l) {
				goodsPrice = getGoodsPrice(cpm.getGoods_id().longValue());
				bigdc = bigdc.add(goodsPrice.getPc_price().multiply(new BigDecimal(cpm.getQuantity())));
			}
			mp.put(k, bigdc);
		}
		
		Set<Long> mkeys = mp.keySet();
		BigDecimal dem = null;

		for (Long k : keys) {
			dem = mp.get(k);
			prom = tpromotiondao.selectByPrimaryKey(k);
			int com = dem.compareTo(prom.getMinAmount()); // 与相应的促销最低消费值比较 
			if(com==0 || com==1){  // 如果达最低消费金额 
				minmoney = minmoney.add(prom.getAmount());
				// 查询赠品
//				CartGift gift = (CartGift)sqlMapClient.queryForObject("mycart.cart-prom-gift", k);
				giftList.add(prom.getId());
			}else{
				giftList.remove(prom.getId());
			}
		}
		
//		System.out.println("category=============="+category.size());
		
		// 算分类
		Map<Long,Long> map1 = new HashMap<Long,Long>();
		
		TPromotion p = null;
		for(Long goosdid : category)
		{
//			System.out.println("========++++++++++++=========>"+goosdid);
			p = getPromByGoods(goosdid);
			if(p!=null && p.getType()==3)	// 查询促销所有分类
			{
				map1.put(goosdid, p.getId());
			}
		}
		
		Map<Long,List<Long>> map2 = new HashMap<Long,List<Long>>();
		List<Long> valueGoodsId = new ArrayList<Long>();
		if(map1.size()>0)
		{
			long promIdKey ;
			Set<Long> set  = map1.keySet(); // key=商品ID
			for(Long goodsid : set)
			{
				promIdKey = map1.get(goodsid);
				valueGoodsId.add(goodsid);
				if(map2.containsKey(promIdKey)){ // 存在ID该促销的的KEY
					map2.get(promIdKey).add(goodsid);
				}else{
					valueGoodsId  =  new ArrayList<Long>();
					valueGoodsId.add(goodsid);
					map2.put(promIdKey, valueGoodsId);
				}
			}
		}
		
		if(map2.size()>0)
		{
			Set<Long> set  = map2.keySet();
			TPromotion promotion = null;
			BigDecimal cateMenoy = new BigDecimal(0);
			List<Long> valGoodsIdList = null;
			for(Long promid : set)	// 促销ID
			{
				valGoodsIdList = map2.get(promid);
				
				for(long goodsid : valGoodsIdList)
				{
					cateMenoy = cateMenoy.add(getGoodsPrice(goodsid).getPc_price().multiply(new BigDecimal(cnt.get(goodsid))));
				}
				promotion = tpromotiondao.selectByPrimaryKey(promid);
				int com = cateMenoy.compareTo(promotion.getMinAmount()); // 与相应的促销最低消费值比较 
//				System.out.println("cateMenoy="+cateMenoy+" promotion.getMinAmount()="+promotion.getMinAmount());
				if(com==0 || com==1){  // 如果达最低消费金额 
					minmoney = minmoney.add(promotion.getAmount());
				}
				cateMenoy = new BigDecimal(0);
			}
		}
		
		// map2的格式： key=促销ID  List:value=商品ID1,商品ID2,商品ID3
		
		StringBuilder sbl = new StringBuilder("0");
		for(Long id : giftList){
			sbl.append(","+id);
		}
		
	    map.put("giftpid", sbl.toString());
	    List<CartGift> giftlist = sqlMapClient.queryForList("mycart.many-prom-gift", map);
		
//	    System.out.println("===========>"+minmoney);
		
		result.put("count", count);
		result.put("money", total.subtract(minmoney));
		result.put("youhui",minmoney);
		result.put("gift", giftlist);
      
		return result;
	}

	public GoodsPrice getGoodsPrice(long id) throws SQLException{
		Map m = new HashMap();
		m.put("goodsid",id);
		GoodsPrice goodsprice = (GoodsPrice) sqlMapClient.queryForObject("mycart.get-goods-price",m);
		return goodsprice;
	}
	
	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public TPromItemDAOImpl getTpromitemdao() {
		return tpromitemdao;
	}

	public void setTpromitemdao(TPromItemDAOImpl tpromitemdao) {
		this.tpromitemdao = tpromitemdao;
	}

	public TPromotionDAOImpl getTpromotiondao() {
		return tpromotiondao;
	}

	public void setTpromotiondao(TPromotionDAOImpl tpromotiondao) {
		this.tpromotiondao = tpromotiondao;
	}



	public TPromGiftDAO getTpromgiftdao() {
		return tpromgiftdao;
	}



	public void setTpromgiftdao(TPromGiftDAO tpromgiftdao) {
		this.tpromgiftdao = tpromgiftdao;
	}



	public TOrderItemDAO getTorderitemdao() {
		return torderitemdao;
	}



	public void setTorderitemdao(TOrderItemDAO torderitemdao) {
		this.torderitemdao = torderitemdao;
	}



	public TOrderDAO getTorderdao() {
		return torderdao;
	}



	public void setTorderdao(TOrderDAO torderdao) {
		this.torderdao = torderdao;
	}
	

}
