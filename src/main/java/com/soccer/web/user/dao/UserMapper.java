package com.soccer.web.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.user.vo.UserVO;

@Mapper
public interface UserMapper {
	
	//회원가입
	void userJoin(UserVO userVO) throws Exception;

	//로그인
	UserVO userLogin(UserVO userVO) throws Exception;
	
	//마이페이지 유저 정보
	UserVO userInfo(Integer userIdx) throws Exception;
	
	//마이페이지 정보 수정
	UserVO userInfoUpdate(UserVO userVO) throws Exception;

}
