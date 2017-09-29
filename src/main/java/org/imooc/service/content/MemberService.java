package org.imooc.service.content;

public interface MemberService {
	/**
	 * 验证手机号是否存在
	 */
	boolean exists(Long phone);
	
	/**
	 * 保存手机号和对应的MD5码到缓存中
	 */
	boolean saveCode(Long phone,String code);
}
