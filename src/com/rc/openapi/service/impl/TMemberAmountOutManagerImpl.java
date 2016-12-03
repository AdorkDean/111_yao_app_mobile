package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TMemberAmountOutDAO;
import com.rc.openapi.service.TMemberAmountOutManager;
import com.rc.openapi.vo.TMemberAmountOut;
import com.rc.openapi.vo.TMemberAmountOutExample;

public class TMemberAmountOutManagerImpl  implements TMemberAmountOutManager {

    private TMemberAmountOutDAO tmemberamountoutdao;

    public TMemberAmountOutManagerImpl() {
        super();
    }
    public void  setTmemberamountoutdao(TMemberAmountOutDAO tmemberamountoutdao){
        this.tmemberamountoutdao=tmemberamountoutdao;
    }
    public TMemberAmountOutDAO getTmemberamountoutdao(){
        return this.tmemberamountoutdao;
    }
    public     int countByExample(TMemberAmountOutExample example) throws SQLException{
        return tmemberamountoutdao. countByExample( example);
    }

    public     int deleteByExample(TMemberAmountOutExample example) throws SQLException{
        return tmemberamountoutdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tmemberamountoutdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TMemberAmountOut record) throws SQLException{
        return tmemberamountoutdao. insert( record);
    }

    public     Long insertSelective(TMemberAmountOut record) throws SQLException{
        return tmemberamountoutdao. insertSelective( record);
    }

    public     List selectByExample(TMemberAmountOutExample example) throws SQLException{
        return tmemberamountoutdao. selectByExample( example);
    }

    public     TMemberAmountOut selectByPrimaryKey(Long id) throws SQLException{
        return tmemberamountoutdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TMemberAmountOut record, TMemberAmountOutExample example) throws SQLException{
        return tmemberamountoutdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TMemberAmountOut record, TMemberAmountOutExample example) throws SQLException{
        return tmemberamountoutdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TMemberAmountOut record) throws SQLException{
        return tmemberamountoutdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TMemberAmountOut record) throws SQLException{
        return tmemberamountoutdao. updateByPrimaryKey( record);
    }


}
