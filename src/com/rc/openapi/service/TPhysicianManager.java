package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TPhysician;
import com.rc.openapi.vo.TPhysicianExample;

public interface TPhysicianManager {
    int countByExample(TPhysicianExample example) throws SQLException;

    int deleteByExample(TPhysicianExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TPhysician record) throws SQLException;

    Long insertSelective(TPhysician record) throws SQLException;

    List selectByExample(TPhysicianExample example) throws SQLException;

    TPhysician selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TPhysician record, TPhysicianExample example) throws SQLException;

    int updateByExample(TPhysician record, TPhysicianExample example) throws SQLException;

    int updateByPrimaryKeySelective(TPhysician record) throws SQLException;

    int updateByPrimaryKey(TPhysician record) throws SQLException;



}
