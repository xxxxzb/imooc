package org.imooc.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码缓存，存放用户手机号和验证码
 * @author xzb
 *
 */
public class CodeCache {
	private static CodeCache instance;
	private Map<Long,String> codeMap;
	
	private CodeCache(){
		codeMap=new HashMap<>();
	}
	
	public static CodeCache getInstance(){
		if(instance==null){
			synchronized (CodeCache.class) {
				if(instance==null){
					instance = new CodeCache();
				}
			}
		}
		return instance;
	}
	
	public boolean save(Long phone,String code){
		if(codeMap.containsKey(phone)){
			return false;
		}
		codeMap.put(phone, code);
		return true;
	}
	
	/**
	 * 根据手机号获取验证码
	 */
	public String getCode(Long phone){
		return codeMap.get(phone);
	}
}
