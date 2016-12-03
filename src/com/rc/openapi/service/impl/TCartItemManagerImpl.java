package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TCartItemDAO;
import com.rc.openapi.service.TCartItemManager;
import com.rc.openapi.vo.TCartItem;
import com.rc.openapi.vo.TCartItemExample;

public class TCartItemManagerImpl  implements TCartItemManager {

    private TCartItemDAO tcartitemdao;

    public TCartItemManagerImpl() {
        super();
    }
    public void  setTcartitemdao(TCartItemDAO tcartitemdao){
        this.tcartitemdao=tcartitemdao;
    }
    public TCartItemDAO getTcartitemdao(){
        return this.tcartitemdao;
    }
    public     int countByExample(TCartItemExample example) throws SQLException{
        return tcartitemdao. countByExample( example);
    }

    public     int deleteByExample(TCartItemExample example) throws SQLException{
        return tcartitemdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tcartitemdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TCartItem record) throws SQLException{
        return tcartitemdao. insert( record);
    }

    public     Long insertSelective(TCartItem record) throws SQLException{
        return tcartitemdao. insertSelective( record);
    }

    public     List selectByExample(TCartItemExample example) throws SQLException{
        return tcartitemdao. selectByExample( example);
    }

    public     TCartItem selectByPrimaryKey(Long id) throws SQLException{
        return tcartitemdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TCartItem record, TCartItemExample example) throws SQLException{
        return tcartitemdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TCartItem record, TCartItemExample example) throws SQLException{
        return tcartitemdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TCartItem record) throws SQLException{
        return tcartitemdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TCartItem record) throws SQLException{
        return tcartitemdao. updateByPrimaryKey( record);
    }


}
