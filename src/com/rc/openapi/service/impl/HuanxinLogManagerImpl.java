package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.HuanxinLogDAO;
import com.rc.openapi.service.HuanxinLogManager;
import com.rc.openapi.vo.HuanxinLog;
import com.rc.openapi.vo.HuanxinLogExample;

public class HuanxinLogManagerImpl  implements HuanxinLogManager {

  

	  private HuanxinLogDAO huanxinlogdao;

	    public HuanxinLogManagerImpl() {
	        super();
	    }
	    public void  setHuanxinlogdao(HuanxinLogDAO huanxinlogdao){
	        this.huanxinlogdao=huanxinlogdao;
	    }
	    public HuanxinLogDAO getHuanxinlogdao(){
	        return this.huanxinlogdao;
	    }
	    public     int countByExample(HuanxinLogExample example) throws SQLException{
	        return huanxinlogdao.countByExample( example);
	    }

	    public     int deleteByExample(HuanxinLogExample example) throws SQLException{
	        return huanxinlogdao. deleteByExample( example);
	    }

	    public     int deleteByPrimaryKey(Long id) throws SQLException{
	        return huanxinlogdao. deleteByPrimaryKey( id);
	    }

	    public     Long insert(HuanxinLog record) throws SQLException{
	        return huanxinlogdao. insert( record);
	    }

	    public     Long insertSelective(HuanxinLog record) throws SQLException{
	        return huanxinlogdao. insertSelective( record);
	    }

	    public     List selectByExample(HuanxinLogExample example) throws SQLException{
	        return huanxinlogdao. selectByExample( example);
	    }

	    public     HuanxinLog selectByPrimaryKey(Long id) throws SQLException{
	        return huanxinlogdao. selectByPrimaryKey( id);
	    }

	    public     int updateByExampleSelective(HuanxinLog record, HuanxinLogExample example) throws SQLException{
	        return huanxinlogdao. updateByExampleSelective( record, example);
	    }

	    public     int updateByExample(HuanxinLog record, HuanxinLogExample example) throws SQLException{
	        return huanxinlogdao. updateByExample( record, example);
	    }

	    public     int updateByPrimaryKeySelective(HuanxinLog record) throws SQLException{
	        return huanxinlogdao. updateByPrimaryKeySelective( record);
	    }

	    public     int updateByPrimaryKey(HuanxinLog record) throws SQLException{
	        return huanxinlogdao. updateByPrimaryKey( record);
	    }

	    
}
