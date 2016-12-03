package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TReturn;
import com.rc.openapi.vo.TReturnExample;

public interface TReturnManager {
    int countByExample(TReturnExample example) throws SQLException;

    int deleteByExample(TReturnExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TReturn record) throws SQLException;

    Long insertSelective(TReturn record) throws Exception;

    List selectByExample(TReturnExample example) throws SQLException;

    TReturn selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TReturn record, TReturnExample example) throws SQLException;

    int updateByExample(TReturn record, TReturnExample example) throws SQLException;

    int updateByPrimaryKeySelective(TReturn record) throws SQLException;

    int updateByPrimaryKey(TReturn record) throws SQLException;



}
