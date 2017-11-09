package org.imooc.controller.system;

import java.util.List;

import org.imooc.dto.UserDto;
import org.imooc.service.content.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UserService userSvice;
	/**
	 * 获取用户列表
	 */
	@RequestMapping()
	public List<UserDto> getList(){
		return userSvice.getList();
	}
}
