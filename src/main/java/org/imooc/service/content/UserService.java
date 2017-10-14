package org.imooc.service.content;

import org.imooc.dto.UserDto;

public interface UserService {
	/**
	 * 验证用户登录信息
	 */
	boolean validate(UserDto userDto);
	
	/**
	 * 
	 */
}
