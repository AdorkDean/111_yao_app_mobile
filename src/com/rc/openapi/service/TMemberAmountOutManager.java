package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberAmountOut;
import com.rc.openapi.vo.TMemberAmountOutExample;

public interface TMemberAmountOutManager {
    int countByExample(TMemberAmountOutExample example) throws SQLException;

    int deleteByExample(TMemberAmountOutExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberAmountOut record) throws SQLException;

    Long insertSelective(TMemberAmountOut record) throws SQLException;

    List selectByExample(TMemberAmountOutExample example) throws SQLException;

    TMemberAmountOut selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberAmountOut record, TMemberAmountOutExample example) throws SQLException;

    int updateByExample(TMemberAmountOut record, TMemberAmountOutExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberAmountOut record) throws SQLException;

    int updateByPrimaryKey(TMemberAmountOut record) throws SQLException;



}
