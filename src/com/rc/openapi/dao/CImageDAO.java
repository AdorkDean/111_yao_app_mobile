package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.CImage;
import com.rc.openapi.vo.CImageExample;

public interface CImageDAO {
    int countByExample(CImageExample example) throws SQLException;

    int deleteByExample(CImageExample example) throws SQLException;

    int deleteByPrimaryKey(Integer id) throws SQLException;

    Long insert(CImage record) throws SQLException;

    Long insertSelective(CImage record) throws SQLException;

    List selectByExample(CImageExample example) throws SQLException;

    CImage selectByPrimaryKey(Integer id) throws SQLException;

    int updateByExampleSelective(CImage record, CImageExample example) throws SQLException;

    int updateByExample(CImage record, CImageExample example) throws SQLException;

    int updateByPrimaryKeySelective(CImage record) throws SQLException;

    int updateByPrimaryKey(CImage record) throws SQLException;


}
