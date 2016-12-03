package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TLeaderDAO;
import com.rc.openapi.service.TLeaderManager;
import com.rc.openapi.vo.TLeader;
import com.rc.openapi.vo.TLeaderExample;

public class TLeaderManagerImpl  implements TLeaderManager {

    private TLeaderDAO tleaderdao;

    public TLeaderManagerImpl() {
        super();
    }
    public void  setTleaderdao(TLeaderDAO tleaderdao){
        this.tleaderdao=tleaderdao;
    }
    public TLeaderDAO getTleaderdao(){
        return this.tleaderdao;
    }
    public     int countByExample(TLeaderExample example) throws SQLException{
        return tleaderdao. countByExample( example);
    }

    public     int deleteByExample(TLeaderExample example) throws SQLException{
        return tleaderdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tleaderdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TLeader record) throws SQLException{
        return tleaderdao. insert( record);
    }

    public     Long insertSelective(TLeader record) throws SQLException{
        return tleaderdao. insertSelective( record);
    }

    public     List selectByExample(TLeaderExample example) throws SQLException{
        return tleaderdao. selectByExample( example);
    }

    public     TLeader selectByPrimaryKey(Long id) throws SQLException{
        return tleaderdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TLeader record, TLeaderExample example) throws SQLException{
        return tleaderdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TLeader record, TLeaderExample example) throws SQLException{
        return tleaderdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TLeader record) throws SQLException{
        return tleaderdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TLeader record) throws SQLException{
        return tleaderdao. updateByPrimaryKey( record);
    }


}
