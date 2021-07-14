package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonString;
import com.soccer.web.notice.controller.NoticeController;
import com.soccer.web.notice.service.NoticeService;
import com.soccer.web.notice.vo.NoticeVO;

@SpringBootTest
@AutoConfigureMockMvc
public class NoticeTestController {

	@Autowired
	NoticeController noticeController;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	MockMvc mockMvc;
	
	// 공지사항 추가 테스트 성공 메서드
	@Test
	@Transactional
	void insertNoticeTestOK() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
			noticeVO.setNoticeTitle("첫번째 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("첫번째 공지글 내용입니다.");
			
			UrlEncodedFormEntity notice = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("userIdx", String.valueOf(noticeVO.getUserIdx())),
					new BasicNameValuePair("noticeTitle", noticeVO.getNoticeTitle()),
					new BasicNameValuePair("noticeType", noticeVO.getNoticeType()),
					new BasicNameValuePair("noticeDesc", noticeVO.getNoticeDesc())
			), "UTF-8");
			
			mockMvc.perform(post("/main/notice")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(EntityUtils.toString(notice)))
					.andExpect(status().isOk())
					.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 추가 테스트 실패 메서드
	@Test
	@Transactional
	void insertNoticeTestFail() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
//			noticeVO.setNoticeTitle("첫번째 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("첫번째 공지글 내용입니다.");
			
			UrlEncodedFormEntity notice = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("userIdx", String.valueOf(noticeVO.getUserIdx())),
//					new BasicNameValuePair("noticeTitle", noticeVO.getNoticeTitle()),
					new BasicNameValuePair("noticeType", noticeVO.getNoticeType()),
					new BasicNameValuePair("noticeDesc", noticeVO.getNoticeDesc())
			), "UTF-8");
			
			mockMvc.perform(post("/main/notice")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(EntityUtils.toString(notice)))
					.andExpect(status().isOk())
					.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 리스트 출력 테스트 성공 메서드
	@Test
	@Transactional
	void selectNoticeListTestOK() throws Exception {
		try {
			for (int i = 1; i <= 5; i++) {
				NoticeVO noticeVO = new NoticeVO();
				noticeVO.setUserIdx(6);
				noticeVO.setNoticeTitle(i + "번째 공지글");
				noticeVO.setNoticeType("N");
				noticeVO.setNoticeDesc(i + "번째 공지글 내용입니다.");
				
				noticeService.insertNotice(noticeVO);
			}
			mockMvc.perform(get("/main/notice"))
					.andExpect(status().isOk())
					.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
