package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.soccer.web.channel.board.controller.ChannelBoardController;
import com.soccer.web.channel.board.service.ChannelBoardService;
import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.notice.controller.NoticeController;
import com.soccer.web.notice.service.NoticeService;
import com.soccer.web.notice.vo.NoticeVO;

@SpringBootTest
@AutoConfigureMockMvc
public class ChannelBoardTestController {

	@Autowired
	ChannelBoardController channelBoardController;
	
	@Autowired
	ChannelBoardService channelBoardService;
	
	@Autowired
	MockMvc mockMvc;
	
	// 채널 게시글 추가 테스트 성공 메서드 (완료)
	@Test
	@Transactional
	@DisplayName("채널 게시글 추가 성공")
	void insertChannelBoardTestOK() throws Exception {
		try {
			ChannelBoardVO channelBoardVO = new ChannelBoardVO();
			channelBoardVO.setMemberIdx(2);
			channelBoardVO.setChannelIdx(1);
			channelBoardVO.setChannelBoardTitle("첫번째 채널 게시글");
			channelBoardVO.setChannelBoardType("N");
			channelBoardVO.setChannelBoardDesc("첫번째 채널 게시글 내용입니다.");
			
			UrlEncodedFormEntity channelBoard = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("memberIdx", String.valueOf(channelBoardVO.getMemberIdx())),
					new BasicNameValuePair("channelIdx", String.valueOf(channelBoardVO.getChannelIdx())),
					new BasicNameValuePair("channelBoardTitle", channelBoardVO.getChannelBoardTitle()),
					new BasicNameValuePair("channelBoardType", channelBoardVO.getChannelBoardType()),
					new BasicNameValuePair("channelBoardDesc", channelBoardVO.getChannelBoardDesc())
			), "UTF-8");
			
			mockMvc.perform(post("/channel/board/" + channelBoardVO.getChannelIdx())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(EntityUtils.toString(channelBoard)))
					.andExpect(status().isOk())
					.andDo(print());
			
//			int channelBoardIdx = channelBoardVO.getChannelBoardIdx();
//			System.out.println("EntityUtils.toString(channelBoard) : " + EntityUtils.toString(channelBoard));
//			ChannelBoardVO resultVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			List<ChannelBoardVO> channelBoardVOList = channelBoardService.selectChannelBoardList(channelBoardVO);
			// 더미데이터가 없는 상태에서 추가하므로 첫번째만 있음
			ChannelBoardVO resultVO = channelBoardVOList.get(0);
			
			System.out.println("resultVO - channelBoardIdx : " + resultVO.getChannelBoardIdx());
			System.out.println("resultVO - memberIdx : " + resultVO.getMemberIdx());
			System.out.println("resultVO - channelIdx : " + resultVO.getChannelIdx());
			System.out.println("resultVO - channelBoardTitle : " + resultVO.getChannelBoardTitle());
			System.out.println("resultVO - channelBoardType : " + resultVO.getChannelBoardType());
			System.out.println("resultVO - channelBoardDesc : " + resultVO.getChannelBoardDesc());
			System.out.println("resultVO - channelBoardDate : " + resultVO.getChannelBoardDate());
			System.out.println("resultVO - memberNick : " + resultVO.getMemberNick());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 게시글 추가 테스트 실패 메서드 (완료)
	@Test
	@Transactional
	@DisplayName("채널 게시글 추가 실패")
	void insertChannelBoardTestFail() throws Exception {
		try {
			ChannelBoardVO channelBoardVO = new ChannelBoardVO();
			channelBoardVO.setMemberIdx(2);
			channelBoardVO.setChannelIdx(1);
			channelBoardVO.setChannelBoardTitle("첫번째 채널 게시글");
			channelBoardVO.setChannelBoardType("N");
			channelBoardVO.setChannelBoardDesc("첫번째 채널 게시글 내용입니다.");
			
			UrlEncodedFormEntity channelBoard = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("memberIdx", String.valueOf(channelBoardVO.getMemberIdx())),
					new BasicNameValuePair("channelIdx", String.valueOf(channelBoardVO.getChannelIdx())),
//					new BasicNameValuePair("channelBoardTitle", channelBoardVO.getChannelBoardTitle()), // 특이사항 : channelBoardTitle - not null
					new BasicNameValuePair("channelBoardType", channelBoardVO.getChannelBoardType()),
					new BasicNameValuePair("channelBoardDesc", channelBoardVO.getChannelBoardDesc())
			), "UTF-8");
			
