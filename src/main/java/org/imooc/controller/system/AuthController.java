package org.imooc.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthController {
	@RequestMapping("/auth")
	public String auth(){
		return "/system/auth";
	}
}
