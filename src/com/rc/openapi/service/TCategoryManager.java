package com.rc.openapi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.rc.openapi.vo.TCategory;
import com.rc.openapi.vo.TCategoryExample;

public interface TCategoryManager {
    int countByExample(TCategoryExample example) throws SQLException;

    int deleteByExample(TCategoryExample example) throws SQLException;

    int deleteByPrimaryKey(Long id) throws SQLException;

    Long insert(TCategory record) throws SQLException;

    Long insertSelective(TCategory record) throws SQLException;

    List selectByExample(TCategoryExample example) throws SQLException;

    TCategory selectByPrimaryKey(Long id) throws SQLException;

    int updateByExampleSelective(TCategory record, TCategoryExample example) throws SQLException;

    int updateByExample(TCategory record, TCategoryExample example) throws SQLException;

    int updateByPrimaryKeySelective(TCategory record) throws SQLException;

    int updateByPrimaryKey(TCategory record) throws SQLException;

    /**
     * 全部分类中,二级分类,带图片
     * @param param
     * @return
     * @throws SQLException 
     */
	List<Map<String, Object>> selectSecoundCategory(Map<String, Object> param) throws SQLException;

	/**
	 * 不带缓存
	 * @param tCategoryExample
	 * @return
	 * @throws SQLException 
	 */
	List<TCategory> selectByExampleNoCache(TCategoryExample tCategoryExample) throws SQLException;



}
