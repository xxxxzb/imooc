package org.imooc.service.impl;

import org.imooc.bean.Page;
import org.imooc.dto.CommentForSubmitDto;
import org.imooc.dto.CommentListDto;
import org.imooc.service.content.CommentService;

public class CommentServiceImpl implements CommentService {

	@Override
	public boolean add(CommentForSubmitDto commentForSubmitDto) {
		
		return false;
	}

	@Override
	public CommentListDto getListByBusinessId(Long businessId, Page page) {

		return null;
	}

}
