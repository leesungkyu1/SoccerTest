package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.soccer.web.payment.controller.PaymentController;
import com.soccer.web.user.vo.UserVO;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentTest {
	
	@Autowired
	PaymentController paymentController;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	@Transactional
	@DisplayName("결제 테스트")
	void paymentTest() throws Exception{
		UserVO loginUser = new UserVO();
		
		loginUser.setUserIdx(10);
		loginUser.setUserId("1cc3265b-5");
		loginUser.setUserPassword("bbb");
		loginUser.setRegionIdx(1);
		loginUser.setUserMobile("010-0000-0000");
		loginUser.setUserName("김똘똘");
		loginUser.setUserNick("똘똘이");
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("userInfo", session);
		
		mvc.perform(post("/user/" + loginUser.getUserIdx())
				.session(session))
				.andExpect(model().)
				.andDo(print());
	}
}
