package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TShortOrder;
import com.rc.openapi.vo.TShortOrderExample;

public interface TShortOrderDAO {
    int countByExample(TShortOrderExample example) throws SQLException;

    int deleteByExample(TShortOrderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TShortOrder record) throws SQLException;

    Long insertSelective(TShortOrder record) throws SQLException;

    List selectByExample(TShortOrderExample example) throws SQLException;

    TShortOrder selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TShortOrder record, TShortOrderExample example) throws SQLException;

    int updateByExample(TShortOrder record, TShortOrderExample example) throws SQLException;

    int updateByPrimaryKeySelective(TShortOrder record) throws SQLException;

    int updateByPrimaryKey(TShortOrder record) throws SQLException;


}
