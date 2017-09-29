package org.imooc.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.imooc.bean.Business;
import org.imooc.bean.Page;
import org.imooc.constant.CategoryConts;
import org.imooc.dao.BusinessDao;
import org.imooc.dto.BusinessDto;
import org.imooc.dto.BusinessListDto;
import org.imooc.service.content.BusinessService;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BusinessServiceImpl implements BusinessService{

	@Autowired
	private BusinessDao businessDao;
	
	@Value("${businessImage.savePath}")
	private String businessImageSavePath;
	
	@Value("${businessImage.url}")
	private String businessImageurl;
	
	@Override
	public boolean insert(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);
		if(businessDto.getImgFile()!=null && businessDto.getImgFile().getSize()>0){
			try {
				String fileName = FileUtil.save(businessDto.getImgFile(), businessImageSavePath);
				business.setImgFileName(fileName);
				//默已认售数量0
				business.setNumber(0);
				//默认评论总次数0
				business.setCommentTotalNum(0L);
				//默认评论星星0
				business.setStarTotalNum(0L);
				businessDao.insert(business);
				return true;
			} catch (IllegalStateException | IOException e) {
				//TODO 需要添加日志
				return false;
			}
		}else{
			return true;
		}
	}

	@Override
	public BusinessDto seachById(Long id) {
		BusinessDto result = new BusinessDto();
		Business business=businessDao.selectById(id);
		BeanUtils.copyProperties(business, result);
		result.setImg(businessImageurl + business.getImgFileName());
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

	@Override
	public boolean modify(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);
		String fileName = null;
		if(businessDto.getImgFile() !=null && businessDto.getImgFile().getSize()>0){
			try {
				fileName = FileUtil.save(businessDto.getImgFile(), businessImageSavePath);
				business.setImgFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				//TODO 需要添加日志
				return false;
			}
		}
		int updateCount=businessDao.update(business);
		if(updateCount!=1){
			return false;
		}
		if(fileName!=null){
			return FileUtil.delete(businessImageSavePath+businessDto.getImgFileName());
		}
		return true;
	}

	@Override
	public BusinessListDto seachByPageForApi(BusinessDto businessDto) {
		Business businessForSelect = new Business();
		BeanUtils.copyProperties(businessDto, businessForSelect);
		// 当关键字不为空时，把关键字的值分别设置到标题、副标题、描述中
		// TODO 改进做法：全文检索
		if(!StringUtils.isEmpty(businessDto.getKeyword())){
			businessForSelect.setTitle(businessDto.getKeyword());
			businessForSelect.setSubtitle(businessDto.getKeyword());
			businessForSelect.setDesc(businessDto.getKeyword());
		}
		//当类别为全部(all)时，需要将类别清空，不作为过滤条件
		if(businessDto.getCategory()!=null && CategoryConts.ALL.equals(businessDto.getCategory())){
			businessForSelect.setCategory(null);
		}
		//前端app页码从0开始计算，这里需要+1
//		int currentPage = businessForSelect.getPage().getCurrentPage();
//		businessForSelect.getPage().setCurrentPage(currentPage+1);
		
		List<Business> list= businessDao.selectLikeByPage(businessForSelect);
		
		//经过查询后，根据page对象设置hasMore
		Page page = businessForSelect.getPage();
		BusinessListDto result = new BusinessListDto();
		result.setHasMore(page.getCurrentPage()<page.getTotalPage());
		
		//对查询结果进行格式化
		for (Business business : list) {
			BusinessDto businessDtoTemp = new BusinessDto();
			BeanUtils.copyProperties(business, businessDtoTemp);
			result.getData().add(businessDtoTemp);
			businessDtoTemp.setImg(businessImageurl + business.getImgFileName());
		
			//为了兼容前端mumber 这个属性
			businessDtoTemp.setMumber(business.getNumber());
			businessDtoTemp.setStar(this.getStar(business));
		}
		return result;
	}
	
	
}
