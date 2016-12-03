package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TCustomerCall;
import com.rc.openapi.vo.TCustomerCallExample;

public interface TCustomerCallManager {
    int countByExample(TCustomerCallExample example) throws SQLException;

    int deleteByExample(TCustomerCallExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TCustomerCall record) throws SQLException;

    Long insertSelective(TCustomerCall record) throws SQLException;

    List selectByExample(TCustomerCallExample example) throws SQLException;

    TCustomerCall selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TCustomerCall record, TCustomerCallExample example) throws SQLException;

    int updateByExample(TCustomerCall record, TCustomerCallExample example) throws SQLException;

    int updateByPrimaryKeySelective(TCustomerCall record) throws SQLException;

    int updateByPrimaryKey(TCustomerCall record) throws SQLException;



}
