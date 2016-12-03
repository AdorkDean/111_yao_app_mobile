package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.THealthyPlanClassification;
import com.rc.openapi.vo.THealthyPlanClassificationExample;

public interface THealthyPlanClassificationDAO {
    int countByExample(THealthyPlanClassificationExample example) throws SQLException;

    int deleteByExample(THealthyPlanClassificationExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(THealthyPlanClassification record) throws SQLException;

    Long insertSelective(THealthyPlanClassification record) throws SQLException;

    List selectByExample(THealthyPlanClassificationExample example) throws SQLException;

    THealthyPlanClassification selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(THealthyPlanClassification record, THealthyPlanClassificationExample example) throws SQLException;

    int updateByExample(THealthyPlanClassification record, THealthyPlanClassificationExample example) throws SQLException;

    int updateByPrimaryKeySelective(THealthyPlanClassification record) throws SQLException;

    int updateByPrimaryKey(THealthyPlanClassification record) throws SQLException;


}
