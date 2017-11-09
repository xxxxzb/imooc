package org.imooc.service.content;

import java.util.List;

import org.imooc.dto.MenuForZtreeDto;

public interface MenuService {
	
	/**
	 * 获取菜单树列表
	 * @return 菜单树列表
	 */
	List<MenuForZtreeDto> getZtreeList();
}
