package org.imooc.controller.system;

import java.util.List;

import org.imooc.dto.MenuDto;
import org.imooc.dto.MenuForZtreeDto;
import org.imooc.service.content.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单相关
 */
@RestController
@RequestMapping("/menus")
public class MenusController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping()
	public List<MenuForZtreeDto> getList(){
		return menuService.getZtreeList();
	}
}
