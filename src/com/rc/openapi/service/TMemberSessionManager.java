package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberSession;
import com.rc.openapi.vo.TMemberSessionExample;

public interface TMemberSessionManager {
    int countByExample(TMemberSessionExample example) throws SQLException;

    int deleteByExample(TMemberSessionExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberSession record) throws SQLException;

    Long insertSelective(TMemberSession record) throws SQLException;

    List selectByExample(TMemberSessionExample example) throws SQLException;

    TMemberSession selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberSession record, TMemberSessionExample example) throws SQLException;

    int updateByExample(TMemberSession record, TMemberSessionExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberSession record) throws SQLException;

    int updateByPrimaryKey(TMemberSession record) throws SQLException;



}
