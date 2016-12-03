package com.rc.openapi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.app.framework.webapp.model.page.PageManager;
import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.dao.AppPaymethodAreaDAO;
import com.rc.openapi.vo.AppPaymethodArea;
import com.rc.openapi.vo.AppPaymethodAreaExample;

public class AppPaymethodAreaDAOImpl implements AppPaymethodAreaDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
    public AppPaymethodAreaDAOImpl() {
        super();
    }

    public AppPaymethodAreaDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    public int countByExample(AppPaymethodAreaExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("app_paymethod_area.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(AppPaymethodAreaExample example) throws SQLException {
        int rows = sqlMapClient.delete("app_paymethod_area.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long id) throws SQLException {
        AppPaymethodArea key = new AppPaymethodArea();
        key.setId(id);
        int rows = sqlMapClient.delete("app_paymethod_area.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(AppPaymethodArea record) throws SQLException {
		return (Long)        sqlMapClient.insert("app_paymethod_area.ibatorgenerated_insert", record);
    }

    public Long insertSelective(AppPaymethodArea record) throws SQLException {
		return (Long)        sqlMapClient.insert("app_paymethod_area.ibatorgenerated_insertSelective", record);
    }

    public List selectByExample(AppPaymethodAreaExample example) throws SQLException {
        List list = sqlMapClient.queryForList("app_paymethod_area.ibatorgenerated_selectByExample", example);
        return list;
    }

    public AppPaymethodArea selectByPrimaryKey(Long id) throws SQLException {
        AppPaymethodArea key = new AppPaymethodArea();
        key.setId(id);
        AppPaymethodArea record = (AppPaymethodArea) sqlMapClient.queryForObject("app_paymethod_area.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(AppPaymethodArea record, AppPaymethodAreaExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("app_paymethod_area.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(AppPaymethodArea record, AppPaymethodAreaExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("app_paymethod_area.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(AppPaymethodArea record) throws SQLException {
        int rows = sqlMapClient.update("app_paymethod_area.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(AppPaymethodArea record) throws SQLException {
        int rows = sqlMapClient.update("app_paymethod_area.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends AppPaymethodAreaExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppPaymethodAreaExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
	public PageWraper selectByRepositoryByPage(AppPaymethodAreaExample example) throws SQLException {
		PageWraper pw=null;
		int count=this.countByExample(example);
		List list = sqlMapClient.queryForList("app_paymethod_area.selectByExampleByPage", example);
		System.out.println("��Դ��ҳ��ѯlist.size="+list.size());
		pw=PageManager.getPageWraper(example.getPageInfo(), list, count);
		return pw;
	}
}
