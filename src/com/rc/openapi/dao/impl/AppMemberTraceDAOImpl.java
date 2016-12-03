package com.rc.openapi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.app.framework.webapp.model.page.PageManager;
import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.dao.AppMemberTraceDAO;
import com.rc.openapi.vo.AppMemberTrace;
import com.rc.openapi.vo.AppMemberTraceExample;

public class AppMemberTraceDAOImpl implements AppMemberTraceDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
    public AppMemberTraceDAOImpl() {
        super();
    }

    public AppMemberTraceDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    public int countByExample(AppMemberTraceExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("app_member_trace.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppMemberTraceExample example) throws SQLException {
        int rows = sqlMapClient.delete("app_member_trace.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        AppMemberTrace key = new AppMemberTrace();
        key.setId(id);
        int rows = sqlMapClient.delete("app_member_trace.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(AppMemberTrace record) throws SQLException {
		return (Long)        sqlMapClient.insert("app_member_trace.ibatorgenerated_insert", record);
    }

    public Long insertSelective(AppMemberTrace record) throws SQLException {
		return (Long)        sqlMapClient.insert("app_member_trace.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppMemberTraceExample example) throws SQLException {
        List list = sqlMapClient.queryForList("app_member_trace.ibatorgenerated_selectByExample", example);
        return list;
    }

    public AppMemberTrace selectByPrimaryKey(Long id) throws SQLException {
        AppMemberTrace key = new AppMemberTrace();
        key.setId(id);
        AppMemberTrace record = (AppMemberTrace) sqlMapClient.queryForObject("app_member_trace.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppMemberTrace record, AppMemberTraceExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("app_member_trace.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppMemberTrace record, AppMemberTraceExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("app_member_trace.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppMemberTrace record) throws SQLException {
        int rows = sqlMapClient.update("app_member_trace.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppMemberTrace record) throws SQLException {
        int rows = sqlMapClient.update("app_member_trace.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppMemberTraceExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppMemberTraceExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
	public PageWraper selectByRepositoryByPage(AppMemberTraceExample example) throws SQLException {
		PageWraper pw=null;
		int count=this.countByExample(example);
		List list = sqlMapClient.queryForList("app_member_trace.selectByExampleByPage", example);
		System.out.println("��Դ��ҳ��ѯlist.size="+list.size());
		pw=PageManager.getPageWraper(example.getPageInfo(), list, count);
		return pw;
	}
}
