package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TChannelDAO;
import com.rc.openapi.service.TChannelManager;
import com.rc.openapi.vo.TChannel;
import com.rc.openapi.vo.TChannelExample;

public class TChannelManagerImpl  implements TChannelManager {

    private TChannelDAO tchanneldao;

    public TChannelManagerImpl() {
        super();
    }
    public void  setTchanneldao(TChannelDAO tchanneldao){
        this.tchanneldao=tchanneldao;
    }
    public TChannelDAO getTchanneldao(){
        return this.tchanneldao;
    }
    public     int countByExample(TChannelExample example) throws SQLException{
        return tchanneldao. countByExample( example);
    }

    public     int deleteByExample(TChannelExample example) throws SQLException{
        return tchanneldao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tchanneldao. deleteByPrimaryKey( id);
    }

    public     Long insert(TChannel record) throws SQLException{
        return tchanneldao. insert( record);
    }

    public     Long insertSelective(TChannel record) throws SQLException{
        return tchanneldao. insertSelective( record);
    }

    public     List selectByExample(TChannelExample example) throws SQLException{
        return tchanneldao. selectByExample( example);
    }

    public     TChannel selectByPrimaryKey(Long id) throws SQLException{
        return tchanneldao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TChannel record, TChannelExample example) throws SQLException{
        return tchanneldao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TChannel record, TChannelExample example) throws SQLException{
        return tchanneldao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TChannel record) throws SQLException{
        return tchanneldao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TChannel record) throws SQLException{
        return tchanneldao. updateByPrimaryKey( record);
    }


}
