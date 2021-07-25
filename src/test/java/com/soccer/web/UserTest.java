package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.UUID;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
	@DisplayName("회원가입(저장)")
	void joinTest() throws Exception {
		UUID uuid = UUID.randomUUID();
		
		UserVO userVO = new UserVO();
		
		userVO.setUserId(uuid.toString().substring(0, 10));
		userVO.setUserPassword("bbb");
		userVO.setRegionIdx(1);
		userVO.setUserMobile("010-0000-0000");
		userVO.setUserName("김똘똘");
		userVO.setUserNick("똘똘이");
		
		UrlEncodedFormEntity userInfo = new UrlEncodedFormEntity(Arrays.asList(
				new BasicNameValuePair("userId", userVO.getUserId()),
				new BasicNameValuePair("userPassword", userVO.getUserPassword()),
				new BasicNameValuePair("regionIdx", String.valueOf(userVO.getRegionIdx())),
				new BasicNameValuePair("userMobile", userVO.getUserMobile()),
				new BasicNameValuePair("userName", userVO.getUserName()),
				new BasicNameValuePair("userNick", userVO.getUserNick())
		), "utf-8");
		
		mvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils.toString(userInfo)))
				.andExpect(model().attribute("message", "가입하셨습니다."))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("로그인")
	void loginTest() throws Exception {
		UserVO userVO = new UserVO();
		
		userVO.setUserId("aaa");
		userVO.setUserPassword("bbb");
		
		UrlEncodedFormEntity userInfo = new UrlEncodedFormEntity(Arrays.asList(
				new BasicNameValuePair("userId", userVO.getUserId()),
				new BasicNameValuePair("userPassword", userVO.getUserPassword())
		), "utf-8");
		
		mvc.perform(post("/user/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils.toString(userInfo)))
				.andExpect(model().attribute("message", "로그인 하셨습니다."))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("회원 정보 조회")
	void userInfoTest() throws Exception {
		//다른 방식 (리턴값을 볼 수 있다?)
		UserVO userVO = new UserVO();
		
		userVO.setUserIdx(6);
		userVO.setUserId("aaa");
		userVO.setUserPassword("bbb");
		userVO.setRegionIdx(1);
		userVO.setUserMobile("010-0000-0000");
		userVO.setUserName("김똘똘");
		userVO.setUserNick("똘똘이");
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loginUser", userVO);
		
		MvcResult mvcResult = mvc.perform(get("/user/6")
					.session(session)
				)
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("userInfo"))
				.andExpect(model().attributeExists("joinChannelList"))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print())
				.andReturn();
		
		String result = mvcResult.getResponse().getContentAsString();
		
		System.out.println("========================================");
		System.out.println(result);
		System.out.println("========================================");
	}
	
	@Test
	@Transactional
	@DisplayName("회원 정보 수정")
	void userInfoUpdateTest() throws Exception{
		UserVO loginUser = new UserVO();
		
		loginUser.setUserIdx(10);
		loginUser.setUserId("1cc3265b-5");
		loginUser.setUserPassword("bbb");
		loginUser.setRegionIdx(1);
		loginUser.setUserMobile("010-0000-0000");
		loginUser.setUserName("김똘똘");
		loginUser.setUserNick("똘똘이");
		
		UserVO updateUserVO = new UserVO();
		
		updateUserVO.setUserPassword("ccc");
		updateUserVO.setRegionIdx(2);
		updateUserVO.setUserMobile("010-1111-1111");
		updateUserVO.setUserName("김똒똒");
		updateUserVO.setUserNick("똒똒이");
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("loginUser", loginUser);
		
		UrlEncodedFormEntity userUpdateInfo = new UrlEncodedFormEntity(Arrays.asList(
				new BasicNameValuePair("userPassword", updateUserVO.getUserPassword()),
				new BasicNameValuePair("regionIdx", String.valueOf(updateUserVO.getRegionIdx())),
				new BasicNameValuePair("userMobile", updateUserVO.getUserMobile()),
				new BasicNameValuePair("userName", updateUserVO.getUserName()),
				new BasicNameValuePair("userNick", updateUserVO.getUserNick())
		), "utf-8");
		
		mvc.perform(put("/user/" + loginUser.getUserIdx())
				.session(session)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils.toString(userUpdateInfo)))
				.andExpect(model().attribute("message", "정보를 수정하셨습니다."))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
	}
}
