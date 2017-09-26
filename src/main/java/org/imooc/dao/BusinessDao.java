package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Ad;
import org.imooc.bean.Business;

public interface BusinessDao {
	
	/**
	 * 新增
	 * @param ad
	 * @return
	 */
	int insert(Business business);
	
	/**
	 * 根据查询条件分页查询
	 * @param Business
	 * @return
	 */
	List<Business> selectByPage(Business business);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	Business selectById(Long id);
	
	/**
     *  根据查询条件分页查询商户列表 : 
     *  标题、副标题、描述三个过滤条件为模糊查询
     *  并且这三个过滤条件之间为或者的关系，用 OR 连接
     *  这三个过滤条件与其他过滤条件依然是并且关系，用 AND 连接
     * @param business 查询条件
     * @return 商户列表
     */
	List<Business> selectLikeByPage(Business business);
	
	
	
	int delete(Long id);
	
}
