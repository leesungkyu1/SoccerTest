package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.soccer.web.user.controller.UserController;
import com.soccer.web.user.vo.UserVO;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
	
	@Autowired
	UserController UserController;
	
	@Autowired
	MockMvc mvc;

	@Test
	@Transactional
	@DisplayName("회원가입(저장), 데이터 조회")
	void joinTest() throws Exception {
		UserVO userVO = new UserVO();
		
		userVO.setUserId("aaa");
		userVO.setUserPassword("bbb");
		userVO.setRegionIdx(1);
		userVO.setUserMobile("010-0000-0000");
		userVO.setUserName("김똘똘");
		userVO.setUserNick("똘똘이");
		
		mvc.perform(post("")
				.contentType("application/x-www-form-​urlencoded")
				.content(userVO)
				);
	}
}
