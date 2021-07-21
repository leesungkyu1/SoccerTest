//package com.soccer.web.common.interceptor;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//public class AdminAuthorityInterceprot extends HandlerInterceptorAdapter{
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		
//		//추후 페이지에 대한 권한 체크가 필요할시 복사해서 적용
//		HttpSession session = request.getSession();
//		
//		if(session.getAttribute("auth") != null) {
//			boolean adminAuth = session.getAttribute("auth").equals("admin") ? true : false;
//			
//			if(adminAuth) {
//				request.setAttribute("message", "권한이 없습니다.");
//			}
//		}else {
//			request.setAttribute("message", "로그인이 필요합니다.");
//		}
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/main");
//		dispatcher.forward(request, response);
//		
//		return super.preHandle(request, response, handler);
//	}
//}
