package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.THealthyPlanSymptomDAO;
import com.rc.openapi.service.THealthyPlanSymptomManager;
import com.rc.openapi.vo.THealthyPlanSymptom;
import com.rc.openapi.vo.THealthyPlanSymptomExample;

public class THealthyPlanSymptomManagerImpl  implements THealthyPlanSymptomManager {

    private THealthyPlanSymptomDAO thealthyplansymptomdao;

    public THealthyPlanSymptomManagerImpl() {
        super();
    }
    public void  setThealthyplansymptomdao(THealthyPlanSymptomDAO thealthyplansymptomdao){
        this.thealthyplansymptomdao=thealthyplansymptomdao;
    }
    public THealthyPlanSymptomDAO getThealthyplansymptomdao(){
        return this.thealthyplansymptomdao;
    }
    public     int countByExample(THealthyPlanSymptomExample example) throws SQLException{
        return thealthyplansymptomdao. countByExample( example);
    }

    public     int deleteByExample(THealthyPlanSymptomExample example) throws SQLException{
        return thealthyplansymptomdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return thealthyplansymptomdao. deleteByPrimaryKey( id);
    }

    public     Long insert(THealthyPlanSymptom record) throws SQLException{
        return thealthyplansymptomdao. insert( record);
    }

    public     Long insertSelective(THealthyPlanSymptom record) throws SQLException{
        return thealthyplansymptomdao. insertSelective( record);
    }

    public     List selectByExample(THealthyPlanSymptomExample example) throws SQLException{
        return thealthyplansymptomdao. selectByExample( example);
    }

    public     THealthyPlanSymptom selectByPrimaryKey(Long id) throws SQLException{
        return thealthyplansymptomdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(THealthyPlanSymptom record, THealthyPlanSymptomExample example) throws SQLException{
        return thealthyplansymptomdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(THealthyPlanSymptom record, THealthyPlanSymptomExample example) throws SQLException{
        return thealthyplansymptomdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(THealthyPlanSymptom record) throws SQLException{
        return thealthyplansymptomdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(THealthyPlanSymptom record) throws SQLException{
        return thealthyplansymptomdao. updateByPrimaryKey( record);
    }


}
