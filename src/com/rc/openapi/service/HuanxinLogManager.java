package com.rc.openapi.service;



import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.HuanxinLog;
import com.rc.openapi.vo.HuanxinLogExample;

public interface HuanxinLogManager {
    int countByExample(HuanxinLogExample example) throws SQLException;

    int deleteByExample(HuanxinLogExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(HuanxinLog record) throws SQLException;

    Long insertSelective(HuanxinLog record) throws SQLException;

    List selectByExample(HuanxinLogExample example) throws SQLException;

    HuanxinLog selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(HuanxinLog record, HuanxinLogExample example) throws SQLException;

    int updateByExample(HuanxinLog record, HuanxinLogExample example) throws SQLException;

    int updateByPrimaryKeySelective(HuanxinLog record) throws SQLException;

    int updateByPrimaryKey(HuanxinLog record) throws SQLException;



}
