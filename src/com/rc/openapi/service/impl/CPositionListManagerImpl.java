package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.CPositionListDAO;
import com.rc.openapi.service.CPositionListManager;
import com.rc.openapi.vo.CPositionList;
import com.rc.openapi.vo.CPositionListExample;

public class CPositionListManagerImpl  implements CPositionListManager {

    private CPositionListDAO cpositionlistdao;

    public CPositionListManagerImpl() {
        super();
    }
    public void  setCpositionlistdao(CPositionListDAO cpositionlistdao){
        this.cpositionlistdao=cpositionlistdao;
    }
    public CPositionListDAO getCpositionlistdao(){
        return this.cpositionlistdao;
    }
    public     int countByExample(CPositionListExample example) throws SQLException{
        return cpositionlistdao. countByExample( example);
    }

    public     int deleteByExample(CPositionListExample example) throws SQLException{
        return cpositionlistdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Integer id) throws SQLException{
        return cpositionlistdao. deleteByPrimaryKey( id);
    }

    public     Long insert(CPositionList record) throws SQLException{
        return cpositionlistdao. insert( record);
    }

    public     Long insertSelective(CPositionList record) throws SQLException{
        return cpositionlistdao. insertSelective( record);
    }

    public     List selectByExampleWithBLOBs(CPositionListExample example) throws SQLException{
        return cpositionlistdao. selectByExampleWithBLOBs( example);
    }

    public     List selectByExampleWithoutBLOBs(CPositionListExample example) throws SQLException{
        return cpositionlistdao. selectByExampleWithoutBLOBs( example);
    }

    public     CPositionList selectByPrimaryKey(Integer id) throws SQLException{
        return cpositionlistdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(CPositionList record, CPositionListExample example) throws SQLException{
        return cpositionlistdao. updateByExampleSelective( record, example);
    }

    public     int updateByExampleWithBLOBs(CPositionList record, CPositionListExample example) throws SQLException{
        return cpositionlistdao. updateByExampleWithBLOBs( record, example);
    }

    public     int updateByExampleWithoutBLOBs(CPositionList record, CPositionListExample example) throws SQLException{
        return cpositionlistdao. updateByExampleWithoutBLOBs( record, example);
    }

    public     int updateByPrimaryKeySelective(CPositionList record) throws SQLException{
        return cpositionlistdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKeyWithBLOBs(CPositionList record) throws SQLException{
        return cpositionlistdao. updateByPrimaryKeyWithBLOBs( record);
    }

    public     int updateByPrimaryKeyWithoutBLOBs(CPositionList record) throws SQLException{
        return cpositionlistdao. updateByPrimaryKeyWithoutBLOBs( record);
    }


}
