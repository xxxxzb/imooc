package org.imooc.service.content;

import java.util.List;

import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;


public interface BusinessService {
	
	boolean insert(BusinessDto dto);
	
	BusinessDto seachById(Long id);

	List<BusinessDto> seachByPage(BusinessDto dto);
	
	BusinessListDto seachByPageForApi(BusinessDto dto);
	
	boolean delete(Long id);
	
	boolean modify(BusinessDto dto);
}
