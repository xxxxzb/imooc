package org.imooc.service.impl;

import java.util.List;
import org.imooc.bean.Member;
import org.imooc.cache.CodeCache;
import org.imooc.dao.MemberDao;
import org.imooc.service.content.MemberService;
import org.imooc.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {

	private final static Logger logger =  LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public boolean exists(Long phone) {
		Member member = new Member();
		member.setPhone(phone);
		List<Member> result = memberDao.select(member);
		return result.size()==1;
	}

	@Override
	public boolean saveCode(Long phone, String code) {
		//TODO 真实环境中，用redis去实现
		CodeCache codeCache= CodeCache.getInstance();
		return codeCache.save(phone, MD5Util.getMD5(code));
	}

	@Override
	public boolean sendCode(Long phone, String content) {
		logger.info(phone+"|"+content);
		return true;
	}

}
