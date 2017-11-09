package org.imooc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.imooc.bean.Group;
import org.imooc.dao.GroupDao;
import org.imooc.dto.GroupDto;
import org.imooc.service.content.GroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public List<GroupDto> getList() {
		List<GroupDto> result = new ArrayList<>();
		List<Group> groupList = groupDao.select(new Group());
		for (Group group : groupList) {
			GroupDto groupDto = new GroupDto();
			result.add(groupDto);
			BeanUtils.copyProperties(group, groupDto);
			groupDto.setpId(0);
		}
		return result;
	}

	@Override
	public boolean modify(GroupDto groupDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean add(GroupDto groupDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GroupDto getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GroupDto getByIdWithMenuAction(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean assignMenu(GroupDto groupDto) {
		// TODO Auto-generated method stub
		return false;
	}

}
