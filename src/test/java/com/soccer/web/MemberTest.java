package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.soccer.web.channel.member.apply.vo.ApplyVO;
import com.soccer.web.channel.member.controller.MemberController;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberTest {
	
	@Autowired
	MemberController memberController;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	@Transactional
	@DisplayName("채널 회원가입 신청 테스트")
	void memberApplyTest() throws Exception{
		MemberVO memberVO = new MemberVO();
		
		memberVO.setUserIdx(12);
		memberVO.setMemberNick("잠자고싶다");
		memberVO.setMemberPosition("G");
		
		UrlEncodedFormEntity memberInfo = new UrlEncodedFormEntity(Arrays.asList(
				new BasicNameValuePair("userIdx", String.valueOf(memberVO.getUserIdx())),
				new BasicNameValuePair("memberNick", memberVO.getMemberNick()),
				new BasicNameValuePair("memberPosition", memberVO.getMemberPosition())
		), "utf-8");
		
		FileInputStream fis = new FileInputStream(new File("C:/Users/akm45/Desktop/바탕화면/test.png"));

		MockMultipartFile imageFile = new MockMultipartFile("imageFile", "test.png", "image/png", fis);
		
		mvc.perform(MockMvcRequestBuilders.multipart("/channel/member/" + 1)
				.file(imageFile)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(EntityUtils.toString(memberInfo)))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("채널 회원가입 승인 테스트")
	void memberAcceptTest() throws Exception{
		ApplyVO applyVO = new ApplyVO();
		
		applyVO.setApplyIdx(4);
		applyVO.setChannelIdx(1);
		
		mvc.perform(put("/channel/apply/" + applyVO.getChannelIdx() + "/" + applyVO.getApplyIdx()))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("채널 회원가입 거절 테스트")
	void memberDenie() throws Exception {
		ApplyVO applyVO = new ApplyVO();
		
		applyVO.setApplyIdx(4);
		applyVO.setChannelIdx(1);
		
		mvc.perform(delete("/channel/apply/" + applyVO.getChannelIdx() + "/" + applyVO.getApplyIdx()))
				.andExpect(model().attribute("message", "회원가입이 거절되었습니다."))
				.andExpect(model().attributeDoesNotExist("code"))
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("채널 회원, 승인 신청 리스트")
	void channelMemberListTest() throws Exception{
		ChannelVO channelVO = new ChannelVO();
		
		channelVO.setChannelIdx(1);
		
		mvc.perform(get("/channel/member/" + channelVO.getChannelIdx()))
				.andExpect(model().attributeExists("memberList"))
				.andExpect(model().attributeExists("applyList"))
				.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("회원 강퇴")
	void memberDeleteTest() throws Exception {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setChannelIdx(1);
		memberVO.setMemberIdx(4);
		
		mvc.perform(delete("/channel/member/" + memberVO.getChannelIdx() + "/" + memberVO.getMemberIdx()))
			.andExpect(model().attribute("message", "회원을 탈퇴시켰습니다."))
			.andExpect(model().attributeDoesNotExist("code"))
			.andDo(print());
	}
	
	@Test
	@Transactional
	@DisplayName("경기 생성용 회원 검색")
	void searchByChannelTest() throws Exception {
		ChannelVO channelVO = new ChannelVO();
		
		channelVO.setChannelIdx(1);
		
		mvc.perform(get("/channel/play/member/" + channelVO.getChannelIdx()))
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
}
