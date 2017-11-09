package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.imooc.bean.User;
import org.imooc.dao.UserDao;
import org.imooc.dto.UserDto;
import org.imooc.service.content.UserService;
import org.imooc.util.CommonUtil;
import org.imooc.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 验证用户登录信息
	 */
	@Override
	public boolean validate(UserDto userDto) {
		if(userDto!=null && !CommonUtil.isEmpty(userDto.getName()) 
				&& !CommonUtil.isEmpty(userDto.getPassword())){
			User user =new User();
			BeanUtils.copyProperties(userDto, user);
			return userDao.select(user).size()==1;
		}
		return false;
	}

	public boolean add(UserDto userDto){
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setPassword(MD5Util.getMD5(userDto.getPassword()));
		return userDao.insert(user)==1;
	}

	
	public List<UserDto> getList() {
		List<UserDto> result = new ArrayList<UserDto>();
		List<User> userList = userDao.select(new User());
		for (User user : userList) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			result.add(userDto);
			userDto.setpId(0);
		}
		return result;
	}
}
