package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TMemberReceiverLatLonDAO;
import com.rc.openapi.service.TMemberReceiverLatLonManager;
import com.rc.openapi.vo.TMemberReceiverLatLon;
import com.rc.openapi.vo.TMemberReceiverLatLonExample;

public class TMemberReceiverLatLonManagerImpl  implements TMemberReceiverLatLonManager {

    private TMemberReceiverLatLonDAO tmemberreceiverlatlondao;

    public TMemberReceiverLatLonManagerImpl() {
        super();
    }
    public void  setTmemberreceiverlatlondao(TMemberReceiverLatLonDAO tmemberreceiverlatlondao){
        this.tmemberreceiverlatlondao=tmemberreceiverlatlondao;
    }
    public TMemberReceiverLatLonDAO getTmemberreceiverlatlondao(){
        return this.tmemberreceiverlatlondao;
    }
    public     int countByExample(TMemberReceiverLatLonExample example) throws SQLException{
        return tmemberreceiverlatlondao. countByExample( example);
    }

    public     int deleteByExample(TMemberReceiverLatLonExample example) throws SQLException{
        return tmemberreceiverlatlondao. deleteByExample( example);
    }

    public     int deleteByPrimaryKey(Long id) throws SQLException{
        return tmemberreceiverlatlondao. deleteByPrimaryKey( id);
    }

    public     Long insert(TMemberReceiverLatLon record) throws SQLException{
        return tmemberreceiverlatlondao. insert( record);
    }

    public     Long insertSelective(TMemberReceiverLatLon record) throws SQLException{
        return tmemberreceiverlatlondao. insertSelective( record);
    }

    public     List selectByExample(TMemberReceiverLatLonExample example) throws SQLException{
        return tmemberreceiverlatlondao. selectByExample( example);
    }

    public     TMemberReceiverLatLon selectByPrimaryKey(Long id) throws SQLException{
        return tmemberreceiverlatlondao. selectByPrimaryKey( id);
    }

    public     int updateByExampleSelective(TMemberReceiverLatLon record, TMemberReceiverLatLonExample example) throws SQLException{
        return tmemberreceiverlatlondao. updateByExampleSelective( record, example);
    }

    public     int updateByExample(TMemberReceiverLatLon record, TMemberReceiverLatLonExample example) throws SQLException{
        return tmemberreceiverlatlondao. updateByExample( record, example);
    }

    public     int updateByPrimaryKeySelective(TMemberReceiverLatLon record) throws SQLException{
        return tmemberreceiverlatlondao. updateByPrimaryKeySelective( record);
    }

    public     int updateByPrimaryKey(TMemberReceiverLatLon record) throws SQLException{
        return tmemberreceiverlatlondao. updateByPrimaryKey( record);
    }
	public boolean receiverLatlonSetDefault(long receiverId,long memberId)
			throws SQLException {
		boolean flag = true;
		try{
			TMemberReceiverLatLonExample latLonExample = new TMemberReceiverLatLonExample();
			latLonExample.createCriteria().andMemberIdEqualTo(memberId);
			List<TMemberReceiverLatLon> latlonList =tmemberreceiverlatlondao.selectByExample(latLonExample);
			TMemberReceiverLatLon updateLatLon = null;
			for (int i = 0; i < latlonList.size(); i++) {
				 updateLatLon = new TMemberReceiverLatLon();
				 updateLatLon.setId(latlonList.get(i).getId());
				 if(latlonList.get(i).getId().longValue() == receiverId){
					 updateLatLon.setIsDefault(1);
				 }else {
					updateLatLon.setIsDefault(0);
				 }
				 tmemberreceiverlatlondao.updateByPrimaryKeySelective(updateLatLon);
			}
		}catch(Exception e){
			flag = false;
			throw new SQLException(e.getMessage());
		}
		return flag;
	}


}
