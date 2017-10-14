package org.imooc.service.content;

import java.util.List;
import org.imooc.dto.OrdersDto;

public interface OrdersService {
	boolean add(OrdersDto dto);
	List<OrdersDto> getListByMemberId(Long memberId);
	OrdersDto getbyId(Long id);
}
