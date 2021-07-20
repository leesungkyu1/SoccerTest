package com.soccer.web.user.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.region.service.RegionServiceImpl;
import com.soccer.web.region.vo.RegionVO;
import com.soccer.web.user.service.UserServiceImpl;
import com.soccer.web.user.vo.UserVO;

@Controller
public class UserController {
	//세션 관련 코드 intercepter로 분리 필요
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	RegionServiceImpl regionService;
	
	//회원가입 뷰
	@RequestMapping(value = "/user/view", method = RequestMethod.GET)
	public String userJoinView(Model model) throws Exception {
		List<RegionVO> regionList = regionService.regionList();
		
		model.addAttribute("regionList", regionList);
		
		return "main/sign_up";
	}
	
	//회원가입
	@RequestMapping(value = "/user" , method = RequestMethod.POST)
	public String userJoin(UserVO userVO, Model model) throws Exception{
		try {
			Integer idCheck = userService.checkId(userVO);
			
			if(idCheck == 0) {
				userService.userJoin(userVO);
				
				model.addAttribute("message", "가입하셨습니다.");
				
				return "main/sign_up_comple";
			}else {
				model.addAttribute("message", "아이디가 중복되었습니다.");
				
				return "main/sign_up";
			}
		}catch (Exception e) {
			e.printStackTrace();
			
			model.addAttribute("code", 101);
			model.addAttribute("message", "회원가입이 실패했습니다.");
			
			return "main/sign_up";
		}		
	}
	
	//로그인 페이지 뷰
	@RequestMapping(value = "/user/login/view", method = RequestMethod.GET)
	public String userLoginView() throws Exception {
		return "main/log_in";
	}
	
	//로그인
	@RequestMapping(value = "/user/login" , method = RequestMethod.POST)
	public String userLogin(UserVO userVO, Model model, RedirectAttributes attributes, HttpSession session) throws Exception{
		UserVO authUser = null;
		try {
			authUser = userService.userLogin(userVO);
			
			if(authUser == null) {
				model.addAttribute("message", "아이디나 비밀번호를 확인하세요.");
				
				return "main/log_in";
			}else {
				//attributes.addAttribute("message", "로그인 하셨습니다.");
				
				session.setAttribute("loginUser", authUser);
				
				return "redirect:/main";
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			model.addAttribute("code", 102);
			model.addAttribute("message", "에러가 발생했습니다.");
			
			return "main/log_in";
		}		
	}
	
	//정보 조회
	@RequestMapping(value = "/user/{userIdx}", method = RequestMethod.GET)
	public String userInfo(@PathVariable("userIdx") Integer userIdx, Model model,
			HttpSession session) throws Exception{
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		
		if(userIdx == null || userIdx != loginUser.getUserIdx()) {
			model.addAttribute("code", 103);
			model.addAttribute("message", "잘못된 접근입니다.");
		}else {
			UserVO userInfo = userService.userInfo(userIdx);
			List<ChannelVO> joinChannelList = userService.joinChannelList(userIdx);
			
			model.addAttribute("joinChannelList",joinChannelList);
			model.addAttribute("userInfo", userInfo);
		}
		return "user/mypage.html";
	}
	
	//정보 수정 뷰
	@RequestMapping(value = "/user/userView/{userIdx}", method = RequestMethod.GET)
	public String userInfoUpdateView(@PathVariable("userIdx") Integer userIdx, Model model) throws Exception {
		UserVO userInfo = userService.userInfo(userIdx);
		List<RegionVO> regionList = regionService.regionList();
		
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("regionList", regionList);
		
		return "user/information_modify";
	}
	
	//마이페이지 정보 수정
	@RequestMapping(value = "/user/{userIdx}", method = RequestMethod.PUT)
	public String userInfoUpdate(@PathVariable("userIdx") Integer userIdx, RedirectAttributes attributes,
			UserVO userVO, HttpSession session) throws Exception{
		
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		
		if(userIdx == null || userIdx != loginUser.getUserIdx()) {
			attributes.addAttribute("code", 104);
			attributes.addAttribute("message", "잘못된 접근입니다.");
		}else {
			userVO.setUserIdx(userIdx);
			UserVO updateUser = userService.userInfoUpdate(userVO);
			
			session.setAttribute("loginUser", updateUser);
			attributes.addAttribute("message", "정보를 수정하셨습니다.");
		}
		
		return "redirect:/user/" + loginUser.getUserIdx();
	}
}
