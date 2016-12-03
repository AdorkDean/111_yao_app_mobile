package com.rc.openapi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.app.framework.webapp.model.page.PageManager;
import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.dao.TChannelDAO;
import com.rc.openapi.vo.TChannel;
import com.rc.openapi.vo.TChannelExample;

public class TChannelDAOImpl implements TChannelDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
    public TChannelDAOImpl() {
        super();
    }

    public TChannelDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    public int countByExample(TChannelExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("t_channel.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(TChannelExample example) throws SQLException {
        int rows = sqlMapClient.delete("t_channel.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        TChannel key = new TChannel();
        key.setId(id);
        int rows = sqlMapClient.delete("t_channel.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(TChannel record) throws SQLException {
		return (Long)        sqlMapClient.insert("t_channel.ibatorgenerated_insert", record);
    }

    public Long insertSelective(TChannel record) throws SQLException {
		return (Long)        sqlMapClient.insert("t_channel.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(TChannelExample example) throws SQLException {
        List list = sqlMapClient.queryForList("t_channel.ibatorgenerated_selectByExample", example);
        return list;
    }

    public TChannel selectByPrimaryKey(Long id) throws SQLException {
        TChannel key = new TChannel();
        key.setId(id);
        TChannel record = (TChannel) sqlMapClient.queryForObject("t_channel.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(TChannel record, TChannelExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("t_channel.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(TChannel record, TChannelExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("t_channel.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(TChannel record) throws SQLException {
        int rows = sqlMapClient.update("t_channel.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(TChannel record) throws SQLException {
        int rows = sqlMapClient.update("t_channel.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends TChannelExample {
        private Object record;

        public UpdateByExampleParms(Object record, TChannelExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
	public PageWraper selectByRepositoryByPage(TChannelExample example) throws SQLException {
		PageWraper pw=null;
		int count=this.countByExample(example);
		List list = sqlMapClient.queryForList("t_channel.selectByExampleByPage", example);
		System.out.println("��Դ��ҳ��ѯlist.size="+list.size());
		pw=PageManager.getPageWraper(example.getPageInfo(), list, count);
		return pw;
	}
}
