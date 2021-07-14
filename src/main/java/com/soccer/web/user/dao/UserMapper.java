package com.soccer.web.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.vo.ChannelVO;
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
	void userInfoUpdate(UserVO userVO) throws Exception;
	
	//아이디 중복 체크
	Integer checkId(UserVO userVO) throws Exception;

	//가입한 채널 리스트 가져오기
	List<ChannelVO> joinChannelList(Integer userIdx) throws Exception;
}
