package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.AppMemberTraceDAO;
import com.rc.openapi.service.AppMemberTraceManager;
import com.rc.openapi.vo.AppMemberTrace;
import com.rc.openapi.vo.AppMemberTraceExample;

public class AppMemberTraceManagerImpl  implements AppMemberTraceManager {

    private AppMemberTraceDAO appmembertracedao;

    public AppMemberTraceManagerImpl() {
        super();
    }
    public void  setAppmembertracedao(AppMemberTraceDAO appmembertracedao){
        this.appmembertracedao=appmembertracedao;
    }
    public AppMemberTraceDAO getAppmembertracedao(){
        return this.appmembertracedao;
    }
    public     int countByExample(AppMemberTraceExample example) throws SQLException{
        return appmembertracedao. countByExample( example);
    }

    public     int deleteByExample(AppMemberTraceExample example) throws SQLException{
        return appmembertracedao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return appmembertracedao. deleteByPrimaryKey( id);
    }

    public     Long insert(AppMemberTrace record) throws SQLException{
        return appmembertracedao. insert( record);
    }

    public     Long insertSelective(AppMemberTrace record) throws SQLException{
        return appmembertracedao. insertSelective( record);
    }

    public     List selectByExample(AppMemberTraceExample example) throws SQLException{
        return appmembertracedao. selectByExample( example);
    }

    public     AppMemberTrace selectByPrimaryKey(Long id) throws SQLException{
        return appmembertracedao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(AppMemberTrace record, AppMemberTraceExample example) throws SQLException{
        return appmembertracedao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(AppMemberTrace record, AppMemberTraceExample example) throws SQLException{
        return appmembertracedao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(AppMemberTrace record) throws SQLException{
        return appmembertracedao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(AppMemberTrace record) throws SQLException{
        return appmembertracedao. updateByPrimaryKey( record);
    }


}
