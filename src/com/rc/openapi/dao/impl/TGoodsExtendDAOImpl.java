package com.rc.openapi.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.rc.app.framework.webapp.model.page.PageManager;
import com.rc.app.framework.webapp.model.page.PageWraper;
import com.rc.openapi.dao.TGoodsExtendDAO;
import com.rc.openapi.vo.TGoodsExtend;
import com.rc.openapi.vo.TGoodsExtendExample;
import com.rc.openapi.vo.TGoodsExtendWithBLOBs;

public class TGoodsExtendDAOImpl implements TGoodsExtendDAO {
    private SqlMapClient sqlMapClient;

    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
    public TGoodsExtendDAOImpl() {
        super();
    }

    public TGoodsExtendDAOImpl(SqlMapClient sqlMapClient) {
        super();
        this.sqlMapClient = sqlMapClient;
    }

    public int countByExample(TGoodsExtendExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("t_goods_extend.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    public int deleteByExample(TGoodsExtendExample example) throws SQLException {
        int rows = sqlMapClient.delete("t_goods_extend.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    public int deleteByPrimaryKey(Long goodsid) throws SQLException {
        TGoodsExtend key = new TGoodsExtend();
        key.setGoodsid(goodsid);
        int rows = sqlMapClient.delete("t_goods_extend.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(TGoodsExtendWithBLOBs record) throws SQLException {
		return (Long)        sqlMapClient.insert("t_goods_extend.ibatorgenerated_insert", record);
    }

    public Long insertSelective(TGoodsExtendWithBLOBs record) throws SQLException {
		return (Long)        sqlMapClient.insert("t_goods_extend.ibatorgenerated_insertSelective", record);
    }

    public List selectByExampleWithBLOBs(TGoodsExtendExample example) throws SQLException {
        List list = sqlMapClient.queryForList("t_goods_extend.ibatorgenerated_selectByExampleWithBLOBs", example);
        return list;
    }

    public List selectByExampleWithoutBLOBs(TGoodsExtendExample example) throws SQLException {
        List list = sqlMapClient.queryForList("t_goods_extend.ibatorgenerated_selectByExample", example);
        return list;
    }

    public TGoodsExtendWithBLOBs selectByPrimaryKey(Long goodsid) throws SQLException {
        TGoodsExtend key = new TGoodsExtend();
        key.setGoodsid(goodsid);
        TGoodsExtendWithBLOBs record = (TGoodsExtendWithBLOBs) sqlMapClient.queryForObject("t_goods_extend.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    public int updateByExampleSelective(TGoodsExtendWithBLOBs record, TGoodsExtendExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("t_goods_extend.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    public int updateByExample(TGoodsExtendWithBLOBs record, TGoodsExtendExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("t_goods_extend.ibatorgenerated_updateByExampleWithBLOBs", parms);
        return rows;
    }

    public int updateByExample(TGoodsExtend record, TGoodsExtendExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("t_goods_extend.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    public int updateByPrimaryKeySelective(TGoodsExtendWithBLOBs record) throws SQLException {
        int rows = sqlMapClient.update("t_goods_extend.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(TGoodsExtendWithBLOBs record) throws SQLException {
        int rows = sqlMapClient.update("t_goods_extend.ibatorgenerated_updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    public int updateByPrimaryKey(TGoodsExtend record) throws SQLException {
        int rows = sqlMapClient.update("t_goods_extend.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    private static class UpdateByExampleParms extends TGoodsExtendExample {
        private Object record;

        public UpdateByExampleParms(Object record, TGoodsExtendExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
	public PageWraper selectByRepositoryByPage(TGoodsExtendExample example) throws SQLException {
		PageWraper pw=null;
		int count=this.countByExample(example);
		List list = sqlMapClient.queryForList("t_goods_extend.selectByExampleByPage", example);
		System.out.println("��Դ��ҳ��ѯlist.size="+list.size());
		pw=PageManager.getPageWraper(example.getPageInfo(), list, count);
		return pw;
	}
}
