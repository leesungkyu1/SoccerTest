package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.web.servlet.MvcResult;
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
	@DisplayName("공지사항 추가")
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
//					new BasicNameValuePair("noticeTitle", noticeVO.getNoticeTitle()), // 특이사항 : title - not null
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
				if ( i % 2 == 0) {
					noticeVO.setNoticeType("N");					
				} else {
					noticeVO.setNoticeType("Y");
				}
				noticeVO.setNoticeDesc(i + "번째 공지글 내용입니다.");
				
				noticeService.insertNotice(noticeVO);
			}
			
			UrlEncodedFormEntity notice = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("userIdx", String.valueOf(6))
			), "UTF-8");
			System.out.println("진입전 =");
			mockMvc.perform(get("/main/notice")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(EntityUtils.toString(notice)))
					.andExpect(status().isOk())
					.andDo(print());
			System.out.println("진입후 =");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 세부사항 출력 테스트 성공 메서드
	@Test
	@Transactional
	void selectNoticeDetailTestOk() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
			noticeVO.setNoticeTitle("디테일 확인 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("디테일 확인 공지글 내용입니다.");
			
			noticeService.insertNotice(noticeVO);
			
			int noticeIdx = noticeVO.getNoticeIdx();
			System.out.println("noticeVO-noticeIdx : " + noticeVO.getNoticeIdx());
			
			System.out.println("진입전 =");
			MvcResult result = mockMvc.perform(get("/main/notice/" + noticeIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content("" + noticeIdx))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			
			System.out.println("==============================");
			System.out.println("result : " + result.getResponse().getContentAsString());
			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 세부사항 출력 실패 테스트 ( 미완 - 삭제메서드 후 구현)
	@Test
	@Transactional
	void selectNoticeDetailTestFail() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
			noticeVO.setNoticeTitle("디테일 확인 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("디테일 확인 공지글 내용입니다.");
			
			noticeService.insertNotice(noticeVO);
			
			int noticeIdx = noticeVO.getNoticeIdx();
			System.out.println("noticeVO-noticeIdx : " + noticeVO.getNoticeIdx());
			
			// TODO 방금 만들었던 공지사항 글을 삭제하는 메서드 필요
			
			// TODO 삭제된 글을 들어가려고 하면 예외가 발생해야 함
			System.out.println("진입전 =");
			MvcResult result = mockMvc.perform(get("/main/notice/" + noticeIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content("" + noticeIdx))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			
			System.out.println("==============================");
			System.out.println("result : " + result.getResponse().getContentAsString());
			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 수정 성공 테스트
	@Test
	@Transactional
	void updateNoticeTestOk() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
			noticeVO.setNoticeTitle("수정 전 확인 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("수정 전 확인 공지글 내용입니다.");
			
			noticeService.insertNotice(noticeVO);
			
			int noticeIdx = noticeVO.getNoticeIdx();
//			System.out.println("noticeVO-noticeIdx : " + noticeVO.getNoticeIdx());
			
			NoticeVO beforeupdateVO = noticeService.selectNoticeDetail(noticeIdx);
			
			System.out.println("beforeupdateVO - noticeIdx : " + beforeupdateVO.getNoticeIdx());
			System.out.println("beforeupdateVO - userIdx : " + beforeupdateVO.getUserIdx());
			System.out.println("beforeupdateVO - noticeTitle : " + beforeupdateVO.getNoticeTitle());
			System.out.println("beforeupdateVO - noticeType : " + beforeupdateVO.getNoticeType());
			System.out.println("beforeupdateVO - noticeDesc : " + beforeupdateVO.getNoticeDesc());
			
			UrlEncodedFormEntity notice = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("noticeIdx", String.valueOf(noticeIdx)),
					new BasicNameValuePair("userIdx", String.valueOf(noticeVO.getUserIdx())),
					new BasicNameValuePair("noticeTitle", "수정 후 확인 공지글"),
					new BasicNameValuePair("noticeType", noticeVO.getNoticeType()),
					new BasicNameValuePair("noticeDesc", "수정 후 확인 공지글 내용입니다.")
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			MvcResult result = mockMvc.perform(put("/main/notice/" + noticeIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(EntityUtils.toString(notice)))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			System.out.println("==========컨트롤러 진입 이후=========");
			
			NoticeVO resultVO = noticeService.selectNoticeDetail(noticeIdx);
			System.out.println("resultVO - noticeIdx : " + resultVO.getNoticeIdx());
			System.out.println("resultVO - userIdx : " + resultVO.getUserIdx());
			System.out.println("resultVO - noticeTitle : " + resultVO.getNoticeTitle());
			System.out.println("resultVO - noticeType : " + resultVO.getNoticeType());
			System.out.println("resultVO - noticeDesc : " + resultVO.getNoticeDesc());
			
//			System.out.println("==============================");
//			System.out.println("result : " + result.getResponse().getContentAsString());
//			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 수정 실패 테스트
	@Test
	@Transactional
	void updateNoticeTestFail() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
			noticeVO.setNoticeTitle("수정 전 확인 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("수정 전 확인 공지글 내용입니다.");
			
			noticeService.insertNotice(noticeVO);
			
			int noticeIdx = noticeVO.getNoticeIdx();
//			System.out.println("noticeVO-noticeIdx : " + noticeVO.getNoticeIdx());
			
			NoticeVO beforeupdateVO = noticeService.selectNoticeDetail(noticeIdx);
			
			System.out.println("beforeupdateVO - noticeIdx : " + beforeupdateVO.getNoticeIdx());
			System.out.println("beforeupdateVO - userIdx : " + beforeupdateVO.getUserIdx());
			System.out.println("beforeupdateVO - noticeTitle : " + beforeupdateVO.getNoticeTitle());
			System.out.println("beforeupdateVO - noticeType : " + beforeupdateVO.getNoticeType());
			System.out.println("beforeupdateVO - noticeDesc : " + beforeupdateVO.getNoticeDesc());
			
			UrlEncodedFormEntity notice = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("noticeIdx", String.valueOf(noticeIdx)),
					new BasicNameValuePair("userIdx", String.valueOf(noticeVO.getUserIdx())),
					new BasicNameValuePair("noticeTitle", null), // 특이사항 : title - not null
					new BasicNameValuePair("noticeType", noticeVO.getNoticeType()),
					new BasicNameValuePair("noticeDesc", "수정 후 확인 공지글 내용입니다.")
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			MvcResult result = mockMvc.perform(put("/main/notice/" + noticeIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(EntityUtils.toString(notice)))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			System.out.println("==========컨트롤러 진입 이후=========");
			
			NoticeVO resultVO = noticeService.selectNoticeDetail(noticeIdx);
			System.out.println("resultVO - noticeIdx : " + resultVO.getNoticeIdx());
			System.out.println("resultVO - userIdx : " + resultVO.getUserIdx());
			System.out.println("resultVO - noticeTitle : " + resultVO.getNoticeTitle());
			System.out.println("resultVO - noticeType : " + resultVO.getNoticeType());
			System.out.println("resultVO - noticeDesc : " + resultVO.getNoticeDesc());
			
//			System.out.println("==============================");
//			System.out.println("result : " + result.getResponse().getContentAsString());
//			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 삭제 성공 테스트
	@Test
	@Transactional
	void deleteNoticeTestOk() throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			noticeVO.setUserIdx(6);
			noticeVO.setNoticeTitle("삭제 전 확인 공지글");
			noticeVO.setNoticeType("N");
			noticeVO.setNoticeDesc("삭제 전 확인 공지글 내용입니다.");
			
			noticeService.insertNotice(noticeVO);
			
			int noticeIdx = noticeVO.getNoticeIdx();
//			System.out.println("noticeVO-noticeIdx : " + noticeVO.getNoticeIdx());
			
			NoticeVO beforeupdateVO = noticeService.selectNoticeDetail(noticeIdx);
			
			System.out.println("beforeupdateVO - noticeIdx : " + beforeupdateVO.getNoticeIdx());
			System.out.println("beforeupdateVO - userIdx : " + beforeupdateVO.getUserIdx());
			System.out.println("beforeupdateVO - noticeTitle : " + beforeupdateVO.getNoticeTitle());
			System.out.println("beforeupdateVO - noticeType : " + beforeupdateVO.getNoticeType());
			System.out.println("beforeupdateVO - noticeDesc : " + beforeupdateVO.getNoticeDesc());
			
//			UrlEncodedFormEntity notice = new UrlEncodedFormEntity(Arrays.asList(
//					new BasicNameValuePair("noticeIdx", String.valueOf(noticeIdx)),
//					new BasicNameValuePair("userIdx", String.valueOf(noticeVO.getUserIdx())),
//					new BasicNameValuePair("noticeTitle", "수정 후 확인 공지글"),
//					new BasicNameValuePair("noticeType", noticeVO.getNoticeType()),
//					new BasicNameValuePair("noticeDesc", "수정 후 확인 공지글 내용입니다.")
//			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			MvcResult result = mockMvc.perform(delete("/main/notice/" + noticeIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content("" + noticeIdx))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			System.out.println("==========컨트롤러 진입 이후=========");
			
			NoticeVO resultVO = noticeService.selectNoticeDetail(noticeIdx);
			System.out.println("resultVO - noticeIdx : " + resultVO.getNoticeIdx());
			System.out.println("resultVO - userIdx : " + resultVO.getUserIdx());
			System.out.println("resultVO - noticeTitle : " + resultVO.getNoticeTitle());
			System.out.println("resultVO - noticeType : " + resultVO.getNoticeType());
			System.out.println("resultVO - noticeDesc : " + resultVO.getNoticeDesc());
			
//			System.out.println("==============================");
//			System.out.println("result : " + result.getResponse().getContentAsString());
//			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
