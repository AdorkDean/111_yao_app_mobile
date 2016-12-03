package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TGoodsConsult;
import com.rc.openapi.vo.TGoodsConsultExample;

public interface TGoodsConsultDAO {
    int countByExample(TGoodsConsultExample example) throws SQLException;

    int deleteByExample(TGoodsConsultExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TGoodsConsult record) throws SQLException;

    Long insertSelective(TGoodsConsult record) throws SQLException;

    List selectByExample(TGoodsConsultExample example) throws SQLException;

    TGoodsConsult selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TGoodsConsult record, TGoodsConsultExample example) throws SQLException;

    int updateByExample(TGoodsConsult record, TGoodsConsultExample example) throws SQLException;

    int updateByPrimaryKeySelective(TGoodsConsult record) throws SQLException;

    int updateByPrimaryKey(TGoodsConsult record) throws SQLException;


}
