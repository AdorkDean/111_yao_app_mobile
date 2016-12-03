package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TGoodsComment;
import com.rc.openapi.vo.TGoodsCommentExample;

public interface TGoodsCommentManager {
    int countByExample(TGoodsCommentExample example) throws SQLException;

    int deleteByExample(TGoodsCommentExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TGoodsComment record) throws SQLException;

    Long insertSelective(TGoodsComment record) throws SQLException;

    List selectByExample(TGoodsCommentExample example) throws SQLException;

    TGoodsComment selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TGoodsComment record, TGoodsCommentExample example) throws SQLException;

    int updateByExample(TGoodsComment record, TGoodsCommentExample example) throws SQLException;

    int updateByPrimaryKeySelective(TGoodsComment record) throws SQLException;

    int updateByPrimaryKey(TGoodsComment record) throws SQLException;



}
