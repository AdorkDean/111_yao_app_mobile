package com.rc.openapi.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rc.openapi.dao.TPrescriptionImageDAO;
import com.rc.openapi.service.TPrescriptionImageManager;
import com.rc.openapi.vo.TPrescriptionImage;
import com.rc.openapi.vo.TPrescriptionImageExample;

public class TPrescriptionImageManagerImpl implements TPrescriptionImageManager {

	private TPrescriptionImageDAO tprescriptionimagedao;

	public TPrescriptionImageManagerImpl() {
		super();
	}

	public void setTprescriptionimagedao(TPrescriptionImageDAO tprescriptionimagedao) {
		this.tprescriptionimagedao = tprescriptionimagedao;
	}

	public TPrescriptionImageDAO getTprescriptionimagedao() {
		return this.tprescriptionimagedao;
	}

	public int countByExample(TPrescriptionImageExample example) throws SQLException {
		return tprescriptionimagedao.countByExample(example);
	}

	public int deleteByExample(TPrescriptionImageExample example) throws SQLException {
		return tprescriptionimagedao.deleteByExample(example);
	}

	public int deleteByPrimaryKey(Long id) throws SQLException {
		return tprescriptionimagedao.deleteByPrimaryKey(id);
	}

	public Long insert(TPrescriptionImage record) throws SQLException {
		return tprescriptionimagedao.insert(record);
	}

	public Long insertSelective(TPrescriptionImage record) throws SQLException {
		return tprescriptionimagedao.insertSelective(record);
	}

	public List selectByExample(TPrescriptionImageExample example) throws SQLException {
		return tprescriptionimagedao.selectByExample(example);
	}

	public TPrescriptionImage selectByPrimaryKey(Long id) throws SQLException {
		return tprescriptionimagedao.selectByPrimaryKey(id);
	}

	public int updateByExampleSelective(TPrescriptionImage record, TPrescriptionImageExample example)
			throws SQLException {
		return tprescriptionimagedao.updateByExampleSelective(record, example);
	}

	public int updateByExample(TPrescriptionImage record, TPrescriptionImageExample example) throws SQLException {
		return tprescriptionimagedao.updateByExample(record, example);
	}

	public int updateByPrimaryKeySelective(TPrescriptionImage record) throws SQLException {
		return tprescriptionimagedao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(TPrescriptionImage record) throws SQLException {
		return tprescriptionimagedao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteByPrescriptionId(Long id) {
		List<TPrescriptionImage> list = selectImagesByPrescriptionId(id);
		List<Long> longList = new ArrayList<Long>();
		for (TPrescriptionImage tPrescriptionImage : list) {
			longList.add(tPrescriptionImage.getId());
		}
		if (longList != null && longList.size() > 0) {
			TPrescriptionImageExample tPrescriptionImageExample = new TPrescriptionImageExample();
			tPrescriptionImageExample.createCriteria().andIdIn(longList);
			try {
				tprescriptionimagedao.deleteByExample(tPrescriptionImageExample);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	public List<TPrescriptionImage> selectImagesByPrescriptionId(Long valueOf) {
		TPrescriptionImageExample example = new TPrescriptionImageExample();
		example.createCriteria().andPrescriptionIdEqualTo(valueOf);
		List<TPrescriptionImage> list = new ArrayList<TPrescriptionImage>();
		try {
			list = tprescriptionimagedao.selectByExample(example);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 保存
	 */
	@Override
	public void savePrescriptionImage(TPrescriptionImage tPrescriptionImage) {
		try {
			tprescriptionimagedao.insertSelective(tPrescriptionImage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
