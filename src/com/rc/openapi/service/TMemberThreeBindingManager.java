package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberThreeBinding;
import com.rc.openapi.vo.TMemberThreeBindingExample;

public interface TMemberThreeBindingManager {
    int countByExample(TMemberThreeBindingExample example) throws SQLException;

    int deleteByExample(TMemberThreeBindingExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberThreeBinding record) throws SQLException;

    Long insertSelective(TMemberThreeBinding record) throws SQLException;

    List selectByExample(TMemberThreeBindingExample example) throws SQLException;

    TMemberThreeBinding selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberThreeBinding record, TMemberThreeBindingExample example) throws SQLException;

    int updateByExample(TMemberThreeBinding record, TMemberThreeBindingExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberThreeBinding record) throws SQLException;

    int updateByPrimaryKey(TMemberThreeBinding record) throws SQLException;



}
