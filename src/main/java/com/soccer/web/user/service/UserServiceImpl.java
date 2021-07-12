package com.soccer.web.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.user.dao.UserMapper;
import com.soccer.web.user.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserMapper userDAO;
	
	//회원가입
	@Override
	public void userJoin(UserVO userVO) throws Exception{
		userDAO.userJoin(userVO);
	}
	
	//로그인
	@Override
	public UserVO userLogin(UserVO userVO) throws Exception{
		return userDAO.userLogin(userVO);
	}
	
	//마이페이지 유저 정보
	public UserVO userInfo(Integer userIdx) throws Exception{
		return userDAO.userInfo(userIdx);
	}
	
	//마이페이지 정보 수정
	public UserVO userInfoUpdate(UserVO userVO) throws Exception{
		userDAO.userInfoUpdate(userVO);
		return userDAO.userInfo(userVO.getUserIdx()); 
	}
}
