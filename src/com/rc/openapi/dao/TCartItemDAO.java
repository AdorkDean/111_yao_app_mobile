package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TCartItem;
import com.rc.openapi.vo.TCartItemExample;

public interface TCartItemDAO {
    int countByExample(TCartItemExample example) throws SQLException;

    int deleteByExample(TCartItemExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TCartItem record) throws SQLException;

    Long insertSelective(TCartItem record) throws SQLException;

    List selectByExample(TCartItemExample example) throws SQLException;

    TCartItem selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TCartItem record, TCartItemExample example) throws SQLException;

    int updateByExample(TCartItem record, TCartItemExample example) throws SQLException;

    int updateByPrimaryKeySelective(TCartItem record) throws SQLException;

    int updateByPrimaryKey(TCartItem record) throws SQLException;


}
