package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.CPositionType;
import com.rc.openapi.vo.CPositionTypeExample;

public interface CPositionTypeDAO {
    int countByExample(CPositionTypeExample example) throws SQLException;

    int deleteByExample(CPositionTypeExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(CPositionType record) throws SQLException;

    Long insertSelective(CPositionType record) throws SQLException;

    List selectByExample(CPositionTypeExample example) throws SQLException;

    CPositionType selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(CPositionType record, CPositionTypeExample example) throws SQLException;

    int updateByExample(CPositionType record, CPositionTypeExample example) throws SQLException;

    int updateByPrimaryKeySelective(CPositionType record) throws SQLException;

    int updateByPrimaryKey(CPositionType record) throws SQLException;


}
