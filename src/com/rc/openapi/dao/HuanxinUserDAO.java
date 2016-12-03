package com.rc.openapi.dao;


import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.HuanxinUser;
import com.rc.openapi.vo.HuanxinUserExample;

public interface HuanxinUserDAO {
    int countByExample(HuanxinUserExample example) throws SQLException;

    int deleteByExample(HuanxinUserExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(HuanxinUser record) throws SQLException;

    Long insertSelective(HuanxinUser record) throws SQLException;

    List selectByExample(HuanxinUserExample example) throws SQLException;

    HuanxinUser selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(HuanxinUser record, HuanxinUserExample example) throws SQLException;

    int updateByExample(HuanxinUser record, HuanxinUserExample example) throws SQLException;

    int updateByPrimaryKeySelective(HuanxinUser record) throws SQLException;

    int updateByPrimaryKey(HuanxinUser record) throws SQLException;


}
