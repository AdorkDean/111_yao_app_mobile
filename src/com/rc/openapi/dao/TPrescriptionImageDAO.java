package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TPrescriptionImage;
import com.rc.openapi.vo.TPrescriptionImageExample;

public interface TPrescriptionImageDAO {
    int countByExample(TPrescriptionImageExample example) throws SQLException;

    int deleteByExample(TPrescriptionImageExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TPrescriptionImage record) throws SQLException;

    Long insertSelective(TPrescriptionImage record) throws SQLException;

    List selectByExample(TPrescriptionImageExample example) throws SQLException;

    TPrescriptionImage selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TPrescriptionImage record, TPrescriptionImageExample example) throws SQLException;

    int updateByExample(TPrescriptionImage record, TPrescriptionImageExample example) throws SQLException;

    int updateByPrimaryKeySelective(TPrescriptionImage record) throws SQLException;

    int updateByPrimaryKey(TPrescriptionImage record) throws SQLException;


}
