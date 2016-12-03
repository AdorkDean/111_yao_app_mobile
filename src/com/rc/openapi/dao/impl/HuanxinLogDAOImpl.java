package com.rc.openapi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.openapi.dao.HuanxinLogDAO;
import com.rc.openapi.vo.HuanxinLog;
import com.rc.openapi.vo.HuanxinLogExample;

public class HuanxinLogDAOImpl implements HuanxinLogDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
    public HuanxinLogDAOImpl() {
        super();
    }

    public HuanxinLogDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    public int countByExample(HuanxinLogExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("huanxin_log.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(HuanxinLogExample example) throws SQLException {
        int rows = sqlMapClient.delete("huanxin_log.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        HuanxinLog key = new HuanxinLog();
        key.setId(id);
        int rows = sqlMapClient.delete("huanxin_log.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(HuanxinLog record) throws SQLException {
		return (Long)        sqlMapClient.insert("huanxin_log.ibatorgenerated_insert", record);
    }

    public Long insertSelective(HuanxinLog record) throws SQLException {
		return (Long)        sqlMapClient.insert("huanxin_log.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(HuanxinLogExample example) throws SQLException {
        List list = sqlMapClient.queryForList("huanxin_log.ibatorgenerated_selectByExample", example);
        return list;
    }

    public HuanxinLog selectByPrimaryKey(Long id) throws SQLException {
        HuanxinLog key = new HuanxinLog();
        key.setId(id);
        HuanxinLog record = (HuanxinLog) sqlMapClient.queryForObject("huanxin_log.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(HuanxinLog record, HuanxinLogExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("huanxin_log.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(HuanxinLog record, HuanxinLogExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("huanxin_log.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(HuanxinLog record) throws SQLException {
        int rows = sqlMapClient.update("huanxin_log.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(HuanxinLog record) throws SQLException {
        int rows = sqlMapClient.update("huanxin_log.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends HuanxinLogExample {
        private Object record;

        public UpdateByExampleParms(Object record, HuanxinLogExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
	
}
