package org.imooc.controller.system;

import java.util.List;

import org.imooc.dto.GroupDto;
import org.imooc.service.content.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户组相关
 */
@RestController
@RequestMapping("/groups")
public class GroupController {
	
	@Autowired
	private GroupService groupService;
	
	@RequestMapping()
	public List<GroupDto> getList(){
		return groupService.getList();
	}
}
