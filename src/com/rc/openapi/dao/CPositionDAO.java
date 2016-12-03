package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.CPosition;
import com.rc.openapi.vo.CPositionExample;

public interface CPositionDAO {
    int countByExample(CPositionExample example) throws SQLException;

    int deleteByExample(CPositionExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    Long insert(CPosition record) throws SQLException;

    Long insertSelective(CPosition record) throws SQLException;

    List selectByExample(CPositionExample example) throws SQLException;

    CPosition selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(CPosition record, CPositionExample example) throws SQLException;

    int updateByExample(CPosition record, CPositionExample example) throws SQLException;

    int updateByPrimaryKeySelective(CPosition record) throws SQLException;

    int updateByPrimaryKey(CPosition record) throws SQLException;


}
