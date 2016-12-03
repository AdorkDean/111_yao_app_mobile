package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TPromItem;
import com.rc.openapi.vo.TPromItemExample;

public interface TPromItemManager {
    int countByExample(TPromItemExample example) throws SQLException;

    int deleteByExample(TPromItemExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TPromItem record) throws SQLException;

    Long insertSelective(TPromItem record) throws SQLException;

    List selectByExample(TPromItemExample example) throws SQLException;

    TPromItem selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TPromItem record, TPromItemExample example) throws SQLException;

    int updateByExample(TPromItem record, TPromItemExample example) throws SQLException;

    int updateByPrimaryKeySelective(TPromItem record) throws SQLException;

    int updateByPrimaryKey(TPromItem record) throws SQLException;



}
