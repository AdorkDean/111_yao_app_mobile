package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberReceiverLatLon;
import com.rc.openapi.vo.TMemberReceiverLatLonExample;

public interface TMemberReceiverLatLonManager {
    int countByExample(TMemberReceiverLatLonExample example) throws SQLException;

    int deleteByExample(TMemberReceiverLatLonExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberReceiverLatLon record) throws SQLException;

    Long insertSelective(TMemberReceiverLatLon record) throws SQLException;

    List selectByExample(TMemberReceiverLatLonExample example) throws SQLException;

    TMemberReceiverLatLon selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberReceiverLatLon record, TMemberReceiverLatLonExample example) throws SQLException;

    int updateByExample(TMemberReceiverLatLon record, TMemberReceiverLatLonExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberReceiverLatLon record) throws SQLException;

    int updateByPrimaryKey(TMemberReceiverLatLon record) throws SQLException;
    /**
     * 设置为默认收货地址
     * @param receiverId
     * @return
     * @throws SQLException
     */
    boolean receiverLatlonSetDefault(long receiverId,long memberId) throws SQLException;
 
}