			mockMvc.perform(post("/channel/board/" + channelBoardVO.getChannelIdx())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(EntityUtils.toString(channelBoard)))
					.andExpect(status().isOk())
					.andDo(print());
			
//			int channelBoardIdx = channelBoardVO.getChannelBoardIdx();
//			System.out.println("EntityUtils.toString(channelBoard) : " + EntityUtils.toString(channelBoard));
//			ChannelBoardVO resultVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
//			List<ChannelBoardVO> channelBoardVOList = channelBoardService.selectChannelBoardList(channelBoardVO);
//			ChannelBoardVO resultVO = channelBoardVOList.get(0);
//			
//			System.out.println("resultVO - channelBoardIdx : " + resultVO.getChannelBoardIdx());
//			System.out.println("resultVO - memberIdx : " + resultVO.getMemberIdx());
//			System.out.println("resultVO - channelIdx : " + resultVO.getChannelIdx());
//			System.out.println("resultVO - channelBoardTitle : " + resultVO.getChannelBoardTitle());
//			System.out.println("resultVO - channelBoardType : " + resultVO.getChannelBoardType());
//			System.out.println("resultVO - channelBoardDesc : " + resultVO.getChannelBoardDesc());
//			System.out.println("resultVO - channelBoardDate : " + resultVO.getChannelBoardDate());
//			System.out.println("resultVO - memberNick : " + resultVO.getMemberNick());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 게시글 리스트 출력 테스트 성공 메서드 (완료)
	@Test
	@Transactional
	void selectChannelBoardListTestOK() throws Exception {
		try {
			for (int i = 1; i <= 12; i++) {
				ChannelBoardVO channelBoardVO = new ChannelBoardVO();
				channelBoardVO.setMemberIdx(2);
				channelBoardVO.setChannelIdx(1);
				channelBoardVO.setChannelBoardTitle(i + "번째 채널 게시글");
				if ( i % 2 == 0) {
					channelBoardVO.setChannelBoardType("N");					
				} else {
					channelBoardVO.setChannelBoardType("Y");
				}
				channelBoardVO.setChannelBoardDesc(i + "번째 채널 게시글 내용입니다.");
				
				channelBoardService.insertChannelBoard(channelBoardVO);
			}
			
			UrlEncodedFormEntity channelBoard = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("memberIdx", String.valueOf(2)),
					new BasicNameValuePair("channelIdx", String.valueOf(1))
			), "UTF-8");
			System.out.println("진입전 =");
			mockMvc.perform(get("/channel/board/" + 1)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(EntityUtils.toString(channelBoard)))
					.andExpect(status().isOk())
					.andDo(print());
			System.out.println("진입후 =");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 게시글 세부사항 출력 테스트 성공 메서드 (완료)
	@Test
	@Transactional
	void selectChannelBoardDetailTestOk() throws Exception {
		try {
			ChannelBoardVO channelBoardVO = new ChannelBoardVO();
			channelBoardVO.setMemberIdx(2);
			channelBoardVO.setChannelIdx(1);
			channelBoardVO.setChannelBoardTitle("디테일 확인용 채널 게시글");
			channelBoardVO.setChannelBoardType("N");
			channelBoardVO.setChannelBoardDesc("디테일 확인용 채널 게시글 내용입니다.");
			
			channelBoardService.insertChannelBoard(channelBoardVO);
			
			int channelIdx = channelBoardVO.getChannelIdx();
			int channelBoardIdx = channelBoardVO.getChannelBoardIdx();
			
			System.out.println("==========컨트롤러 진입 이전=========");
			MvcResult result = mockMvc.perform(get("/channel/board/" + channelIdx + "/" + channelBoardIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content("" + channelBoardIdx))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			System.out.println("==========컨트롤러 진입 이후=========");
			
//			System.out.println("==============================");
//			System.out.println("result : " + result.getResponse().getContentAsString());
//			System.out.println("==============================");
			
			ChannelBoardVO resultVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			
			System.out.println("ㅇㅇㅇㅇㅇㅇ 결과 확인 ㅇㅇㅇㅇㅇㅇㅇ");
			System.out.println("resultVO - channelBoardIdx : " + resultVO.getChannelBoardIdx());
			System.out.println("resultVO - memberIdx : " + resultVO.getMemberIdx());
			System.out.println("resultVO - channelIdx : " + resultVO.getChannelIdx());
			System.out.println("resultVO - channelBoardTitle : " + resultVO.getChannelBoardTitle());
			System.out.println("resultVO - channelBoardType : " + resultVO.getChannelBoardType());
			System.out.println("resultVO - channelBoardDesc : " + resultVO.getChannelBoardDesc());
			System.out.println("resultVO - channelBoardDate : " + resultVO.getChannelBoardDate());
			System.out.println("resultVO - memberNick : " + resultVO.getMemberNick());
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
	
	// 채널 게시글 수정 성공 테스트
	@Test
	@Transactional
	void updateChannelBoardTestOk() throws Exception {
		try {
			ChannelBoardVO channelBoardVO = new ChannelBoardVO();
			channelBoardVO.setMemberIdx(2);
			channelBoardVO.setChannelIdx(1);
			channelBoardVO.setChannelBoardTitle("수정 전 확인용 채널 게시글");
			channelBoardVO.setChannelBoardType("N");
			channelBoardVO.setChannelBoardDesc("수정 전 확인용 채널 게시글 내용입니다.");
			
			channelBoardService.insertChannelBoard(channelBoardVO);
			
			int channelBoardIdx = channelBoardVO.getChannelBoardIdx();
			
			ChannelBoardVO beforeUpdateVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			
			System.out.println("beforeUpdateVO - channelBoardIdx : " + beforeUpdateVO.getChannelBoardIdx());
			System.out.println("beforeUpdateVO - memberIdx : " + beforeUpdateVO.getMemberIdx());
			System.out.println("beforeUpdateVO - channelIdx : " + beforeUpdateVO.getChannelIdx());
			System.out.println("beforeUpdateVO - channelBoardTitle : " + beforeUpdateVO.getChannelBoardTitle());
			System.out.println("beforeUpdateVO - channelBoardType : " + beforeUpdateVO.getChannelBoardType());
			System.out.println("beforeUpdateVO - channelBoardDesc : " + beforeUpdateVO.getChannelBoardDesc());
			System.out.println("beforeUpdateVO - channelBoardDate : " + beforeUpdateVO.getChannelBoardDate());
			System.out.println("beforeUpdateVO - memberNick : " + beforeUpdateVO.getMemberNick());
			
			UrlEncodedFormEntity channelBoard = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelBoardIdx", String.valueOf(channelBoardIdx)),
					new BasicNameValuePair("memberIdx", String.valueOf(beforeUpdateVO.getMemberIdx())),
					new BasicNameValuePair("channelIdx", String.valueOf(beforeUpdateVO.getChannelIdx())),
					new BasicNameValuePair("channelBoardTitle", "수정 후 확인용 채널 게시글"),
					new BasicNameValuePair("channelBoardType", "Y"),
					new BasicNameValuePair("channelBoardDesc", "수정 후 확인용 채널 게시글 내용입니다.")
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			MvcResult result = mockMvc.perform(put("/channel/board/" + channelBoardVO.getChannelIdx() + "/" + channelBoardIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(EntityUtils.toString(channelBoard)))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			System.out.println("==========컨트롤러 진입 이후=========");
			
			ChannelBoardVO resultVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			
			System.out.println("ㅇㅇㅇㅇㅇㅇ 결과 확인 ㅇㅇㅇㅇㅇㅇㅇ");
			System.out.println("resultVO - channelBoardIdx : " + resultVO.getChannelBoardIdx());
			System.out.println("resultVO - memberIdx : " + resultVO.getMemberIdx());
			System.out.println("resultVO - channelIdx : " + resultVO.getChannelIdx());
			System.out.println("resultVO - channelBoardTitle : " + resultVO.getChannelBoardTitle());
			System.out.println("resultVO - channelBoardType : " + resultVO.getChannelBoardType());
			System.out.println("resultVO - channelBoardDesc : " + resultVO.getChannelBoardDesc());
			System.out.println("resultVO - channelBoardDate : " + resultVO.getChannelBoardDate());
			System.out.println("resultVO - memberNick : " + resultVO.getMemberNick());
			
//			System.out.println("==============================");
//			System.out.println("result : " + result.getResponse().getContentAsString());
//			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 게시글 수정 실패 테스트
	@Test
	@Transactional
	void updateChannelBoardTestFail() throws Exception {
		try {
			ChannelBoardVO channelBoardVO = new ChannelBoardVO();
			channelBoardVO.setMemberIdx(2);
			channelBoardVO.setChannelIdx(1);
			channelBoardVO.setChannelBoardTitle("수정 전 확인용 채널 게시글");
			channelBoardVO.setChannelBoardType("N");
			channelBoardVO.setChannelBoardDesc("수정 전 확인용 채널 게시글 내용입니다.");
			
			channelBoardService.insertChannelBoard(channelBoardVO);
			
			int channelBoardIdx = channelBoardVO.getChannelBoardIdx();
			
			ChannelBoardVO beforeUpdateVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			
			System.out.println("beforeUpdateVO - channelBoardIdx : " + beforeUpdateVO.getChannelBoardIdx());
			System.out.println("beforeUpdateVO - memberIdx : " + beforeUpdateVO.getMemberIdx());
			System.out.println("beforeUpdateVO - channelIdx : " + beforeUpdateVO.getChannelIdx());
			System.out.println("beforeUpdateVO - channelBoardTitle : " + beforeUpdateVO.getChannelBoardTitle());
			System.out.println("beforeUpdateVO - channelBoardType : " + beforeUpdateVO.getChannelBoardType());
			System.out.println("beforeUpdateVO - channelBoardDesc : " + beforeUpdateVO.getChannelBoardDesc());
			System.out.println("beforeUpdateVO - channelBoardDate : " + beforeUpdateVO.getChannelBoardDate());
			System.out.println("beforeUpdateVO - memberNick : " + beforeUpdateVO.getMemberNick());
			
			UrlEncodedFormEntity channelBoard = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelBoardIdx", String.valueOf(channelBoardIdx)),
					new BasicNameValuePair("memberIdx", String.valueOf(beforeUpdateVO.getMemberIdx())),
					new BasicNameValuePair("channelIdx", String.valueOf(beforeUpdateVO.getChannelIdx())),
					new BasicNameValuePair("channelBoardTitle", null), // 특이사항 : channelBoardTitle - not null
					new BasicNameValuePair("channelBoardType", "Y"),
					new BasicNameValuePair("channelBoardDesc", "수정 후 확인용 채널 게시글 내용입니다.")
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			MvcResult result = mockMvc.perform(put("/channel/board/" + channelBoardVO.getChannelIdx() + "/" + channelBoardIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(EntityUtils.toString(channelBoard)))
							.andExpect(status().isOk())
							.andDo(print())
							.andReturn();
			System.out.println("==========컨트롤러 진입 이후=========");
			
			ChannelBoardVO resultVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			
			System.out.println("ㅇㅇㅇㅇㅇㅇ 결과 확인 ㅇㅇㅇㅇㅇㅇㅇ");
			System.out.println("resultVO - channelBoardIdx : " + resultVO.getChannelBoardIdx());
			System.out.println("resultVO - memberIdx : " + resultVO.getMemberIdx());
			System.out.println("resultVO - channelIdx : " + resultVO.getChannelIdx());
			System.out.println("resultVO - channelBoardTitle : " + resultVO.getChannelBoardTitle());
			System.out.println("resultVO - channelBoardType : " + resultVO.getChannelBoardType());
			System.out.println("resultVO - channelBoardDesc : " + resultVO.getChannelBoardDesc());
			System.out.println("resultVO - channelBoardDate : " + resultVO.getChannelBoardDate());
			System.out.println("resultVO - memberNick : " + resultVO.getMemberNick());
			
//			System.out.println("==============================");
//			System.out.println("result : " + result.getResponse().getContentAsString());
//			System.out.println("==============================");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 공지사항 삭제 성공 테스트 ------ 여기부터 할 차례
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
