package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.THealthyPlanDAO;
import com.rc.openapi.service.THealthyPlanManager;
import com.rc.openapi.vo.THealthyPlan;
import com.rc.openapi.vo.THealthyPlanExample;

public class THealthyPlanManagerImpl  implements THealthyPlanManager {

    private THealthyPlanDAO thealthyplandao;

    public THealthyPlanManagerImpl() {
        super();
    }
    public void  setThealthyplandao(THealthyPlanDAO thealthyplandao){
        this.thealthyplandao=thealthyplandao;
    }
    public THealthyPlanDAO getThealthyplandao(){
        return this.thealthyplandao;
    }
    public     int countByExample(THealthyPlanExample example) throws SQLException{
        return thealthyplandao. countByExample( example);
    }

    public     int deleteByExample(THealthyPlanExample example) throws SQLException{
        return thealthyplandao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return thealthyplandao. deleteByPrimaryKey( id);
    }

    public     Long insert(THealthyPlan record) throws SQLException{
        return thealthyplandao. insert( record);
    }

    public     Long insertSelective(THealthyPlan record) throws SQLException{
        return thealthyplandao. insertSelective( record);
    }

    public     List selectByExample(THealthyPlanExample example) throws SQLException{
        return thealthyplandao. selectByExample( example);
    }

    public     THealthyPlan selectByPrimaryKey(Long id) throws SQLException{
        return thealthyplandao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(THealthyPlan record, THealthyPlanExample example) throws SQLException{
        return thealthyplandao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(THealthyPlan record, THealthyPlanExample example) throws SQLException{
        return thealthyplandao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(THealthyPlan record) throws SQLException{
        return thealthyplandao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(THealthyPlan record) throws SQLException{
        return thealthyplandao. updateByPrimaryKey( record);
    }


}
