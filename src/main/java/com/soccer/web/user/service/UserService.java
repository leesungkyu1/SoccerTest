package com.soccer.web.user.service;

import com.soccer.web.user.vo.UserVO;

public interface UserService {

	void userJoin(UserVO userVO) throws Exception;

	UserVO userLogin(UserVO userVO) throws Exception;

}
