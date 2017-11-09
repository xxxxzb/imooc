package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.imooc.bean.Menu;
import org.imooc.dao.MenuDao;
import org.imooc.dto.MenuForZtreeDto;
import org.imooc.service.content.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDao menuDao;
	
	public List<MenuForZtreeDto> getZtreeList() {
		List<MenuForZtreeDto> result = new ArrayList<MenuForZtreeDto>();
		List<Menu> menuList = menuDao.selectWithAction(new Menu());
		for (Menu menu : menuList) {
			MenuForZtreeDto menuForZtreeDto = new MenuForZtreeDto();
			result.add(menuForZtreeDto);
			BeanUtils.copyProperties(menu, menuForZtreeDto);
			menuForZtreeDto.setOpen(true);
			menuForZtreeDto.setComboId(MenuForZtreeDto.PREFIX_MENU + menu.getId());
			menuForZtreeDto.setComboParentId(MenuForZtreeDto.PREFIX_MENU + menu.getParentId());
			
			// 组装菜单下对应的动作
			
		}
		return result;
	}

}
