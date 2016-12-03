package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TGoodsPremiums;
import com.rc.openapi.vo.TGoodsPremiumsExample;

public interface TGoodsPremiumsManager {
    int countByExample(TGoodsPremiumsExample example) throws SQLException;

    int deleteByExample(TGoodsPremiumsExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TGoodsPremiums record) throws SQLException;

    Long insertSelective(TGoodsPremiums record) throws SQLException;

    List selectByExample(TGoodsPremiumsExample example) throws SQLException;

    TGoodsPremiums selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TGoodsPremiums record, TGoodsPremiumsExample example) throws SQLException;

    int updateByExample(TGoodsPremiums record, TGoodsPremiumsExample example) throws SQLException;

    int updateByPrimaryKeySelective(TGoodsPremiums record) throws SQLException;

    int updateByPrimaryKey(TGoodsPremiums record) throws SQLException;



}
