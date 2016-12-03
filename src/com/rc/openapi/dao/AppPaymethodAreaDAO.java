package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.AppPaymethodArea;
import com.rc.openapi.vo.AppPaymethodAreaExample;

public interface AppPaymethodAreaDAO {
    int countByExample(AppPaymethodAreaExample example) throws SQLException;

    int deleteByExample(AppPaymethodAreaExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(AppPaymethodArea record) throws SQLException;

    Long insertSelective(AppPaymethodArea record) throws SQLException;

    List selectByExample(AppPaymethodAreaExample example) throws SQLException;

    AppPaymethodArea selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(AppPaymethodArea record, AppPaymethodAreaExample example) throws SQLException;

    int updateByExample(AppPaymethodArea record, AppPaymethodAreaExample example) throws SQLException;

    int updateByPrimaryKeySelective(AppPaymethodArea record) throws SQLException;

    int updateByPrimaryKey(AppPaymethodArea record) throws SQLException;


}
