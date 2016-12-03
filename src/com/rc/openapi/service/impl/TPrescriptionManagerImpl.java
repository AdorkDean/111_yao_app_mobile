package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.dao.TPrescriptionDAO;
import com.rc.openapi.service.TPrescriptionManager;
import com.rc.openapi.vo.TPrescription;
import com.rc.openapi.vo.TPrescriptionExample;

public class TPrescriptionManagerImpl implements TPrescriptionManager {

	private TPrescriptionDAO tprescriptiondao;

	public TPrescriptionManagerImpl() {
		super();
	}

	public void setTprescriptiondao(TPrescriptionDAO tprescriptiondao) {
		this.tprescriptiondao = tprescriptiondao;
	}

	public TPrescriptionDAO getTprescriptiondao() {
		return this.tprescriptiondao;
	}

	public int countByExample(TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.countByExample(example);
	}

	public int deleteByExample(TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.deleteByExample(example);
	}

	public int deleteByPrimaryKey(Long id) throws SQLException {
		return tprescriptiondao.deleteByPrimaryKey(id);
	}

	public Long insert(TPrescription record) throws SQLException {
		return tprescriptiondao.insert(record);
	}

	public Long insertSelective(TPrescription record) throws SQLException {
		return tprescriptiondao.insertSelective(record);
	}

	public List selectByExampleWithBLOBs(TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.selectByExampleWithBLOBs(example);
	}

	public List selectByExampleWithoutBLOBs(TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.selectByExampleWithoutBLOBs(example);
	}

	public TPrescription selectByPrimaryKey(Long id) throws SQLException {
		return tprescriptiondao.selectByPrimaryKey(id);
	}

	public int updateByExampleSelective(TPrescription record, TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.updateByExampleSelective(record, example);
	}

	public int updateByExampleWithBLOBs(TPrescription record, TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.updateByExampleWithBLOBs(record, example);
	}

	public int updateByExampleWithoutBLOBs(TPrescription record, TPrescriptionExample example) throws SQLException {
		return tprescriptiondao.updateByExampleWithoutBLOBs(record, example);
	}

	public int updateByPrimaryKeySelective(TPrescription record) throws SQLException {
		return tprescriptiondao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKeyWithBLOBs(TPrescription record) throws SQLException {
		return tprescriptiondao.updateByPrimaryKeyWithBLOBs(record);
	}

	public int updateByPrimaryKeyWithoutBLOBs(TPrescription record) throws SQLException {
		return tprescriptiondao.updateByPrimaryKeyWithoutBLOBs(record);
	}

	@Override
	public void deleteById(Long id) {
		try {
			tprescriptiondao.deleteByPrimaryKey(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TPrescription> queryList(Long userId, int pageNo, int pageSize) {
		List<TPrescription> list = null;
		try {
			TPrescriptionExample tPrescriptionExample = new TPrescriptionExample();
			tPrescriptionExample.createCriteria().andUserIdEqualTo(userId);
			tPrescriptionExample.setOrderByClause("id desc");
			list = tprescriptiondao.selectByExamplePages(tPrescriptionExample, pageNo, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 保存
	 */
	@Override
	public Long savePrescription(TPrescription tPrescription) {
		Long id = 0L;
		try {
			id = tprescriptiondao.insertSelective(tPrescription);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 更新病例
	 */
	@Override
	public void updatePrescription(TPrescription tPrescription) {
		try {
			tprescriptiondao.updateByPrimaryKeySelective(tPrescription);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
