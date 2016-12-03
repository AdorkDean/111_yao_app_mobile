package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.AppPaymethodAreaDAO;
import com.rc.openapi.service.AppPaymethodAreaManager;
import com.rc.openapi.vo.AppPaymethodArea;
import com.rc.openapi.vo.AppPaymethodAreaExample;

public class AppPaymethodAreaManagerImpl  implements AppPaymethodAreaManager {

    private AppPaymethodAreaDAO apppaymethodareadao;

    public AppPaymethodAreaManagerImpl() {
        super();
    }
    public void  setApppaymethodareadao(AppPaymethodAreaDAO apppaymethodareadao){
        this.apppaymethodareadao=apppaymethodareadao;
    }
    public AppPaymethodAreaDAO getApppaymethodareadao(){
        return this.apppaymethodareadao;
    }
    public     int countByExample(AppPaymethodAreaExample example) throws SQLException{
        return apppaymethodareadao. countByExample( example);
    }

    public     int deleteByExample(AppPaymethodAreaExample example) throws SQLException{
        return apppaymethodareadao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return apppaymethodareadao. deleteByPrimaryKey( id);
    }

    public     Long insert(AppPaymethodArea record) throws SQLException{
        return apppaymethodareadao. insert( record);
    }

    public     Long insertSelective(AppPaymethodArea record) throws SQLException{
        return apppaymethodareadao. insertSelective( record);
    }

    public     List selectByExample(AppPaymethodAreaExample example) throws SQLException{
        return apppaymethodareadao. selectByExample( example);
    }

    public     AppPaymethodArea selectByPrimaryKey(Long id) throws SQLException{
        return apppaymethodareadao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(AppPaymethodArea record, AppPaymethodAreaExample example) throws SQLException{
        return apppaymethodareadao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(AppPaymethodArea record, AppPaymethodAreaExample example) throws SQLException{
        return apppaymethodareadao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(AppPaymethodArea record) throws SQLException{
        return apppaymethodareadao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(AppPaymethodArea record) throws SQLException{
        return apppaymethodareadao. updateByPrimaryKey( record);
    }


}
