package com.soccer.web.user.service;

import java.util.List;

import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.user.vo.UserVO;

public interface UserService {

	void userJoin(UserVO userVO) throws Exception;

	UserVO userLogin(UserVO userVO) throws Exception;

	UserVO userInfo(Integer userIdx) throws Exception;

	UserVO userInfoUpdate(UserVO userVO) throws Exception;

	Integer checkId(UserVO userVO) throws Exception;

	List<ChannelVO> joinChannelList(Integer userIdx) throws Exception;

}
