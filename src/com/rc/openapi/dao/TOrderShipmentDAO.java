package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TOrderShipment;
import com.rc.openapi.vo.TOrderShipmentExample;

public interface TOrderShipmentDAO {
    int countByExample(TOrderShipmentExample example) throws SQLException;

    int deleteByExample(TOrderShipmentExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TOrderShipment record) throws SQLException;

    Long insertSelective(TOrderShipment record) throws SQLException;

    List selectByExample(TOrderShipmentExample example) throws SQLException;

    TOrderShipment selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TOrderShipment record, TOrderShipmentExample example) throws SQLException;

    int updateByExample(TOrderShipment record, TOrderShipmentExample example) throws SQLException;

    int updateByPrimaryKeySelective(TOrderShipment record) throws SQLException;

    int updateByPrimaryKey(TOrderShipment record) throws SQLException;


}
