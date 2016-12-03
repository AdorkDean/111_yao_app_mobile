package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TGoodsExtend;
import com.rc.openapi.vo.TGoodsExtendExample;
import com.rc.openapi.vo.TGoodsExtendWithBLOBs;

public interface TGoodsExtendDAO {
    int countByExample(TGoodsExtendExample example) throws SQLException;

    int deleteByExample(TGoodsExtendExample example) throws SQLException;

    int deleteByPrimaryKey(Long goodsid) throws SQLException;

    Long insert(TGoodsExtendWithBLOBs record) throws SQLException;

    Long insertSelective(TGoodsExtendWithBLOBs record) throws SQLException;

    List selectByExampleWithBLOBs(TGoodsExtendExample example) throws SQLException;

    List selectByExampleWithoutBLOBs(TGoodsExtendExample example) throws SQLException;

    TGoodsExtendWithBLOBs selectByPrimaryKey(Long goodsid) throws SQLException;

    int updateByExampleSelective(TGoodsExtendWithBLOBs record, TGoodsExtendExample example) throws SQLException;

    int updateByExample(TGoodsExtendWithBLOBs record, TGoodsExtendExample example) throws SQLException;

    int updateByExample(TGoodsExtend record, TGoodsExtendExample example) throws SQLException;

    int updateByPrimaryKeySelective(TGoodsExtendWithBLOBs record) throws SQLException;

    int updateByPrimaryKey(TGoodsExtendWithBLOBs record) throws SQLException;

    int updateByPrimaryKey(TGoodsExtend record) throws SQLException;


}
