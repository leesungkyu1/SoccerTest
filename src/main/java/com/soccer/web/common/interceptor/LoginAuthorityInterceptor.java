//package com.soccer.web.common.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.soccer.web.user.vo.UserVO;
//
//public class LoginAuthorityInterceptor extends HandlerInterceptorAdapter{
//		
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		HttpSession session = request.getSession();
//		
//		UserVO loginUserVO = new UserVO();
//		
//		loginUserVO = (UserVO)session.getAttribute("loginUser");
//		
//		if(loginUserVO.getUserId().equals("")) {
//			session.setAttribute("auth", "admin");
//		}else {
//			session.setAttribute("auth", "user");
//		}
//	}
//}
