package org.imooc.service.content;

import java.util.List;

import org.imooc.dto.BusinessDto;


public interface BusinessService {
	
	boolean insert(BusinessDto dto);
	
	BusinessDto seachById(Long id);

	List<BusinessDto> seachByPage(BusinessDto dto);
	
	boolean delete(Long id);
}
