package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;

import com.rc.openapi.vo.TPrescription;
import com.rc.openapi.vo.TPrescriptionExample;

public interface TPrescriptionManager {
	int countByExample(TPrescriptionExample example) throws SQLException;

	int deleteByExample(TPrescriptionExample example) throws SQLException;

	int deleteByPrimaryKey(Long id) throws SQLException;

	Long insert(TPrescription record) throws SQLException;

	Long insertSelective(TPrescription record) throws SQLException;

	List selectByExampleWithBLOBs(TPrescriptionExample example) throws SQLException;

	List selectByExampleWithoutBLOBs(TPrescriptionExample example) throws SQLException;

	TPrescription selectByPrimaryKey(Long id) throws SQLException;

	int updateByExampleSelective(TPrescription record, TPrescriptionExample example) throws SQLException;

	int updateByExampleWithBLOBs(TPrescription record, TPrescriptionExample example) throws SQLException;

	int updateByExampleWithoutBLOBs(TPrescription record, TPrescriptionExample example) throws SQLException;

	int updateByPrimaryKeySelective(TPrescription record) throws SQLException;

	int updateByPrimaryKeyWithBLOBs(TPrescription record) throws SQLException;

	int updateByPrimaryKeyWithoutBLOBs(TPrescription record) throws SQLException;

	void deleteById(Long id);

	List<TPrescription> queryList(Long userId, int pageNo, int pageSize);

	Long savePrescription(TPrescription tPrescription);

	void updatePrescription(TPrescription tPrescription);

}
