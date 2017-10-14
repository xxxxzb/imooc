package org.imooc.controller.system;

import javax.servlet.http.HttpSession;

import org.imooc.constant.PageCodeEnum;
import org.imooc.constant.SessionKeyConst;
import org.imooc.dto.UserDto;
import org.imooc.service.content.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping()
	public String login(){
		return "/system/login";
	}
	
	/**
	 * session超时
	 * @return
	 */
	@RequestMapping("/sessionTimeout")
	public String sessionTimeout(RedirectAttributes attr){
		attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.SESSION_TIMEOUT);
		return "redirect:/login";
	}
	
	/**
	 * 验证用户名、密码是否正确  验证通过后跳转至后台管理首页，验证失败，返回登录页面
	 */
	@RequestMapping("/validate")
	public String validate(UserDto userDto,RedirectAttributes attr,HttpSession session){
		if(userService.validate(userDto)){
			session.setAttribute(SessionKeyConst.USER_INFO, userDto);
			return "redirect:/index";
		}
		attr.addFlashAttribute(PageCodeEnum.KEY, PageCodeEnum.LOGIN_FAIL);
		return "redirect:/login";
	}
}
