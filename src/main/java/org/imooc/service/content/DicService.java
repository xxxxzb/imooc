package org.imooc.service.content;

import java.util.List;
import org.imooc.bean.*;

public interface DicService {
	
	/**
	 * 根据类型获取字典表列表
	 * @param type
	 * @return
	 */
	public List<Dic> getListByType(String type);
}
