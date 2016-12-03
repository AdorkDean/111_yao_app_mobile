package com.rc.openapi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.openapi.dao.HuanxinUserDAO;
import com.rc.openapi.vo.HuanxinUser;
import com.rc.openapi.vo.HuanxinUserExample;

public class HuanxinUserDAOImpl implements HuanxinUserDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
    public HuanxinUserDAOImpl() {
        super();
    }

    public HuanxinUserDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    public int countByExample(HuanxinUserExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("huanxin_user.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(HuanxinUserExample example) throws SQLException {
        int rows = sqlMapClient.delete("huanxin_user.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        HuanxinUser key = new HuanxinUser();
        key.setId(id);
        int rows = sqlMapClient.delete("huanxin_user.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(HuanxinUser record) throws SQLException {
		return (Long)        sqlMapClient.insert("huanxin_user.ibatorgenerated_insert", record);
    }

    public Long insertSelective(HuanxinUser record) throws SQLException {
		return (Long)        sqlMapClient.insert("huanxin_user.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(HuanxinUserExample example) throws SQLException {
        List list = sqlMapClient.queryForList("huanxin_user.ibatorgenerated_selectByExample", example);
        return list;
    }

    public HuanxinUser selectByPrimaryKey(Long id) throws SQLException {
        HuanxinUser key = new HuanxinUser();
        key.setId(id);
        HuanxinUser record = (HuanxinUser) sqlMapClient.queryForObject("huanxin_user.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(HuanxinUser record, HuanxinUserExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("huanxin_user.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(HuanxinUser record, HuanxinUserExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("huanxin_user.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(HuanxinUser record) throws SQLException {
        int rows = sqlMapClient.update("huanxin_user.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(HuanxinUser record) throws SQLException {
        int rows = sqlMapClient.update("huanxin_user.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends HuanxinUserExample {
        private Object record;

        public UpdateByExampleParms(Object record, HuanxinUserExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
	
}
