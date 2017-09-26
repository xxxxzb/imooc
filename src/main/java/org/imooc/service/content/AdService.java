package org.imooc.service.content;

import java.util.List;

import org.imooc.bean.Ad;
import org.imooc.dto.AdDto;

public interface AdService {
	
	
	/**
	 * 新增广告
	 * @param adDto
	 * @return :true-成功；false-失败
	 */
	boolean add(AdDto adDto);
	
	List<AdDto> searchByPage(AdDto adDto);
	
	void delete(Long id);
	
	AdDto searchById(Long id);
	
	boolean modify(AdDto adDto);
}
