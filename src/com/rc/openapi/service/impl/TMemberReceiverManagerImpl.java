package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TMemberReceiverDAO;
import com.rc.openapi.service.TMemberReceiverManager;
import com.rc.openapi.vo.TMemberReceiver;
import com.rc.openapi.vo.TMemberReceiverExample;
import com.rc.openapi.vo.TMemberReceiverExample.Criteria;

public class TMemberReceiverManagerImpl  implements TMemberReceiverManager {

    private TMemberReceiverDAO tmemberreceiverdao;

    public TMemberReceiverManagerImpl() {
        super();
    }
    public void  setTmemberreceiverdao(TMemberReceiverDAO tmemberreceiverdao){
        this.tmemberreceiverdao=tmemberreceiverdao;
    }
    public TMemberReceiverDAO getTmemberreceiverdao(){
        return this.tmemberreceiverdao;
    }
    public     int countByExample(TMemberReceiverExample example) throws SQLException{
        return tmemberreceiverdao. countByExample( example);
    }

    public     int deleteByExample(TMemberReceiverExample example) throws SQLException{
        return tmemberreceiverdao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tmemberreceiverdao. deleteByPrimaryKey( id);
    }

    public     Long insert(TMemberReceiver record) throws SQLException{
        return tmemberreceiverdao. insert( record);
    }

    public     Long insertSelective(TMemberReceiver record) throws SQLException{
        return tmemberreceiverdao. insertSelective( record);
    }

    public     List selectByExample(TMemberReceiverExample example) throws SQLException{
        return tmemberreceiverdao. selectByExample( example);
    }

    public     TMemberReceiver selectByPrimaryKey(Long id) throws SQLException{
        return tmemberreceiverdao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TMemberReceiver record, TMemberReceiverExample example) throws SQLException{
        return tmemberreceiverdao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TMemberReceiver record, TMemberReceiverExample example) throws SQLException{
        return tmemberreceiverdao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TMemberReceiver record) throws SQLException{
        return tmemberreceiverdao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TMemberReceiver record) throws SQLException{
        return tmemberreceiverdao. updateByPrimaryKey( record);
    }
	
    
	public int updateDefaultArea(Long memberId, Long areaId)
			throws SQLException {
		TMemberReceiver  dr = new  TMemberReceiver();
		dr.setIsDefault(0);
		TMemberReceiverExample dre = new TMemberReceiverExample();
		dre.createCriteria().andMemberIdEqualTo(memberId);
		tmemberreceiverdao.updateByExampleSelective(dr, dre);
		dre.createCriteria().andMemberIdEqualTo(memberId).andIdEqualTo(areaId);
		dr.setIsDefault(1);
		return tmemberreceiverdao. updateByExampleSelective( dr, dre);
	}

	public int userReceiverUpOrAdd(TMemberReceiver record,Long userId) throws SQLException {
		TMemberReceiverExample dre = new TMemberReceiverExample();
		Criteria criteria = dre.createCriteria();
		criteria.andMemberIdEqualTo(userId);
        if(null != record.getId() && !("").equals(record.getId())){  //修改地址
        	if(record.getIsDefault()==1){
        		TMemberReceiver  dr = new  TMemberReceiver();
    			dr.setIsDefault(0);
    			tmemberreceiverdao.updateByExampleSelective(dr, dre);
    			tmemberreceiverdao.updateByPrimaryKeySelective(record);
        	}else{
        		tmemberreceiverdao.updateByPrimaryKeySelective(record);
        	}
        }else{                     //添加地址
    		List list = tmemberreceiverdao.selectByExample(dre);
    		if(null != list && list.size()>0){ //用户已有收货地址
    			if(record.getIsDefault()==1){
    				TMemberReceiver  dr = new  TMemberReceiver();
        			dr.setIsDefault(0);
        			tmemberreceiverdao.updateByExampleSelective(dr, dre);
        			tmemberreceiverdao.insertSelective(record);
    			}else{
    				tmemberreceiverdao.insertSelective(record);
    			}
    		}else{    //当用户首次添加地址的时候该地址为默认地址
    			record.setIsDefault(1);
    			tmemberreceiverdao.insertSelective(record);
    		}
    		
        }
		return 0;
	}

}
