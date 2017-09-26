package org.imooc.controller.content;


import org.imooc.constant.DicTypeConst;
import org.imooc.constant.PageCodeEnum;
import org.imooc.dao.BusinessDao;
import org.imooc.dto.BusinessDto;
import org.imooc.service.content.BusinessService;
import org.imooc.service.content.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/businesses")
public class BusinessesController {
	
	@Autowired
	private BusinessService businessService;
	
	@Autowired
	private DicService dicService;
	
	/**
	 * 商户列表
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String search(BusinessDto dto,Model model){
		model.addAttribute("list",businessService.seachByPage(dto));
		model.addAttribute("searchParam", dto);
		return "/content/businessList";	
	}
	
	/**
	 * 删除商户
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String remove(@PathVariable("id")Long id){
		businessService.delete(id);
		return "redirect:/businesses";
	}
	
	/**
	 * 商户新增初始页
	 * @return
	 */
	@RequestMapping(value="/addPage" ,method=RequestMethod.GET)
	public String addInit(Model model){
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		return "/content/businessAdd";
	}
	
	/**
	 * 商户新增
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String add(BusinessDto dto,RedirectAttributes attr){
		if(businessService.insert(dto)){
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
			return "redirect:/businesses";
		}else{
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
			return "redirect:/businesses/addPage";
		}
	}
	
	/**
	 * 商户修改初始页
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String modifyInit(@PathVariable("id")Long id,Model model){
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		model.addAttribute("modifyObj", businessService.seachById(id));
		return "/content/businessModify";
	}
	
	/**
	 * 商户修改
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public String modify(@PathVariable("id")Long id,Model model){
		
		return "/content/businessModify";
	}
}
