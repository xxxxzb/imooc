package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.imooc.bean.Orders;
import org.imooc.constant.CommentStateConst;
import org.imooc.dao.OrdersDao;
import org.imooc.dto.OrdersDto;
import org.imooc.service.content.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl implements OrdersService{
	
	@Autowired
	private OrdersDao ordersDao;
	
	@Value("${businessImage.url}")
	private String businessImageurl;
	
	@Override
	public boolean add(OrdersDto dto) {
		Orders orders = new Orders();
		BeanUtils.copyProperties(dto, orders);
		orders.setCommentState(CommentStateConst.NOT_COMMENT);
		orders.setCreateTime(new Date());
		ordersDao.insert(orders);
		return true;
	}

	@Override
	public List<OrdersDto> getListByMemberId(Long memberId) {
		List<OrdersDto> result= new ArrayList<OrdersDto>(); 
		Orders ordersForSelect = new Orders();
		ordersForSelect.setMemberId(memberId);
		List<Orders>list = ordersDao.select(ordersForSelect);
		for (Orders orders : list) {
			OrdersDto ordersDto =new OrdersDto();
			BeanUtils.copyProperties(orders, ordersDto);
			result.add(ordersDto);
			ordersDto.setImg(businessImageurl + orders.getBusiness().getImgFileName());
			ordersDto.setTitle(orders.getBusiness().getTitle());
			ordersDto.setCount(orders.getBusiness().getNumber());
		}
		return result;
	}

	@Override
	public OrdersDto getbyId(Long id) {
		OrdersDto dto = new OrdersDto();
		Orders orders = ordersDao.selectById(id);
		BeanUtils.copyProperties(orders, dto);
		return dto;
	}

}
