package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TGoodsDAO;
import com.rc.openapi.service.TGoodsManager;
import com.rc.openapi.vo.TGoods;
import com.rc.openapi.vo.TGoodsExample;

public class TGoodsManagerImpl  implements TGoodsManager {

    private TGoodsDAO tgoodsdao;

    public TGoodsManagerImpl() {
        super();
    }
    public void  setTgoodsdao(TGoodsDAO tgoodsdao){
        this.tgoodsdao=tgoodsdao;
    }
    public TGoodsDAO getTgoodsdao(){
        return this.tgoodsdao;
    }
    public     int countByExample(TGoodsExample example) throws SQLException{
        return tgoodsdao. countByExample( example);
    }

    public     int deleteByExample(TGoodsExample example) throws SQLException{
        return tgoodsdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tgoodsdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TGoods record) throws SQLException{
        return tgoodsdao. insert( record);
    }

    public     Long insertSelective(TGoods record) throws SQLException{
        return tgoodsdao. insertSelective( record);
    }

    public     List selectByExample(TGoodsExample example) throws SQLException{
        return tgoodsdao. selectByExample( example);
    }

    public     TGoods selectByPrimaryKey(Long id) throws SQLException{
        return tgoodsdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TGoods record, TGoodsExample example) throws SQLException{
        return tgoodsdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TGoods record, TGoodsExample example) throws SQLException{
        return tgoodsdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TGoods record) throws SQLException{
        return tgoodsdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TGoods record) throws SQLException{
        return tgoodsdao. updateByPrimaryKey( record);
    }


}
