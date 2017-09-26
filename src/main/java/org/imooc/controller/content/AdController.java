package org.imooc.controller.content;

import javax.servlet.http.HttpServletRequest;

import org.imooc.constant.PageCodeEnum;
import org.imooc.dto.AdDto;
import org.imooc.service.content.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ad")
public class AdController {
	
	@Autowired
	private AdService adService;
	
	/**
	 * 广告管理页初始化(点广告管理菜单进入的第一个页面)
	 */
	@RequestMapping
	public String init(Model model,HttpServletRequest request){
		AdDto adDto = new AdDto();
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}
	
	/**
	 * 分页
	 * @param model
	 * @param adDto
	 * @return
	 */
	@RequestMapping("/search")
	public String search(Model model,AdDto adDto){
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam",adDto);
		
		return "/content/adList";
		
	}
	
	/**
	 * 新增初始化
	 * @return
	 */
	@RequestMapping("/addInit")
	public String addInit(){
		return "/content/adAdd";
	}
	
	/**
	 * 新增
	 * @param adDto
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(AdDto adDto,Model model){	
		if(adService.add(adDto)){
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
			
		}else{
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);

		}
		return "/content/adAdd";
	}
	
	@RequestMapping("/remove")
	public String remove(@RequestParam("id")Long id){
		adService.delete(id);
		return "forward:/ad";
	}
	
	/**
	 * 修改初始化
	 * TODO adModify.jsp查看图片未实现
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model,@RequestParam("id")Long id){
		model.addAttribute("modifyObj", adService.searchById(id));
		return "/content/adModify";
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	public String modify(Model model,AdDto adDto){
		model.addAttribute("modifyObj", adDto);
		if(adService.modify(adDto)){
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS);
		}else{
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
		}
		return "/content/adModify";
	}
	
}
