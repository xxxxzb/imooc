package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Orders;

public interface OrdersDao {
	
	/**
	 * 新增
	 * @param order
	 * @return 影响的行数
	 */
	int insert(Orders order);
	
	List<Orders> select(Orders orders);
	
	Orders selectById(Long id);
	
	int update(Orders orders);
}
