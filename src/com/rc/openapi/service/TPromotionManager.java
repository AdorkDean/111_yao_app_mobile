package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TPromotion;
import com.rc.openapi.vo.TPromotionExample;

public interface TPromotionManager {
    int countByExample(TPromotionExample example) throws SQLException;

    int deleteByExample(TPromotionExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TPromotion record) throws SQLException;

    Long insertSelective(TPromotion record) throws SQLException;

    List selectByExample(TPromotionExample example) throws SQLException;

    TPromotion selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TPromotion record, TPromotionExample example) throws SQLException;

    int updateByExample(TPromotion record, TPromotionExample example) throws SQLException;

    int updateByPrimaryKeySelective(TPromotion record) throws SQLException;

    int updateByPrimaryKey(TPromotion record) throws SQLException;



}
