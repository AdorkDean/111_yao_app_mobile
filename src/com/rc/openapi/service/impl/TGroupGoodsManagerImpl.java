package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TGroupGoodsDAO;
import com.rc.openapi.service.TGroupGoodsManager;
import com.rc.openapi.vo.TGroupGoods;
import com.rc.openapi.vo.TGroupGoodsExample;

public class TGroupGoodsManagerImpl  implements TGroupGoodsManager {

    private TGroupGoodsDAO tgroupgoodsdao;

    public TGroupGoodsManagerImpl() {
        super();
    }
    public void  setTgroupgoodsdao(TGroupGoodsDAO tgroupgoodsdao){
        this.tgroupgoodsdao=tgroupgoodsdao;
    }
    public TGroupGoodsDAO getTgroupgoodsdao(){
        return this.tgroupgoodsdao;
    }
    public     int countByExample(TGroupGoodsExample example) throws SQLException{
        return tgroupgoodsdao. countByExample( example);
    }

    public     int deleteByExample(TGroupGoodsExample example) throws SQLException{
        return tgroupgoodsdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tgroupgoodsdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TGroupGoods record) throws SQLException{
        return tgroupgoodsdao. insert( record);
    }

    public     Long insertSelective(TGroupGoods record) throws SQLException{
        return tgroupgoodsdao. insertSelective( record);
    }

    public     List selectByExample(TGroupGoodsExample example) throws SQLException{
        return tgroupgoodsdao. selectByExample( example);
    }

    public     TGroupGoods selectByPrimaryKey(Long id) throws SQLException{
        return tgroupgoodsdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TGroupGoods record, TGroupGoodsExample example) throws SQLException{
        return tgroupgoodsdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TGroupGoods record, TGroupGoodsExample example) throws SQLException{
        return tgroupgoodsdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TGroupGoods record) throws SQLException{
        return tgroupgoodsdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TGroupGoods record) throws SQLException{
        return tgroupgoodsdao. updateByPrimaryKey( record);
    }


}
