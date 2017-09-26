package org.imooc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.imooc.bean.Ad;
import org.imooc.dao.AdDao;
import org.imooc.dto.AdDto;
import org.imooc.service.content.AdService;
import org.imooc.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AdServiceImpl implements AdService{
		
	@Autowired
	private AdDao adDao;
	
	@Value("${adImage.savePath}")
	private String adImageSavePath;

	@Value("${adImage.url}")
	private String adImageUrl;
	
	@Override
	//TODO 可以改成获取失败的详细原因
	public boolean add(AdDto adDto) {
		Ad ad=new Ad();
		BeanUtils.copyProperties(adDto, ad);
		
		if(adDto.getImgFile()!=null && adDto.getImgFile().getSize()>0){
			
			//adDto.getImgFile().getOriginalFilename():
			//adDto.getImgFile()拿到jsp传来的文件，getOriginalFilename()拿到上传时选中的文件名
			String fileName=System.currentTimeMillis() + "_" + adDto.getImgFile().getOriginalFilename();
			
			File file=new File(adImageSavePath + fileName);				
			
			//对adImageSavePath文件夹判断
			File fileFolder = new File(adImageSavePath);
			if( ! fileFolder.exists()){
				//多级目录一并创建
				fileFolder.mkdirs();
			}
			
			try {
				
				adDto.getImgFile().transferTo(file);
				ad.setImgFileName(fileName);
				adDao.insert(ad);
				return true;
				
			} catch (IllegalStateException | IOException e) {
				//TODO 需要添加日志
				return false;
			}
		}else{
			//若文件上传比填，你没填的情况
			return false;
		}
		
	}
	
	public List<AdDto> searchByPage(AdDto adDto){
		List<AdDto> result = new ArrayList<AdDto>();
		Ad condition = new Ad();
		
		BeanUtils.copyProperties(adDto, condition);
		List<Ad> adList = adDao.selectByPage(condition);
		
		for (Ad ad : adList) {
			AdDto adDtoTemp = new AdDto();
			adDtoTemp.setImg(adImageUrl+ad.getImgFileName());
			BeanUtils.copyProperties(ad, adDtoTemp);
			result.add(adDtoTemp);
		}
		return result;
	}
	
	public void delete(Long id){
		adDao.delete(id); 
	}

	
	public boolean modify(AdDto adDto) {
		Ad ad = new Ad();
		BeanUtils.copyProperties(adDto, ad);
		String fileName = null;
		if(adDto.getImgFile() !=null && adDto.getImgFile().getSize()>0){
			try {
				
				fileName = FileUtil.save(adDto.getImgFile(), adImageSavePath);
				ad.setImgFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				//TODO 需要添加日志
				return false;
			}
		}
		int updateCount = adDao.update(ad);
		if(updateCount !=1){
			return false;
		}
		if(fileName !=null){
			//删除原来的图片
			return FileUtil.delete(adImageSavePath+adDto.getImgFileName());
		}
		return true;
	}

	public AdDto searchById(Long id) {
		AdDto adDto = new AdDto();
		BeanUtils.copyProperties(adDao.selectById(id), adDto);
		
		return adDto;
	}

	

}
