package com.soccer.web.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.user.dao.UserMapper;
import com.soccer.web.user.vo.UserVO;

@Service("userService")
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
	@Override
	public UserVO userInfo(Integer userIdx) throws Exception{
		return userDAO.userInfo(userIdx);
	}
	
	//마이페이지 정보 수정
	@Override
	public UserVO userInfoUpdate(UserVO userVO) throws Exception{
		userDAO.userInfoUpdate(userVO);
		return userDAO.userInfo(userVO.getUserIdx()); 
	}
	
	//아이디 중복 체크
	@Override
	public Integer checkId(UserVO userVO) throws Exception{
		return userDAO.checkId(userVO);
	}
	
	//채널 가입 목록 가져오기
	@Override
	public List<ChannelVO> joinChannelList(Integer userIdx) throws Exception{
		return userDAO.joinChannelList(userIdx);
	}
}
