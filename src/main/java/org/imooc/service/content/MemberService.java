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
	
	 /**
     * 下发短信验证码
     * @param phone 手机号
     * @param content 验证码
     * @return 是否发送成功：true：发送成功，false：发送失败
     */
	boolean sendCode(Long phone,String content);
	
	/**
     * 根据手机号获取验证码的MD5码值
     * @param phone 手机号
     * @return 验证码的MD5码值
     */
    String getCode(Long phone);
    
    /**
	 * 保存手机号和token到缓存中
	 */
	void saveToken(String token,Long phone);
}
