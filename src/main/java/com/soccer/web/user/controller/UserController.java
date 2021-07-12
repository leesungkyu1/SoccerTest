package com.soccer.web.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.user.service.UserServiceImpl;
import com.soccer.web.user.vo.UserVO;

@Controller
public class UserController {
	
	@Autowired
	UserServiceImpl userService;
	
	//회원가입
	@RequestMapping(value = "" , method = RequestMethod.POST)
	public String userJoin(UserVO userVO, RedirectAttributes attributes) throws Exception{
		try {
			userService.userJoin(userVO);
			
			attributes.addAttribute("code", 200);
			attributes.addAttribute("message", "가입하셨습니다.");
		}catch (Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 101);
			attributes.addAttribute("message", "회원가입이 실패했습니다.");
			
			return "redirect:";
		}		
		
		return "redirect:";
	}
	
	//로그인
	@RequestMapping(value = "" , method = RequestMethod.POST)
	public String userLogin(UserVO userVO, Model model, HttpSession session) throws Exception{
		UserVO authUser = null;
		
		try {
			authUser = userService.userLogin(userVO);
			
			if(authUser == null) {
				model.addAttribute("message", "아이디나 비밀번호를 확인하세요.");
			}else {
				session.setAttribute("loginUser", authUser);	
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			model.addAttribute("code", 102);
			model.addAttribute("message", "에러가 발생했습니다.");
			
			return "";
		}		
		
		return "";
	}
	
	//마이 페이지 정보 {} 동적 파라미터 알아보기
	@RequestMapping(value = "/{userIdx}", method = RequestMethod.GET)
	public String userInfo(@PathVariable("userIdx") Integer userIdx, Model model,
			HttpSession session) throws Exception{
		
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		
		if(userIdx == null || userIdx != loginUser.getUserIdx()) {
			model.addAttribute("message", "잘못된 접근입니다.");
		}
		
		UserVO userInfo = userService.userInfo(userIdx);
		
		model.addAttribute("userInfo", userInfo);
		
		return "";
	}
	
	//마이페이지 정보 수정
	@RequestMapping(value = "/{userIdx}", method = RequestMethod.PUT)
	public String userInfoUpdate(@PathVariable("userIdx") Integer userIdx, RedirectAttributes attributes,
			UserVO userVO, HttpSession session) throws Exception{
		
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		
		if(userIdx == null || userIdx != loginUser.getUserIdx()) {
			attributes.addAttribute("message", "잘못된 접근입니다.");
		}
		
		userVO.setUserIdx(userIdx);
		UserVO updateUser = userService.userInfoUpdate(userVO);
		
		session.setAttribute("loginUser", updateUser);
		attributes.addAttribute("message", "정보를 수정하셨습니다.");
		
		return "redirect:";
	}
}
