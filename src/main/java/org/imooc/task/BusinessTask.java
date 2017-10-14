package org.imooc.task;

import org.imooc.dao.BusinessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商户相关的定时任务
 * @author xzb
 *
 */
@Component("BusinessTask")
public class BusinessTask {
	
	@Autowired
	private BusinessDao businessDao;
	
	//private static final Logger logger = LoggerFactory.getLogger(BusinessTask.class);
	/**
	 * 同步已售数量
	 */
	
	public void synNumber(){
		businessDao.updateNumber();
	}
}
