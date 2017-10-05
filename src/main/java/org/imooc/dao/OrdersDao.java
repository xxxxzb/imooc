package org.imooc.dao;

import org.imooc.bean.Orders;

public interface OrdersDao {
	
	/**
	 * 新增
	 * @param order
	 * @return 影响的行数
	 */
	int inster(Orders order);
}
