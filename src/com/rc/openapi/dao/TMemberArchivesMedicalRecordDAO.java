package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberArchivesMedicalRecord;
import com.rc.openapi.vo.TMemberArchivesMedicalRecordExample;

public interface TMemberArchivesMedicalRecordDAO {
    int countByExample(TMemberArchivesMedicalRecordExample example) throws SQLException;

    int deleteByExample(TMemberArchivesMedicalRecordExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberArchivesMedicalRecord record) throws SQLException;

    Long insertSelective(TMemberArchivesMedicalRecord record) throws SQLException;

    List selectByExample(TMemberArchivesMedicalRecordExample example) throws SQLException;

    TMemberArchivesMedicalRecord selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberArchivesMedicalRecord record, TMemberArchivesMedicalRecordExample example) throws SQLException;

    int updateByExample(TMemberArchivesMedicalRecord record, TMemberArchivesMedicalRecordExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberArchivesMedicalRecord record) throws SQLException;

    int updateByPrimaryKey(TMemberArchivesMedicalRecord record) throws SQLException;


}
