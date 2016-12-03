package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TMemberArchivesLifeDisease;
import com.rc.openapi.vo.TMemberArchivesLifeDiseaseExample;

public interface TMemberArchivesLifeDiseaseManager {
    int countByExample(TMemberArchivesLifeDiseaseExample example) throws SQLException;

    int deleteByExample(TMemberArchivesLifeDiseaseExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberArchivesLifeDisease record) throws SQLException;

    Long insertSelective(TMemberArchivesLifeDisease record) throws SQLException;

    List selectByExample(TMemberArchivesLifeDiseaseExample example) throws SQLException;

    TMemberArchivesLifeDisease selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberArchivesLifeDisease record, TMemberArchivesLifeDiseaseExample example) throws SQLException;

    int updateByExample(TMemberArchivesLifeDisease record, TMemberArchivesLifeDiseaseExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberArchivesLifeDisease record) throws SQLException;

    int updateByPrimaryKey(TMemberArchivesLifeDisease record) throws SQLException;



}
