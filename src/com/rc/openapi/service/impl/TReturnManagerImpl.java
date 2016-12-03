package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rc.openapi.dao.OpenSqlDAO;
import com.rc.openapi.dao.TReturnDAO;
import com.rc.openapi.dao.TReturnImageDAO;
import com.rc.openapi.dao.TReturnItemDAO;
import com.rc.openapi.service.TReturnManager;
import com.rc.openapi.vo.TReturn;
import com.rc.openapi.vo.TReturnExample;
import com.rc.openapi.vo.TReturnImage;
import com.rc.openapi.vo.TReturnItem;

public class TReturnManagerImpl  implements TReturnManager {

    private TReturnDAO treturndao;
    private TReturnItemDAO treturnitemdao;
    private TReturnImageDAO treturnimagedao;
    private OpenSqlDAO opensqldao;
    
    public OpenSqlDAO getOpensqldao() {
		return opensqldao;
	}
	public void setOpensqldao(OpenSqlDAO opensqldao) {
		this.opensqldao = opensqldao;
	}
	public TReturnItemDAO getTreturnitemdao() {
		return treturnitemdao;
	}
	public void setTreturnitemdao(TReturnItemDAO treturnitemdao) {
		this.treturnitemdao = treturnitemdao;
	}
	public TReturnImageDAO getTreturnimagedao() {
		return treturnimagedao;
	}
	public void setTreturnimagedao(TReturnImageDAO treturnimagedao) {
		this.treturnimagedao = treturnimagedao;
	}
	public TReturnManagerImpl() {
        super();
    }
    public void  setTreturndao(TReturnDAO treturndao){
        this.treturndao=treturndao;
    }
    public TReturnDAO getTreturndao(){
        return this.treturndao;
    }
    public     int countByExample(TReturnExample example) throws SQLException{
        return treturndao. countByExample( example);
    }

    public     int deleteByExample(TReturnExample example) throws SQLException{
        return treturndao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return treturndao. deleteByPrimaryKey( id);
    }

    public     Long insert(TReturn record) throws SQLException{
        return treturndao. insert( record);
    }

    public     Long insertSelective(TReturn record) throws Exception{
    	//商品id,名称,数量,编号                      图片 服务类型 描述内容
    	//商品id,名称,数量,编号:商品id,名称,数量,编号:
    	String products=record.getProducts();
    	if(null!=products&&!"".equals(products.trim())){
			String[] pros = products.split(":");
			for (String pro : pros) {
				record.setId(null);
				String[] p = pro.split(",");
		        Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("len", 6);
				    String returnOrderSn = (String) opensqldao.selectForObjectByMap(paramMap,"t_return.select_new_return_ordersn");
					while (returnOrderSn == null || "".equals(returnOrderSn) || "-1".equals(returnOrderSn)) {
						returnOrderSn = (String) opensqldao.selectForObjectByMap(paramMap, "t_return.select_new_return_ordersn");
					}
					record.setOrderSn(returnOrderSn);
					record.setCreateTime(new Date());
					record.setOrderStatus(0);
		    		long rid = treturndao. insertSelective( record);
					TReturnItem tReturnItem = new TReturnItem();
					tReturnItem.setCreateTime(new Date());
					tReturnItem.setGoodsId(Long.parseLong(p[0]));
					tReturnItem.setQuantity(Integer.parseInt(p[2]));
					tReturnItem.setReturnId(rid);
					treturnitemdao.insertSelective(tReturnItem);
					//图片集合
					List<String> imgPathList = record.getImgPathList();
					if(null!=imgPathList&&0<imgPathList.size()){
						for (String imgp : imgPathList) {
							TReturnImage img = new TReturnImage();
							img.setImageUrl(imgp);
							img.setReturnId(rid);
							treturnimagedao.insertSelective(img);
						}
					}
			}
		}else {
			throw new Exception("订单无商品!");
		}
        return 1l;
    }
    public     List selectByExample(TReturnExample example) throws SQLException{
        return treturndao. selectByExample( example);
    }

    public     TReturn selectByPrimaryKey(Long id) throws SQLException{
        return treturndao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TReturn record, TReturnExample example) throws SQLException{
        return treturndao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TReturn record, TReturnExample example) throws SQLException{
        return treturndao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TReturn record) throws SQLException{
        return treturndao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TReturn record) throws SQLException{
        return treturndao. updateByPrimaryKey( record);
    }


}
