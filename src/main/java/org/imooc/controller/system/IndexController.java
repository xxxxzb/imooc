package org.imooc.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制页面跳转至WEB-INF/jsp/system/index.jsp
 * @author xzb
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	
	@RequestMapping()
	public String init(){
		return "/system/index";
	}
}
