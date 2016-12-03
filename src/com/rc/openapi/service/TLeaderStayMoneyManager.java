package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rc.openapi.vo.TLeaderStayMoney;
import com.rc.openapi.vo.TLeaderStayMoneyExample;
import com.rc.openapi.vo.TOrder;

public interface TLeaderStayMoneyManager {
    int countByExample(TLeaderStayMoneyExample example) throws SQLException;

    int deleteByExample(TLeaderStayMoneyExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TLeaderStayMoney record) throws SQLException;

    Long insertSelective(TLeaderStayMoney record) throws SQLException;

    List selectByExample(TLeaderStayMoneyExample example) throws SQLException;

    TLeaderStayMoney selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TLeaderStayMoney record, TLeaderStayMoneyExample example) throws SQLException;

    int updateByExample(TLeaderStayMoney record, TLeaderStayMoneyExample example) throws SQLException;

    int updateByPrimaryKeySelective(TLeaderStayMoney record) throws SQLException;

    int updateByPrimaryKey(TLeaderStayMoney record) throws SQLException;

	void cancelOrderInsertStayByHDF(Map<String, Object> phoneMap, TOrder tOrder) throws SQLException;



}
