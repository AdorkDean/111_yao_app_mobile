package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberReceiver;
import com.rc.openapi.vo.TMemberReceiverExample;

public interface TMemberReceiverManager {
    int countByExample(TMemberReceiverExample example) throws SQLException;

    int deleteByExample(TMemberReceiverExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberReceiver record) throws SQLException;

    Long insertSelective(TMemberReceiver record) throws SQLException;

    List selectByExample(TMemberReceiverExample example) throws SQLException;

    TMemberReceiver selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberReceiver record, TMemberReceiverExample example) throws SQLException;

    int updateByExample(TMemberReceiver record, TMemberReceiverExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberReceiver record) throws SQLException;

    int updateByPrimaryKey(TMemberReceiver record) throws SQLException;

    int updateDefaultArea(Long memberId, Long areaId) throws SQLException;

    int userReceiverUpOrAdd(TMemberReceiver record,Long userId) throws SQLException;
}
