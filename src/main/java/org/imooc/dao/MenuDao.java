package org.imooc.dao;

import java.util.List;

import org.imooc.bean.Menu;

public interface MenuDao {
	/**
	 * 根据查询条件查询菜单列表（关联动作表，结果集里包含了动作列表）
	 * @param menu
	 * @return 菜单列表
	 */
	List<Menu> selectWithAction(Menu menu);
}
