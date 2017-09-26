package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Ad;

public interface AdDao {
	/**
	 * 新增
	 * @param ad
	 * @return
	 */
	int insert(Ad ad);
	
	/**
	 * 根据查询条件分页查询
	 * @param ad
	 * @return
	 */
	List<Ad> selectByPage(Ad ad);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Ad selectById(Long id);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int delete(Long id);
	
	/**
	 * 修改更新
	 * @param ad
	 * @return
	 */
	int update(Ad ad);
	
	
	
}
