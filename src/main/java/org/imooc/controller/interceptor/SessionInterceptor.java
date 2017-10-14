package org.imooc.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imooc.constant.SessionKeyConst;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author xzb
 *
 */
public class SessionInterceptor implements HandlerInterceptor{

	/**
	 * 进入Handle之前，执行本方法
	 * @return true:执行下一个拦截器，直到所有拦截器都执行完，再执行被拦截的Controller
	 * 		   false:从本拦截器 往回 执行 所有拦截器的afterCompletion()，再退出拦截器链
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getSession().getAttribute(SessionKeyConst.USER_INFO)!=null){
			return true;
		}
		request.getRequestDispatcher("/login/sessionTimeout").forward(request, response);
		return false;
	}

	/**
	 * 进入Handle之后，返回ModelAndView之前，执行本方法
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 进入Handle之后，执行本方法
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
