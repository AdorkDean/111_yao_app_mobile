package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.HuanxinUserDAO;
import com.rc.openapi.service.HuanxinUserManager;
import com.rc.openapi.vo.HuanxinUser;
import com.rc.openapi.vo.HuanxinUserExample;

public class HuanxinUserManagerImpl  implements HuanxinUserManager {

    private HuanxinUserDAO huanxinuserdao;

    public HuanxinUserManagerImpl() {
        super();
    }
    public void  setHuanxinuserdao(HuanxinUserDAO huanxinuserdao){
        this.huanxinuserdao=huanxinuserdao;
    }
    public HuanxinUserDAO getHuanxinuserdao(){
        return this.huanxinuserdao;
    }
    public     int countByExample(HuanxinUserExample example) throws SQLException{
        return huanxinuserdao. countByExample( example);
    }

    public     int deleteByExample(HuanxinUserExample example) throws SQLException{
        return huanxinuserdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return huanxinuserdao. deleteByPrimaryKey( id);
    }

    public     Long insert(HuanxinUser record) throws SQLException{
        return huanxinuserdao. insert( record);
    }

    public     Long insertSelective(HuanxinUser record) throws SQLException{
        return huanxinuserdao. insertSelective( record);
    }

    public     List selectByExample(HuanxinUserExample example) throws SQLException{
        return huanxinuserdao. selectByExample( example);
    }

    public     HuanxinUser selectByPrimaryKey(Long id) throws SQLException{
        return huanxinuserdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(HuanxinUser record, HuanxinUserExample example) throws SQLException{
        return huanxinuserdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(HuanxinUser record, HuanxinUserExample example) throws SQLException{
        return huanxinuserdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(HuanxinUser record) throws SQLException{
        return huanxinuserdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(HuanxinUser record) throws SQLException{
        return huanxinuserdao. updateByPrimaryKey( record);
    }


}
