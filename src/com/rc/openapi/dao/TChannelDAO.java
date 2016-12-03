package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TChannel;
import com.rc.openapi.vo.TChannelExample;

public interface TChannelDAO {
    int countByExample(TChannelExample example) throws SQLException;

    int deleteByExample(TChannelExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TChannel record) throws SQLException;

    Long insertSelective(TChannel record) throws SQLException;

    List selectByExample(TChannelExample example) throws SQLException;

    TChannel selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TChannel record, TChannelExample example) throws SQLException;

    int updateByExample(TChannel record, TChannelExample example) throws SQLException;

    int updateByPrimaryKeySelective(TChannel record) throws SQLException;

    int updateByPrimaryKey(TChannel record) throws SQLException;


}
