package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TBrand;
import com.rc.openapi.vo.TBrandExample;

public interface TBrandDAO {
    int countByExample(TBrandExample example) throws SQLException;

    int deleteByExample(TBrandExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TBrand record) throws SQLException;

    Long insertSelective(TBrand record) throws SQLException;

    List selectByExample(TBrandExample example) throws SQLException;

    TBrand selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TBrand record, TBrandExample example) throws SQLException;

    int updateByExample(TBrand record, TBrandExample example) throws SQLException;

    int updateByPrimaryKeySelective(TBrand record) throws SQLException;

    int updateByPrimaryKey(TBrand record) throws SQLException;


}
