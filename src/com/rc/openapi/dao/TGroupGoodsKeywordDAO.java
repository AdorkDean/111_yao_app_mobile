package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TGroupGoodsKeyword;
import com.rc.openapi.vo.TGroupGoodsKeywordExample;

public interface TGroupGoodsKeywordDAO {
    int countByExample(TGroupGoodsKeywordExample example) throws SQLException;

    int deleteByExample(TGroupGoodsKeywordExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TGroupGoodsKeyword record) throws SQLException;

    Long insertSelective(TGroupGoodsKeyword record) throws SQLException;

    List selectByExample(TGroupGoodsKeywordExample example) throws SQLException;

    TGroupGoodsKeyword selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TGroupGoodsKeyword record, TGroupGoodsKeywordExample example) throws SQLException;

    int updateByExample(TGroupGoodsKeyword record, TGroupGoodsKeywordExample example) throws SQLException;

    int updateByPrimaryKeySelective(TGroupGoodsKeyword record) throws SQLException;

    int updateByPrimaryKey(TGroupGoodsKeyword record) throws SQLException;


}
