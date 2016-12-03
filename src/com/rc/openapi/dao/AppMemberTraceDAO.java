package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.AppMemberTrace;
import com.rc.openapi.vo.AppMemberTraceExample;

public interface AppMemberTraceDAO {
    int countByExample(AppMemberTraceExample example) throws SQLException;

    int deleteByExample(AppMemberTraceExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(AppMemberTrace record) throws SQLException;

    Long insertSelective(AppMemberTrace record) throws SQLException;

    List selectByExample(AppMemberTraceExample example) throws SQLException;

    AppMemberTrace selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(AppMemberTrace record, AppMemberTraceExample example) throws SQLException;

    int updateByExample(AppMemberTrace record, AppMemberTraceExample example) throws SQLException;

    int updateByPrimaryKeySelective(AppMemberTrace record) throws SQLException;

    int updateByPrimaryKey(AppMemberTrace record) throws SQLException;


}
