package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rc.openapi.dao.TCategoryDAO;
import com.rc.openapi.service.TCategoryManager;
import com.rc.openapi.vo.TCategory;
import com.rc.openapi.vo.TCategoryExample;

public class TCategoryManagerImpl  implements TCategoryManager {

    private TCategoryDAO tcategorydao;

    public TCategoryManagerImpl() {
        super();
    }
    public void  setTcategorydao(TCategoryDAO tcategorydao){
        this.tcategorydao=tcategorydao;
    }
    public TCategoryDAO getTcategorydao(){
        return this.tcategorydao;
    }
    public     int countByExample(TCategoryExample example) throws SQLException{
        return tcategorydao. countByExample( example);
    }

    public     int deleteByExample(TCategoryExample example) throws SQLException{
        return tcategorydao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tcategorydao. deleteByPrimaryKey( id);
    }

    public     Long insert(TCategory record) throws SQLException{
        return tcategorydao. insert( record);
    }

    public     Long insertSelective(TCategory record) throws SQLException{
        return tcategorydao. insertSelective( record);
    }

    public     List selectByExample(TCategoryExample example) throws SQLException{
        return tcategorydao. selectByExample( example);
    }

    public     TCategory selectByPrimaryKey(Long id) throws SQLException{
        return tcategorydao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TCategory record, TCategoryExample example) throws SQLException{
        return tcategorydao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TCategory record, TCategoryExample example) throws SQLException{
        return tcategorydao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TCategory record) throws SQLException{
        return tcategorydao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TCategory record) throws SQLException{
        return tcategorydao. updateByPrimaryKey( record);
    }
    
    
    /**
     * @throws SQLException 
     * 
     */
	@Override
	public List<Map<String,Object>> selectSecoundCategory(Map<String, Object> param) throws SQLException {
		return tcategorydao.selectSecoundCategory(param);
	}
	@Override
	public List<TCategory> selectByExampleNoCache(TCategoryExample tCategoryExample) throws SQLException {
		return tcategorydao. selectByExampleNoCache(tCategoryExample);
	}


}
