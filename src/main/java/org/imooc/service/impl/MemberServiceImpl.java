package org.imooc.service.impl;

import java.util.List;
import org.imooc.bean.Member;
import org.imooc.cache.CodeCache;
import org.imooc.cache.TokenCache;
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
		//TODO 真实环境中，问题1可以用redis去实现
		/*实际环境中，需处理3个问题：
		1、集群服务器中，如何保证每次请求都是同一台服务器
		2、如何挡住重复请求
		3、实现token时效性（如：保持10分钟 然后失效）*/
		
		CodeCache codeCache= CodeCache.getInstance();
		return codeCache.save(phone, MD5Util.getMD5(code));
	}

	@Override
	public boolean sendCode(Long phone, String content) {
		logger.info(phone+"|"+content);
		return true;
	}

	@Override
	public String getCode(Long phone) {
		CodeCache codeCache= CodeCache.getInstance();
		return codeCache.getCode(phone);
	}

	@Override
	public void saveToken(String token,Long phone) {
		TokenCache tokenCache = TokenCache.getInstance();
		tokenCache.saveToken(token, phone);
	}
	
	public Long getPhone(String token) {
		TokenCache tokenCache = TokenCache.getInstance();
		return tokenCache.getToken(token);
	}
	
	public Long getIdByPhone(Long phone) {
		Member member = new Member();
		member.setPhone(phone);
		List<Member>list = memberDao.select(member);
		if(list!=null &&list.size()==1) {
			return list.get(0).getId();
		}
		return null;
	}

}
