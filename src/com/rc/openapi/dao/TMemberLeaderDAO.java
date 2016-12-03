package com.rc.openapi.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rc.openapi.vo.TMemberLeader;
import com.rc.openapi.vo.TMemberLeaderExample;

public interface TMemberLeaderDAO {
    int countByExample(TMemberLeaderExample example) throws SQLException;

    int deleteByExample(TMemberLeaderExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TMemberLeader record) throws SQLException;

    Long insertSelective(TMemberLeader record) throws SQLException;

    List selectByExample(TMemberLeaderExample example) throws SQLException;

    TMemberLeader selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TMemberLeader record, TMemberLeaderExample example) throws SQLException;

    int updateByExample(TMemberLeader record, TMemberLeaderExample example) throws SQLException;

    int updateByPrimaryKeySelective(TMemberLeader record) throws SQLException;

    int updateByPrimaryKey(TMemberLeader record) throws SQLException;

	Map<String, Object> selectLeaderPhoneByXiufenId(Long memberId) throws SQLException;


}
