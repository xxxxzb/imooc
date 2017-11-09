package org.imooc.service.content;

import java.util.List;

import org.imooc.dto.UserDto;

public interface UserService {
	/**
	 * 验证用户登录信息
	 */
	boolean validate(UserDto userDto);
	
	/**
	 * 获取用户列表
	 */
	List<UserDto> getList();
}
