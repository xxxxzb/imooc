package org.imooc.service.impl;

import org.imooc.bean.Orders;
import org.imooc.constant.CommentStateConst;
import org.imooc.dao.OrdersDao;
import org.imooc.dto.OrdersDto;
import org.imooc.service.content.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService{
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Override
	public boolean add(OrdersDto dto) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(dto, orders);
		orders.setCommentState(CommentStateConst.NOT_COMMENT);
		ordersDao.inster(orders);
		return true;
	}

}
