package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.imooc.bean.Business;
import org.imooc.dao.BusinessDao;
import org.imooc.dto.BusinessDto;
import org.imooc.service.content.BusinessService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService{

	@Autowired
	private BusinessDao businessDao;
	
	@Value("${adImage.savePath}")
	private String adImageSavePath;
	
	@Value("${adImage.url}")
	private String adImageurl;
	
	@Override
	public boolean insert(BusinessDto dto) {
		Business business = new Business();
		BeanUtils.copyProperties(dto, business);
		businessDao.insert(business);
		
		return true;
	}

	@Override
	public BusinessDto seachById(Long id) {
		BusinessDto result = new BusinessDto();
		Business business=businessDao.selectById(id);
		BeanUtils.copyProperties(business, result);
		result.setImg(adImageurl + business.getImgFileName());
		result.setStar(this.getStar(business));
		return result;
	}
	
	private int getStar(Business business){
		if(business.getStarTotalNum() !=null && business.getCommentTotalNum() !=null && business.getCommentTotalNum()!=0){
			int star = (int) (business.getStarTotalNum()/business.getCommentTotalNum());
			return star;
		}else{
			return 0;
		}
	}
	
	@Override
	public List<BusinessDto> seachByPage(BusinessDto dto) {
		Business b = new Business();
		BeanUtils.copyProperties(dto, b);
		
		List<Business> bList=businessDao.selectByPage(b);
		List<BusinessDto> result = new ArrayList<BusinessDto>();
		for (Business business : bList) {
			BusinessDto dtoTemp = new BusinessDto();
			BeanUtils.copyProperties(business, dtoTemp);
			result.add(dtoTemp);
		}
		return result;
	}

	@Override
	public boolean delete(Long id) {
		if(businessDao.delete(id)==1){
			return true;
			}
		return false;
	}

	
	
}
