package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rc.openapi.vo.TMember;
import com.rc.openapi.vo.TMemberExample;

public interface TMemberManager {
    int countByExample(TMemberExample example) throws SQLException;

    int deleteByExample(TMemberExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMember record) throws SQLException;

    Long insertSelective(TMember record) throws SQLException;

    List selectByExample(TMemberExample example) throws SQLException;

    TMember selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMember record, TMemberExample example) throws SQLException;

    int updateByExample(TMember record, TMemberExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMember record) throws SQLException;

    int updateByPrimaryKey(TMember record) throws SQLException;
    
    Long getMemberId(String session)throws SQLException;

    Long insertSelectiveAndSession(TMember member,Map map)throws SQLException;

    int updateMemberToSession(TMember member,TMemberExample example,Map map)throws SQLException;
    
    int updateMemberPassword(TMember member,TMemberExample example,Map map)throws SQLException;
    
    Long insertMemberBinding(TMember member,Map map,String uuid)throws SQLException;
    
    int updateWXMember(TMember member,TMemberExample example,Map map,String imgUrl)throws SQLException;
    
    int updateMember(TMember member,TMemberExample example,Map map)throws SQLException;
}
