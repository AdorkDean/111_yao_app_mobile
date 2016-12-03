package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberArchivesExamination;
import com.rc.openapi.vo.TMemberArchivesExaminationExample;

public interface TMemberArchivesExaminationDAO {
    int countByExample(TMemberArchivesExaminationExample example) throws SQLException;

    int deleteByExample(TMemberArchivesExaminationExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberArchivesExamination record) throws SQLException;

    Long insertSelective(TMemberArchivesExamination record) throws SQLException;

    List selectByExample(TMemberArchivesExaminationExample example) throws SQLException;

    TMemberArchivesExamination selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberArchivesExamination record, TMemberArchivesExaminationExample example) throws SQLException;

    int updateByExample(TMemberArchivesExamination record, TMemberArchivesExaminationExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberArchivesExamination record) throws SQLException;

    int updateByPrimaryKey(TMemberArchivesExamination record) throws SQLException;


}
