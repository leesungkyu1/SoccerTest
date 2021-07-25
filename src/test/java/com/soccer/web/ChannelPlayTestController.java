package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.soccer.web.channel.play.controller.ChannelPlayController;
import com.soccer.web.channel.play.dao.ChannelPlayMapper;
import com.soccer.web.channel.play.service.ChannelPlayService;
import com.soccer.web.channel.play.service.TeamPlayerService;
import com.soccer.web.channel.play.vo.ChannelPlayGoalVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;

@SpringBootTest
@AutoConfigureMockMvc
public class ChannelPlayTestController { // 영상 제외

	@Autowired
	ChannelPlayController channelPlayController;
	
	@Autowired
	ChannelPlayService channelPlayService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	TeamPlayerService teamPlayerService;
	
	@Autowired
	ChannelPlayMapper channelPlayMapper;
	
	// 채널 영상글 리스트 출력 성공 테스트 (완료)
	@Test
	@Transactional
	void selectChannelPlayListTestOk() throws Exception {
		try {
			for (int i = 1; i <= 12; i++) {
				ChannelPlayVO channelPlayVO = new ChannelPlayVO();
				channelPlayVO.setMemberIdx(2);
				channelPlayVO.setChannelIdx(1);
				channelPlayVO.setChannelPlayTitle(i + "번째 영상 게시물");
				channelPlayVO.setChannelPlayImage(i + "번째 이미지");
				channelPlayVO.setChannelPlayVideo(i + "번째 비디오");
				if (i % 2 == 0) {
					channelPlayVO.setChannelPlayStep("ok");
				} else {
					channelPlayVO.setChannelPlayStep("applying");
				}
				channelPlayVO.setChannelPlayHomeFormation("442");
				channelPlayVO.setChannelPlayAwayFormation("433");
				
				channelPlayService.insertChannelPlay(channelPlayVO);
			}
			
//			ChannelPlayVO tmpVO = new ChannelPlayVO();
//			tmpVO.setChannelIdx(1);
//			List<ChannelPlayVO> channelPlayVOList = channelPlayService.selectChannelPlayList(tmpVO);
//			for (ChannelPlayVO item : channelPlayVOList) {
//				System.out.println("item - channelPlayTitle : " + item.getChannelPlayTitle());
//			}
			
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(1))
			), "UTF-8");
			
			int channelIdx = 1;
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/" + channelIdx)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(channelPlay)))
						.andExpect(status().isOk())
//						.andExpect(model().attributeExists("channelPlayList"))
//						.andExpect(model().attributeExists("message"))
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 영상글 세부사항 출력 성공 테스트 (완료)
	@Test
	@Transactional
	void selectChannelPlayDetailTestOk() throws Exception {
		try {
			// play 글 하나 생성
			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
			channelPlayVO.setMemberIdx(2);
			channelPlayVO.setChannelIdx(1);
			channelPlayVO.setChannelPlayTitle("영상 게시물 디테일 테스트");
			channelPlayVO.setChannelPlayImage("영상 게시물 디테일 테스트 이미지");
			channelPlayVO.setChannelPlayVideo("영상 게시물 디테일 테스트 비디오");
			channelPlayVO.setChannelPlayStep("ok");
			channelPlayVO.setChannelPlayHomeFormation("442");
			channelPlayVO.setChannelPlayAwayFormation("433");
			
			channelPlayService.insertChannelPlay(channelPlayVO);
			
			int channelIdx = channelPlayVO.getChannelIdx();
			int channelPlayIdx = channelPlayVO.getChannelPlayIdx();
			
			System.out.println("channelPlayIdx : " + channelPlayIdx);
			
			ChannelPlayVO beforeSelectDetailVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			
			System.out.println("beforeSelectDetailVO - channelPlayIdx : " + beforeSelectDetailVO.getChannelPlayIdx());
			System.out.println("beforeSelectDetailVO - memberIdx : " + beforeSelectDetailVO.getMemberIdx());
			System.out.println("beforeSelectDetailVO - channelIdx : " + beforeSelectDetailVO.getChannelIdx());
			System.out.println("beforeSelectDetailVO - channelPlayTitle : " + beforeSelectDetailVO.getChannelPlayTitle());
			System.out.println("beforeSelectDetailVO - channelPlaydate : " + beforeSelectDetailVO.getChannelPlayDate());
			System.out.println("beforeSelectDetailVO - channelPlayImage : " + beforeSelectDetailVO.getChannelPlayImage());
			System.out.println("beforeSelectDetailVO - channelPlayVideo : " + beforeSelectDetailVO.getChannelPlayVideo());
			System.out.println("beforeSelectDetailVO - channelPlayStep : " + beforeSelectDetailVO.getChannelPlayStep());
			System.out.println("beforeSelectDetailVO - channelPlayHomeFormation : " + beforeSelectDetailVO.getChannelPlayHomeFormation());
			System.out.println("beforeSelectDetailVO - channelPlayAwayFormation : " + beforeSelectDetailVO.getChannelPlayAwayFormation());
			System.out.println("beforeSelectDetailVO - memberNick : " + beforeSelectDetailVO.getMemberNick());
			
			// case 1. 팀이 지정되어 있지 않은 경우 (case 2를 주석처리 하기)
			
			
			// case 2. 팀이 지정되어 있는 경우
			TeamVO homeTeamVO = new TeamVO();
			homeTeamVO.setChannelPlayIdx(channelPlayIdx);
			homeTeamVO.setChannelIdx(channelIdx);
			homeTeamVO.setTeamName("HOME Team");
			homeTeamVO.setTeamType("H");
			
			TeamVO awayTeamVO = new TeamVO();
			awayTeamVO.setChannelPlayIdx(channelPlayIdx);
			awayTeamVO.setChannelIdx(channelIdx);
			awayTeamVO.setTeamName("AWAY Team");
			awayTeamVO.setTeamType("A");
			
			channelPlayMapper.insertTeam(homeTeamVO);
			channelPlayMapper.insertTeam(awayTeamVO);
			
			int homeTeamIdx = homeTeamVO.getTeamIdx();
			int awayTeamIdx = awayTeamVO.getTeamIdx();
			
			System.out.println("homeTeamIdx : " + homeTeamIdx);
			System.out.println("awayTeamIdx : " + awayTeamIdx);
			
			for (int i = 0; i <= 10; i++) {
				TeamPlayerVO teamPlayerVO = new TeamPlayerVO();
				teamPlayerVO.setUserIdx(6);
				teamPlayerVO.setTeamIdx(homeTeamIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				if (i == 0) {
					teamPlayerVO.setTeamPlayerPosition("G");
				} else if (0 < i && i <= 4) {
					teamPlayerVO.setTeamPlayerPosition("D");
				} else if (4 < i && i <= 8) {
					teamPlayerVO.setTeamPlayerPosition("M");
				} else {
					teamPlayerVO.setTeamPlayerPosition("F");
				}
				teamPlayerVO.setTeamPlayerFormationNumber(i);
				teamPlayerVO.setTeamPlayerName(i + 1 + "번째 Home team 멤버");
				
				HashMap<String, String> teamPlayerMap = new HashMap<>();
				teamPlayerMap.put("userIdx", Integer.toString(teamPlayerVO.getUserIdx()));
				teamPlayerMap.put("teamIdx", Integer.toString(teamPlayerVO.getTeamIdx()));
				teamPlayerMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
				teamPlayerMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
				
				teamPlayerService.insertTeamPlayer(teamPlayerMap);
				
//				System.out.println("teamPlayerIdx : " + String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				int teamPlayerIdx = Integer.parseInt(String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				
				PlayresultVO playresultVO = new PlayresultVO();
				playresultVO.setChannelPlayIdx(channelPlayIdx);
				playresultVO.setTeamIdx(homeTeamIdx);
				playresultVO.setTeamPlayerIdx(teamPlayerIdx);
				playresultVO.setPlayresultTotaltackle(i);
				playresultVO.setPlayresultSuccesstackle(i);
				playresultVO.setPlayresultTotalcross(i);
				playresultVO.setPlayresultSuccesscross(i);
				playresultVO.setPlayresultTotalcornerkick(i);
				playresultVO.setPlayresultSuccesscornerkick(i);
				playresultVO.setPlayresultTotalfreekick(i);
				playresultVO.setPlayresultSuccessfreekick(i);
				playresultVO.setPlayresultTotalshooting(i);
				playresultVO.setPlayresultSuccessshooting(i);
				playresultVO.setPlayresultTotalassist(i);
				playresultVO.setPlayresultSuccessassist(i);
				playresultVO.setPlayresultTotalpass(i);
				playresultVO.setPlayresultSuccesspass(i);
				playresultVO.setPlayresultTotalcontention(i);
				playresultVO.setPlayresultSuccesscontention(i);
				
				HashMap<String, Integer> playresultMap = new HashMap<>();
				
				playresultMap.put("channelPlayIdx", playresultVO.getChannelPlayIdx());
				playresultMap.put("teamIdx", playresultVO.getTeamIdx());
				playresultMap.put("teamPlayerIdx", playresultVO.getTeamPlayerIdx());
				
				teamPlayerService.insertPlayresult(playresultMap);
				
			}
			
			for (int i = 0; i <= 10; i++) {
				TeamPlayerVO teamPlayerVO = new TeamPlayerVO();
				teamPlayerVO.setUserIdx(6);
				teamPlayerVO.setTeamIdx(awayTeamIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				if (i == 0) {
					teamPlayerVO.setTeamPlayerPosition("G");
				} else if (0 < i && i <= 4) {
					teamPlayerVO.setTeamPlayerPosition("D");
				} else if (4 < i && i <= 7) {
					teamPlayerVO.setTeamPlayerPosition("M");
				} else {
					teamPlayerVO.setTeamPlayerPosition("F");
				}
				teamPlayerVO.setTeamPlayerFormationNumber(i);
				teamPlayerVO.setTeamPlayerName(i + 1 + "번째 Away team 멤버");

				HashMap<String, String> teamPlayerMap = new HashMap<>();
				teamPlayerMap.put("userIdx", Integer.toString(teamPlayerVO.getUserIdx()));
				teamPlayerMap.put("teamIdx", Integer.toString(teamPlayerVO.getTeamIdx()));
				teamPlayerMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
				teamPlayerMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
				
				teamPlayerService.insertTeamPlayer(teamPlayerMap);
				int teamPlayerIdx = Integer.parseInt(String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				
				PlayresultVO playresultVO = new PlayresultVO();
				playresultVO.setChannelPlayIdx(channelPlayIdx);
				playresultVO.setTeamIdx(awayTeamIdx);
				playresultVO.setTeamPlayerIdx(teamPlayerIdx);
				playresultVO.setPlayresultTotaltackle(i);
				playresultVO.setPlayresultSuccesstackle(i);
				playresultVO.setPlayresultTotalcross(i);
				playresultVO.setPlayresultSuccesscross(i);
				playresultVO.setPlayresultTotalcornerkick(i);
				playresultVO.setPlayresultSuccesscornerkick(i);
				playresultVO.setPlayresultTotalfreekick(i);
				playresultVO.setPlayresultSuccessfreekick(i);
				playresultVO.setPlayresultTotalshooting(i);
				playresultVO.setPlayresultSuccessshooting(i);
				playresultVO.setPlayresultTotalassist(i);
				playresultVO.setPlayresultSuccessassist(i);
				playresultVO.setPlayresultTotalpass(i);
				playresultVO.setPlayresultSuccesspass(i);
				playresultVO.setPlayresultTotalcontention(i);
				playresultVO.setPlayresultSuccesscontention(i);
				
				HashMap<String, Integer> playresultMap = new HashMap<>();
				
				playresultMap.put("channelPlayIdx", playresultVO.getChannelPlayIdx());
				playresultMap.put("teamIdx", playresultVO.getTeamIdx());
				playresultMap.put("teamPlayerIdx", playresultVO.getTeamPlayerIdx());
				
				teamPlayerService.insertPlayresult(playresultMap);
			}
			
			// 컨트롤러 확인 작업
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx))
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/" + channelIdx + "/" +channelPlayIdx)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(channelPlay))
						.content("" + channelIdx)
						.content("" + channelPlayIdx))
						.andExpect(status().isOk())
//						.andExpect(model().attributeExists("channelPlayList"))
//						.andExpect(model().attributeExists("message"))
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 영상글 추가 성공 테스트 (완료)
	@Test
	@Transactional
	void insertChannelPlayTestOK() throws Exception {
		try {
			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
			channelPlayVO.setMemberIdx(2);
			channelPlayVO.setChannelIdx(1);
			channelPlayVO.setChannelPlayTitle("영상 게시물 추가 테스트");
			channelPlayVO.setChannelPlayImage("영상 게시물 추가 테스트 이미지");
			channelPlayVO.setChannelPlayVideo("영상 게시물 추가 테스트 비디오");
//			channelPlayVO.setChannelPlayStep("ok");
			channelPlayVO.setChannelPlayHomeFormation("442");
			channelPlayVO.setChannelPlayAwayFormation("433");
			
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("memberIdx", String.valueOf(channelPlayVO.getMemberIdx())),
					new BasicNameValuePair("channelIdx", String.valueOf(channelPlayVO.getChannelIdx())),
					new BasicNameValuePair("channelPlayTitle", channelPlayVO.getChannelPlayTitle()),
					new BasicNameValuePair("channelPlayImage", channelPlayVO.getChannelPlayImage()),
					new BasicNameValuePair("channelPlayVideo", channelPlayVO.getChannelPlayVideo())
					), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(post("/channel/play/" + channelPlayVO.getChannelIdx())
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayVO.getChannelIdx() + "")
							.content(EntityUtils.toString(channelPlay)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 채널 영상글 추가 실패 테스트 (완료)
	@Test
	@Transactional
	void insertChannelPlayTestFail() throws Exception {
		try {
			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
			channelPlayVO.setMemberIdx(2);
			channelPlayVO.setChannelIdx(1);
//			channelPlayVO.setChannelPlayTitle("영상 게시물 추가 테스트"); // 특이사항 : channelPlayTitle - not null
			channelPlayVO.setChannelPlayImage("영상 게시물 추가 테스트 이미지");
			channelPlayVO.setChannelPlayVideo("영상 게시물 추가 테스트 비디오");
//			channelPlayVO.setChannelPlayStep("ok");
			channelPlayVO.setChannelPlayHomeFormation("442");
			channelPlayVO.setChannelPlayAwayFormation("433");
			
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("memberIdx", String.valueOf(channelPlayVO.getMemberIdx())),
					new BasicNameValuePair("channelIdx", String.valueOf(channelPlayVO.getChannelIdx())),
//					new BasicNameValuePair("channelPlayTitle", channelPlayVO.getChannelPlayTitle()), // 특이사항 : channelPlayTitle - not null
					new BasicNameValuePair("channelPlayImage", channelPlayVO.getChannelPlayImage()),
					new BasicNameValuePair("channelPlayVideo", channelPlayVO.getChannelPlayVideo())
					), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(post("/channel/play/" + channelPlayVO.getChannelIdx())
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayVO.getChannelIdx() + "")
							.content(EntityUtils.toString(channelPlay)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 영상 게시글 수정 성공 테스트 (완료)
	@Test
	@Transactional
	void updateChannelPlayTestOk() throws Exception {
		try {
			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
			channelPlayVO.setMemberIdx(2);
			channelPlayVO.setChannelIdx(1);
			channelPlayVO.setChannelPlayTitle("영상 게시물 수정 전 테스트 제목");
			channelPlayVO.setChannelPlayImage("영상 게시물 수정 전 테스트 이미지");
			channelPlayVO.setChannelPlayVideo("영상 게시물 수정 전 테스트 비디오");
			channelPlayVO.setChannelPlayStep("ok");
			channelPlayVO.setChannelPlayHomeFormation("442");
			channelPlayVO.setChannelPlayAwayFormation("433");
			
			channelPlayService.insertChannelPlay(channelPlayVO);
			
			int channelPlayIdx = channelPlayVO.getChannelPlayIdx();
			
			ChannelPlayVO beforeUpdateDetailVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			
			System.out.println("========== 수정 전 추가한 영상 게시물 정보 =========");
			System.out.println("beforeUpdateDetailVO - channelPlayIdx : " + beforeUpdateDetailVO.getChannelPlayIdx());
			System.out.println("beforeUpdateDetailVO - memberIdx : " + beforeUpdateDetailVO.getMemberIdx());
			System.out.println("beforeUpdateDetailVO - channelIdx : " + beforeUpdateDetailVO.getChannelIdx());
			System.out.println("beforeUpdateDetailVO - channelPlayTitle : " + beforeUpdateDetailVO.getChannelPlayTitle());
			System.out.println("beforeUpdateDetailVO - channelPlaydate : " + beforeUpdateDetailVO.getChannelPlayDate());
			System.out.println("beforeUpdateDetailVO - channelPlayImage : " + beforeUpdateDetailVO.getChannelPlayImage());
			System.out.println("beforeUpdateDetailVO - channelPlayVideo : " + beforeUpdateDetailVO.getChannelPlayVideo());
			System.out.println("beforeUpdateDetailVO - channelPlayStep : " + beforeUpdateDetailVO.getChannelPlayStep());
			System.out.println("beforeUpdateDetailVO - channelPlayHomeFormation : " + beforeUpdateDetailVO.getChannelPlayHomeFormation());
			System.out.println("beforeUpdateDetailVO - channelPlayAwayFormation : " + beforeUpdateDetailVO.getChannelPlayAwayFormation());
			System.out.println("beforeUpdateDetailVO - memberNick : " + beforeUpdateDetailVO.getMemberNick());
			System.out.println("=====================================================");
			
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
					new BasicNameValuePair("channelPlayTitle", "영상 게시물 수정 후 테스트 제목"),
					new BasicNameValuePair("channelPlayImage", "영상 게시물 수정 후 테스트 이미지"),
					new BasicNameValuePair("channelPlayVideo", "영상 게시물 수정 후 테스트 비디오")
					), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(put("/channel/play/" + channelPlayVO.getChannelIdx() + "/" + channelPlayIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayVO.getChannelIdx() + "")
							.content(channelPlayIdx + "")
							.content(EntityUtils.toString(channelPlay)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			// 여기서 해당 게시글이 없으면 정상
			List<ChannelPlayVO> channelPlayVOList = channelPlayService.selectChannelPlayList(channelPlayVO);
			System.out.println("========== 수정 후 추가한 영상 게시물 리스트 정보 =========");
			for (ChannelPlayVO item : channelPlayVOList) {
				System.out.println("item - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("item - channelPlayTitle : " + item.getChannelPlayTitle());
			}
			System.out.println("=====================================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 영상 게시글 수정 실패 테스트 (완료)
	@Test
	@Transactional
	void updateChannelPlayTestFail() throws Exception {
		try {
			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
			channelPlayVO.setMemberIdx(2);
			channelPlayVO.setChannelIdx(1);
			channelPlayVO.setChannelPlayTitle("영상 게시물 수정 전 테스트 제목");
			channelPlayVO.setChannelPlayImage("영상 게시물 수정 전 테스트 이미지");
			channelPlayVO.setChannelPlayVideo("영상 게시물 수정 전 테스트 비디오");
			channelPlayVO.setChannelPlayStep("ok");
			channelPlayVO.setChannelPlayHomeFormation("442");
			channelPlayVO.setChannelPlayAwayFormation("433");
			
			channelPlayService.insertChannelPlay(channelPlayVO);
			
			int channelPlayIdx = channelPlayVO.getChannelPlayIdx();
			
			ChannelPlayVO beforeUpdateDetailVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			
			System.out.println("========== 수정 전 추가한 영상 게시물 정보 =========");
			System.out.println("beforeUpdateDetailVO - channelPlayIdx : " + beforeUpdateDetailVO.getChannelPlayIdx());
			System.out.println("beforeUpdateDetailVO - memberIdx : " + beforeUpdateDetailVO.getMemberIdx());
			System.out.println("beforeUpdateDetailVO - channelIdx : " + beforeUpdateDetailVO.getChannelIdx());
			System.out.println("beforeUpdateDetailVO - channelPlayTitle : " + beforeUpdateDetailVO.getChannelPlayTitle());
			System.out.println("beforeUpdateDetailVO - channelPlaydate : " + beforeUpdateDetailVO.getChannelPlayDate());
			System.out.println("beforeUpdateDetailVO - channelPlayImage : " + beforeUpdateDetailVO.getChannelPlayImage());
			System.out.println("beforeUpdateDetailVO - channelPlayVideo : " + beforeUpdateDetailVO.getChannelPlayVideo());
			System.out.println("beforeUpdateDetailVO - channelPlayStep : " + beforeUpdateDetailVO.getChannelPlayStep());
			System.out.println("beforeUpdateDetailVO - channelPlayHomeFormation : " + beforeUpdateDetailVO.getChannelPlayHomeFormation());
			System.out.println("beforeUpdateDetailVO - channelPlayAwayFormation : " + beforeUpdateDetailVO.getChannelPlayAwayFormation());
			System.out.println("beforeUpdateDetailVO - memberNick : " + beforeUpdateDetailVO.getMemberNick());
			System.out.println("=====================================================");
			
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
//					new BasicNameValuePair("channelPlayTitle", "영상 게시물 수정 후 테스트 제목"), // 특이사항 : channelPlayTitle - not null
					new BasicNameValuePair("channelPlayImage", "영상 게시물 수정 후 테스트 이미지"),
					new BasicNameValuePair("channelPlayVideo", "영상 게시물 수정 후 테스트 비디오")
					), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(put("/channel/play/" + channelPlayVO.getChannelIdx() + "/" + channelPlayIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayVO.getChannelIdx() + "")
							.content(channelPlayIdx + "")
							.content(EntityUtils.toString(channelPlay)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			// 여기서 해당 게시글이 있으면 정상 (수정이 되지 않았기 때문에 channelPlayStep이 default로 변경되지 않음)
			List<ChannelPlayVO> channelPlayVOList = channelPlayService.selectChannelPlayList(channelPlayVO);
			System.out.println("========== 수정 후 추가한 영상 게시물 리스트 정보 =========");
			for (ChannelPlayVO item : channelPlayVOList) {
				System.out.println("item - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("item - channelPlayTitle : " + item.getChannelPlayTitle());
			}
			System.out.println("=====================================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 영상 게시물 삭제 성공 테스트
	@Test
	@Transactional
	void deleteChannelPlayTestOk() throws Exception {
		try {
			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
			channelPlayVO.setMemberIdx(2);
			channelPlayVO.setChannelIdx(1);
			channelPlayVO.setChannelPlayTitle("영상 게시물 삭제 전 테스트 제목");
			channelPlayVO.setChannelPlayImage("영상 게시물 삭제 전 테스트 이미지");
			channelPlayVO.setChannelPlayVideo("영상 게시물 삭제 전 테스트 비디오");
			channelPlayVO.setChannelPlayStep("ok");
			channelPlayVO.setChannelPlayHomeFormation("442");
			channelPlayVO.setChannelPlayAwayFormation("433");
			
			channelPlayService.insertChannelPlay(channelPlayVO);
			
			int channelPlayIdx = channelPlayVO.getChannelPlayIdx();
			
			ChannelPlayVO beforeDeleteDetailVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			
			System.out.println("========== 삭제 전 추가한 영상 게시물 정보 =========");
			System.out.println("beforeDeleteDetailVO - channelPlayIdx : " + beforeDeleteDetailVO.getChannelPlayIdx());
			System.out.println("beforeDeleteDetailVO - memberIdx : " + beforeDeleteDetailVO.getMemberIdx());
			System.out.println("beforeDeleteDetailVO - channelIdx : " + beforeDeleteDetailVO.getChannelIdx());
			System.out.println("beforeDeleteDetailVO - channelPlayTitle : " + beforeDeleteDetailVO.getChannelPlayTitle());
			System.out.println("beforeDeleteDetailVO - channelPlaydate : " + beforeDeleteDetailVO.getChannelPlayDate());
			System.out.println("beforeDeleteDetailVO - channelPlayImage : " + beforeDeleteDetailVO.getChannelPlayImage());
			System.out.println("beforeDeleteDetailVO - channelPlayVideo : " + beforeDeleteDetailVO.getChannelPlayVideo());
			System.out.println("beforeDeleteDetailVO - channelPlayStep : " + beforeDeleteDetailVO.getChannelPlayStep());
			System.out.println("beforeDeleteDetailVO - channelPlayHomeFormation : " + beforeDeleteDetailVO.getChannelPlayHomeFormation());
			System.out.println("beforeDeleteDetailVO - channelPlayAwayFormation : " + beforeDeleteDetailVO.getChannelPlayAwayFormation());
			System.out.println("beforeDeleteDetailVO - memberNick : " + beforeDeleteDetailVO.getMemberNick());
			System.out.println("=====================================================");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(delete("/channel/play/" + channelPlayVO.getChannelIdx() + "/" + channelPlayIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(beforeDeleteDetailVO.getChannelIdx() + "")
							.content(beforeDeleteDetailVO.getChannelPlayIdx() + ""))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			// 여기서 해당 게시글이 없으면 정상 (검색 조건 중 step이 ok인 상황과 조건이 없는 상황도 확인해야 함)
			List<ChannelPlayVO> channelPlayVOList = channelPlayService.selectChannelPlayList(channelPlayVO);
			System.out.println("========== 삭제 후 영상 게시물 리스트 정보 =========");
			for (ChannelPlayVO item : channelPlayVOList) {
				System.out.println("item - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("item - channelPlayTitle : " + item.getChannelPlayTitle());
			}
			System.out.println("=====================================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 득점 정보 추가 성공 테스트 (완료)
	@Test
	@Transactional
	void insertGoalTestOk() throws Exception {
		try {
			ChannelPlayGoalVO goalVO = new ChannelPlayGoalVO();
			goalVO.setChannelPlayGoalName("선수");
			goalVO.setChannelPlayGoalTime("21:20");
			goalVO.setChannelPlayGoalType("H");
			goalVO.setChannelPlayIdx(1);
			
			int channelPlayIdx = goalVO.getChannelPlayIdx();
			
			UrlEncodedFormEntity goal = new UrlEncodedFormEntity(Arrays.asList(
							new BasicNameValuePair("channelPlayGoalName", goalVO.getChannelPlayGoalName()),
							new BasicNameValuePair("channelPlayGoalTime", goalVO.getChannelPlayGoalTime()),
							new BasicNameValuePair("channelPlayGoalType", goalVO.getChannelPlayGoalType()),
							new BasicNameValuePair("channelPlayIdx", String.valueOf(goalVO.getChannelPlayIdx()))
							), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(post("/channel/play/goal/" + channelPlayIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayIdx + "")
							.content(EntityUtils.toString(goal)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			List<ChannelPlayGoalVO> goalList = channelPlayService.goalList(channelPlayIdx);
			
			System.out.println("========== 추가 후 골 정보 =========");
			for (ChannelPlayGoalVO item : goalList) {
				System.out.println("goalName : " + item.getChannelPlayGoalName());
				System.out.println("goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("goalType : " + item.getChannelPlayGoalType());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("====================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 득점 정보 추가 실패 테스트 (완료)
	@Test
	@Transactional
	void insertGoalTestFail() throws Exception {
		try {
			ChannelPlayGoalVO goalVO = new ChannelPlayGoalVO();
			goalVO.setChannelPlayGoalName("선수");
			goalVO.setChannelPlayGoalTime("21:20");
			goalVO.setChannelPlayGoalType("H");
			goalVO.setChannelPlayIdx(1);
			
			int channelPlayIdx = goalVO.getChannelPlayIdx();
			
			UrlEncodedFormEntity goal = new UrlEncodedFormEntity(Arrays.asList(
//							new BasicNameValuePair("channelPlayGoalName", goalVO.getChannelPlayGoalName()), // 특이사항 : channelPlayGoalName - not null
							new BasicNameValuePair("channelPlayGoalTime", goalVO.getChannelPlayGoalTime()),
							new BasicNameValuePair("channelPlayGoalType", goalVO.getChannelPlayGoalType()),
							new BasicNameValuePair("channelPlayIdx", String.valueOf(goalVO.getChannelPlayIdx()))
							), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(post("/channel/play/goal/" + channelPlayIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayIdx + "")
							.content(EntityUtils.toString(goal)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			List<ChannelPlayGoalVO> goalList = channelPlayService.goalList(channelPlayIdx);
			
			System.out.println("========== 추가 후 골 정보 =========");
			for (ChannelPlayGoalVO item : goalList) {
				System.out.println("goalName : " + item.getChannelPlayGoalName());
				System.out.println("goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("goalType : " + item.getChannelPlayGoalType());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("====================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 팀 득점 정보 확인 성공 테스트 (완료)
	@Test
	@Transactional
	void goalListTestOk() throws Exception {
		try {
			// 기록이 있는 경우 - 완료
			ChannelPlayGoalVO goalVO1 = new ChannelPlayGoalVO();
			goalVO1.setChannelPlayGoalName("Home 선수1");
			goalVO1.setChannelPlayGoalTime("20:20");
			goalVO1.setChannelPlayGoalType("H");
			goalVO1.setChannelPlayIdx(1);
			
			ChannelPlayGoalVO goalVO2 = new ChannelPlayGoalVO();
			goalVO2.setChannelPlayGoalName("Away 선수1");
			goalVO2.setChannelPlayGoalTime("9:10");
			goalVO2.setChannelPlayGoalType("A");
			goalVO2.setChannelPlayIdx(1);
			
			ChannelPlayGoalVO goalVO3 = new ChannelPlayGoalVO();
			goalVO3.setChannelPlayGoalName("Home 선수2");
			goalVO3.setChannelPlayGoalTime("15:40");
			goalVO3.setChannelPlayGoalType("H");
			goalVO3.setChannelPlayIdx(1);
			
			channelPlayService.insertGoal(goalVO1);
			channelPlayService.insertGoal(goalVO2);
			channelPlayService.insertGoal(goalVO3);
			
			int channelPlayIdx = goalVO1.getChannelPlayIdx();
			
//			// 기록이 아무것도 없는 경우 - 완료
//			int channelPlayIdx = 1;
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/goal/" + channelPlayIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayIdx + ""))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			List<ChannelPlayGoalVO> goalList = channelPlayService.goalList(channelPlayIdx);
			
			// 시간 순서대로 나오는지 확인
			// TODO 시간 issue 발생 (10:00보다 9:00이 늦게 나오는 현상)
			System.out.println("=========== 리스트 정보 ===========");
			for (ChannelPlayGoalVO item : goalList) {
				System.out.println("goalName : " + item.getChannelPlayGoalName());
				System.out.println("goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("goalType : " + item.getChannelPlayGoalType());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("====================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 득점 기록 수정 성공 테스트 (완료)
	@Test
	@Transactional
	void updateGoalTestOk() throws Exception {
		try {
			ChannelPlayGoalVO beforeUpdateGoalVO = new ChannelPlayGoalVO();
			beforeUpdateGoalVO.setChannelPlayGoalName("Home 선수");
			beforeUpdateGoalVO.setChannelPlayGoalTime("21:20");
			beforeUpdateGoalVO.setChannelPlayGoalType("H");
			beforeUpdateGoalVO.setChannelPlayIdx(1);
			
			channelPlayService.insertGoal(beforeUpdateGoalVO);
			
			int channelPlayGoalIdx = 0;
			int channelPlayIdx = beforeUpdateGoalVO.getChannelPlayIdx();
			
			System.out.println("========== 수정 전 골 정보 리스트 ========");
			List<ChannelPlayGoalVO> beforeList = channelPlayService.goalList(beforeUpdateGoalVO.getChannelPlayIdx());
			for (ChannelPlayGoalVO item : beforeList) {
				channelPlayGoalIdx = item.getChannelPlayGoalIdx();
				System.out.println("beforeList - goalName : " + item.getChannelPlayGoalName());
				System.out.println("beforeList - goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("beforeList - goalType : " + item.getChannelPlayGoalType());
				System.out.println("beforeList - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("beforeList - channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("=========================================");
			
			UrlEncodedFormEntity goal = new UrlEncodedFormEntity(Arrays.asList(
							new BasicNameValuePair("channelPlayGoalName", "Away 선수"),
							new BasicNameValuePair("channelPlayGoalTime", "20:20"),
							new BasicNameValuePair("channelPlayGoalType", "A"),
							new BasicNameValuePair("channelPlayGoalIdx", String.valueOf(channelPlayGoalIdx))
							), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(put("/channel/play/goal/" + channelPlayIdx + "/" + channelPlayGoalIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayIdx + "")
							.content(channelPlayGoalIdx + "")
							.content(EntityUtils.toString(goal)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			List<ChannelPlayGoalVO> goalList = channelPlayService.goalList(channelPlayIdx);
			
			System.out.println("========== 수정 후 골 정보 =========");
			for (ChannelPlayGoalVO item : goalList) {
				System.out.println("goalName : " + item.getChannelPlayGoalName());
				System.out.println("goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("goalType : " + item.getChannelPlayGoalType());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("====================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 득점 기록 수정 실패 테스트 (완료)
	@Test
	@Transactional
	void updateGoalTestFail() throws Exception {
		try {
			ChannelPlayGoalVO beforeUpdateGoalVO = new ChannelPlayGoalVO();
			beforeUpdateGoalVO.setChannelPlayGoalName("Home 선수");
			beforeUpdateGoalVO.setChannelPlayGoalTime("21:20");
			beforeUpdateGoalVO.setChannelPlayGoalType("H");
			beforeUpdateGoalVO.setChannelPlayIdx(1);
			
			channelPlayService.insertGoal(beforeUpdateGoalVO);
			
			int channelPlayGoalIdx = 0;
			int channelPlayIdx = beforeUpdateGoalVO.getChannelPlayIdx();
			
			System.out.println("========== 수정 전 골 정보 리스트 ========");
			List<ChannelPlayGoalVO> beforeList = channelPlayService.goalList(beforeUpdateGoalVO.getChannelPlayIdx());
			for (ChannelPlayGoalVO item : beforeList) {
				channelPlayGoalIdx = item.getChannelPlayGoalIdx();
				System.out.println("beforeList - goalName : " + item.getChannelPlayGoalName());
				System.out.println("beforeList - goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("beforeList - goalType : " + item.getChannelPlayGoalType());
				System.out.println("beforeList - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("beforeList - channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("=========================================");
			
			UrlEncodedFormEntity goal = new UrlEncodedFormEntity(Arrays.asList(
//							new BasicNameValuePair("channelPlayGoalName", "Away 선수"), // 특이사항 : channelPlayGoalName - not null
							new BasicNameValuePair("channelPlayGoalTime", "20:20"),
							new BasicNameValuePair("channelPlayGoalType", "A"),
							new BasicNameValuePair("channelPlayGoalIdx", String.valueOf(channelPlayGoalIdx))
							), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(put("/channel/play/goal/" + channelPlayIdx + "/" + channelPlayGoalIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayIdx + "")
							.content(channelPlayGoalIdx + "")
							.content(EntityUtils.toString(goal)))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			List<ChannelPlayGoalVO> goalList = channelPlayService.goalList(channelPlayIdx);
			
			System.out.println("========== 수정 후 골 정보 =========");
			for (ChannelPlayGoalVO item : goalList) {
				System.out.println("goalName : " + item.getChannelPlayGoalName());
				System.out.println("goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("goalType : " + item.getChannelPlayGoalType());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("====================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 득점 기록 삭제 성공 테스트 (완료)
	@Test
	@Transactional
	void deleteGoalTestOk() throws Exception {
		try {
			ChannelPlayGoalVO beforeDeleteGoalVO = new ChannelPlayGoalVO();
			beforeDeleteGoalVO.setChannelPlayGoalName("Home 선수");
			beforeDeleteGoalVO.setChannelPlayGoalTime("21:20");
			beforeDeleteGoalVO.setChannelPlayGoalType("H");
			beforeDeleteGoalVO.setChannelPlayIdx(1);
			
			channelPlayService.insertGoal(beforeDeleteGoalVO);
			
			int channelPlayGoalIdx = 0;
			int channelPlayIdx = beforeDeleteGoalVO.getChannelPlayIdx();
			
			List<ChannelPlayGoalVO> list = channelPlayService.goalList(beforeDeleteGoalVO.getChannelPlayIdx());
			for (ChannelPlayGoalVO item : list) {
				channelPlayGoalIdx = item.getChannelPlayGoalIdx();
			}
			
			ChannelPlayGoalVO goalVO = new ChannelPlayGoalVO();
			goalVO.setChannelPlayGoalName("Home 선수");
			goalVO.setChannelPlayGoalTime("22:20");
			goalVO.setChannelPlayGoalType("H");
			goalVO.setChannelPlayIdx(1);
			
			channelPlayService.insertGoal(goalVO);
			
			System.out.println("========== 삭제 전 골 정보 리스트 ========");
			List<ChannelPlayGoalVO> beforeList = channelPlayService.goalList(beforeDeleteGoalVO.getChannelPlayIdx());
			for (ChannelPlayGoalVO item : beforeList) {
				System.out.println("beforeList - goalName : " + item.getChannelPlayGoalName());
				System.out.println("beforeList - goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("beforeList - goalType : " + item.getChannelPlayGoalType());
				System.out.println("beforeList - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("beforeList - channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("=========================================");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(delete("/channel/play/goal/" + channelPlayIdx + "/" + channelPlayGoalIdx)
							.contentType(MediaType.APPLICATION_FORM_URLENCODED)
							.content(channelPlayIdx + "")
							.content(channelPlayGoalIdx + ""))
							.andExpect(status().isOk())
							.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			List<ChannelPlayGoalVO> goalList = channelPlayService.goalList(channelPlayIdx);
			
			System.out.println("========== 삭제 후 골 정보 =========");
			for (ChannelPlayGoalVO item : goalList) {
				System.out.println("goalName : " + item.getChannelPlayGoalName());
				System.out.println("goalTime : " + item.getChannelPlayGoalTime());
				System.out.println("goalType : " + item.getChannelPlayGoalType());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("channelPlayGoalIdx : " + item.getChannelPlayGoalIdx());
			}
			System.out.println("====================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 팀 성적 디테일 성공 테스트 (완료)
	@Test
	@Transactional
	void totalScoreTestOk() throws Exception {
		try {
//			// play 글 하나 생성
//			ChannelPlayVO channelPlayVO = new ChannelPlayVO();
//			channelPlayVO.setMemberIdx(2);
//			channelPlayVO.setChannelIdx(1);
//			channelPlayVO.setChannelPlayTitle("영상 게시물 디테일 테스트");
//			channelPlayVO.setChannelPlayImage("영상 게시물 디테일 테스트 이미지");
//			channelPlayVO.setChannelPlayVideo("영상 게시물 디테일 테스트 비디오");
//			channelPlayVO.setChannelPlayStep("ok");
//			channelPlayVO.setChannelPlayHomeFormation("442");
//			channelPlayVO.setChannelPlayAwayFormation("433");
//			
//			channelPlayService.insertChannelPlay(channelPlayVO);
//			
//			int channelIdx = channelPlayVO.getChannelIdx();
//			int channelPlayIdx = channelPlayVO.getChannelPlayIdx();
//			
//			System.out.println("channelPlayIdx : " + channelPlayIdx);
//			
//			ChannelPlayVO beforeSelectDetailVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
//			
//			System.out.println("beforeSelectDetailVO - channelPlayIdx : " + beforeSelectDetailVO.getChannelPlayIdx());
//			System.out.println("beforeSelectDetailVO - memberIdx : " + beforeSelectDetailVO.getMemberIdx());
//			System.out.println("beforeSelectDetailVO - channelIdx : " + beforeSelectDetailVO.getChannelIdx());
//			System.out.println("beforeSelectDetailVO - channelPlayTitle : " + beforeSelectDetailVO.getChannelPlayTitle());
//			System.out.println("beforeSelectDetailVO - channelPlaydate : " + beforeSelectDetailVO.getChannelPlayDate());
//			System.out.println("beforeSelectDetailVO - channelPlayImage : " + beforeSelectDetailVO.getChannelPlayImage());
//			System.out.println("beforeSelectDetailVO - channelPlayVideo : " + beforeSelectDetailVO.getChannelPlayVideo());
//			System.out.println("beforeSelectDetailVO - channelPlayStep : " + beforeSelectDetailVO.getChannelPlayStep());
//			System.out.println("beforeSelectDetailVO - channelPlayHomeFormation : " + beforeSelectDetailVO.getChannelPlayHomeFormation());
//			System.out.println("beforeSelectDetailVO - channelPlayAwayFormation : " + beforeSelectDetailVO.getChannelPlayAwayFormation());
//			System.out.println("beforeSelectDetailVO - memberNick : " + beforeSelectDetailVO.getMemberNick());
			
			// DB에 테스트용으로 저장되어있는 채널 게시글
			int channelPlayIdx = 1;
			int channelIdx = 1;
			
			// case 1. 팀이 지정되어 있지 않은 경우 (case 2를 주석처리 하기)
			
			
			// case 2. 팀이 지정되어 있는 경우
			TeamVO homeTeamVO = new TeamVO();
			homeTeamVO.setChannelPlayIdx(channelPlayIdx);
			homeTeamVO.setChannelIdx(channelIdx);
			homeTeamVO.setTeamName("HOME Team");
			homeTeamVO.setTeamType("H");
			
			TeamVO awayTeamVO = new TeamVO();
			awayTeamVO.setChannelPlayIdx(channelPlayIdx);
			awayTeamVO.setChannelIdx(channelIdx);
			awayTeamVO.setTeamName("AWAY Team");
			awayTeamVO.setTeamType("A");
			
			channelPlayMapper.insertTeam(homeTeamVO);
			channelPlayMapper.insertTeam(awayTeamVO);
			
			int homeTeamIdx = homeTeamVO.getTeamIdx();
			int awayTeamIdx = awayTeamVO.getTeamIdx();
			
//			System.out.println("homeTeamIdx : " + homeTeamIdx);
//			System.out.println("awayTeamIdx : " + awayTeamIdx);
			
			for (int i = 0; i <= 10; i++) {
				TeamPlayerVO teamPlayerVO = new TeamPlayerVO();
				teamPlayerVO.setUserIdx(6);
				teamPlayerVO.setTeamIdx(homeTeamIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				if (i == 0) {
					teamPlayerVO.setTeamPlayerPosition("G");
				} else if (0 < i && i <= 4) {
					teamPlayerVO.setTeamPlayerPosition("D");
				} else if (4 < i && i <= 8) {
					teamPlayerVO.setTeamPlayerPosition("M");
				} else {
					teamPlayerVO.setTeamPlayerPosition("F");
				}
				teamPlayerVO.setTeamPlayerFormationNumber(i);
				teamPlayerVO.setTeamPlayerName(i + 1 + "번째 Home team 멤버");
				
				HashMap<String, String> teamPlayerMap = new HashMap<>();
				teamPlayerMap.put("userIdx", Integer.toString(teamPlayerVO.getUserIdx()));
				teamPlayerMap.put("teamIdx", Integer.toString(teamPlayerVO.getTeamIdx()));
				teamPlayerMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
				teamPlayerMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
				
				teamPlayerService.insertTeamPlayer(teamPlayerMap);
				
//				System.out.println("teamPlayerIdx : " + String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				int teamPlayerIdx = Integer.parseInt(String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				
				PlayresultVO playresultVO = new PlayresultVO();
				playresultVO.setChannelPlayIdx(channelPlayIdx);
				playresultVO.setTeamIdx(homeTeamIdx);
				playresultVO.setTeamPlayerIdx(teamPlayerIdx);
				playresultVO.setPlayresultTotaltackle(i);
				playresultVO.setPlayresultSuccesstackle(i);
				playresultVO.setPlayresultTotalcross(i);
				playresultVO.setPlayresultSuccesscross(i);
				playresultVO.setPlayresultTotalcornerkick(i);
				playresultVO.setPlayresultSuccesscornerkick(i);
				playresultVO.setPlayresultTotalfreekick(i);
				playresultVO.setPlayresultSuccessfreekick(i);
				playresultVO.setPlayresultTotalshooting(i);
				playresultVO.setPlayresultSuccessshooting(i);
				playresultVO.setPlayresultTotalassist(i);
				playresultVO.setPlayresultSuccessassist(i);
				playresultVO.setPlayresultTotalpass(i);
				playresultVO.setPlayresultSuccesspass(i);
				playresultVO.setPlayresultTotalcontention(i);
				playresultVO.setPlayresultSuccesscontention(i);
				
				HashMap<String, Integer> playresultMap = new HashMap<>();
				
				playresultMap.put("channelPlayIdx", playresultVO.getChannelPlayIdx());
				playresultMap.put("teamIdx", playresultVO.getTeamIdx());
				playresultMap.put("teamPlayerIdx", playresultVO.getTeamPlayerIdx());
				
				teamPlayerService.insertPlayresult(playresultMap);
				
				playresultVO.setPlayresultIdx(Integer.parseInt(String.valueOf(playresultMap.get("playresultIdx"))));
				
				teamPlayerService.updatePlayresult(playresultVO);
				
			}
			
			for (int i = 0; i <= 10; i++) {
				TeamPlayerVO teamPlayerVO = new TeamPlayerVO();
				teamPlayerVO.setUserIdx(6);
				teamPlayerVO.setTeamIdx(awayTeamIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				if (i == 0) {
					teamPlayerVO.setTeamPlayerPosition("G");
				} else if (0 < i && i <= 4) {
					teamPlayerVO.setTeamPlayerPosition("D");
				} else if (4 < i && i <= 7) {
					teamPlayerVO.setTeamPlayerPosition("M");
				} else {
					teamPlayerVO.setTeamPlayerPosition("F");
				}
				teamPlayerVO.setTeamPlayerFormationNumber(i);
				teamPlayerVO.setTeamPlayerName(i + 1 + "번째 Away team 멤버");

				HashMap<String, String> teamPlayerMap = new HashMap<>();
				teamPlayerMap.put("userIdx", Integer.toString(teamPlayerVO.getUserIdx()));
				teamPlayerMap.put("teamIdx", Integer.toString(teamPlayerVO.getTeamIdx()));
				teamPlayerMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
				teamPlayerMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
				
				teamPlayerService.insertTeamPlayer(teamPlayerMap);
				int teamPlayerIdx = Integer.parseInt(String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				
				PlayresultVO playresultVO = new PlayresultVO();
				playresultVO.setChannelPlayIdx(channelPlayIdx);
				playresultVO.setTeamIdx(awayTeamIdx);
				playresultVO.setTeamPlayerIdx(teamPlayerIdx);
				playresultVO.setPlayresultTotaltackle(i + 1);
				playresultVO.setPlayresultSuccesstackle(i + 1);
				playresultVO.setPlayresultTotalcross(i + 1);
				playresultVO.setPlayresultSuccesscross(i + 1);
				playresultVO.setPlayresultTotalcornerkick(i + 1);
				playresultVO.setPlayresultSuccesscornerkick(i + 1);
				playresultVO.setPlayresultTotalfreekick(i + 1);
				playresultVO.setPlayresultSuccessfreekick(i + 1);
				playresultVO.setPlayresultTotalshooting(i + 1);
				playresultVO.setPlayresultSuccessshooting(i + 1);
				playresultVO.setPlayresultTotalassist(i + 1);
				playresultVO.setPlayresultSuccessassist(i + 1);
				playresultVO.setPlayresultTotalpass(i + 1);
				playresultVO.setPlayresultSuccesspass(i + 1);
				playresultVO.setPlayresultTotalcontention(i + 1);
				playresultVO.setPlayresultSuccesscontention(i + 1);
				
				HashMap<String, Integer> playresultMap = new HashMap<>();
				
				playresultMap.put("channelPlayIdx", playresultVO.getChannelPlayIdx());
				playresultMap.put("teamIdx", playresultVO.getTeamIdx());
				playresultMap.put("teamPlayerIdx", playresultVO.getTeamPlayerIdx());
				
				teamPlayerService.insertPlayresult(playresultMap);
				
				playresultVO.setPlayresultIdx(Integer.parseInt(String.valueOf(playresultMap.get("playresultIdx"))));
				
				teamPlayerService.updatePlayresult(playresultVO);
			}
			
			// 컨트롤러 확인 작업
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/team/result/" + channelPlayIdx)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(channelPlayIdx + ""))
						.andExpect(status().isOk())
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 팀 생성 성공 테스트 (미완성) - 찬기씨한테 ChannelPlayServiceImpl#createPlayInfo 메서드 물어봐야 함
	@Test
	@Transactional
	void createPlayInfoTestOk() throws Exception {
		try {
			int channelIdx = 1;
			int channelPlayIdx = 1;
			
			TeamVO homeTeamVO = new TeamVO();
			homeTeamVO.setTeamName("HOME Team");
			homeTeamVO.setTeamType("H");
			
			List<TeamPlayerVO> playerList = Collections.emptyList();
			
			for (int i = 0; i <= 10; i++) {
				TeamPlayerVO teamPlayerVO = new TeamPlayerVO();
				teamPlayerVO.setUserIdx(6);
				if (i == 0) {
					teamPlayerVO.setTeamPlayerPosition("G");
				} else if (0 < i && i <= 4) {
					teamPlayerVO.setTeamPlayerPosition("D");
				} else if (4 < i && i <= 8) {
					teamPlayerVO.setTeamPlayerPosition("M");
				} else {
					teamPlayerVO.setTeamPlayerPosition("F");
				}
				teamPlayerVO.setTeamPlayerFormationNumber(i);
				teamPlayerVO.setTeamPlayerName(i + 1 + "번째 Home team 멤버");
				
				HashMap<String, String> teamPlayerMap = new HashMap<>();
				teamPlayerMap.put("userIdx", Integer.toString(teamPlayerVO.getUserIdx()));
				teamPlayerMap.put("teamIdx", Integer.toString(teamPlayerVO.getTeamIdx()));
				teamPlayerMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
				teamPlayerMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
				
				teamPlayerService.insertTeamPlayer(teamPlayerMap);
				
//				System.out.println("teamPlayerIdx : " + String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				int teamPlayerIdx = Integer.parseInt(String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				
				PlayresultVO playresultVO = new PlayresultVO();
				playresultVO.setChannelPlayIdx(channelPlayIdx);
				//playresultVO.setTeamIdx(homeTeamIdx);
				playresultVO.setTeamPlayerIdx(teamPlayerIdx);
				playresultVO.setPlayresultTotaltackle(i);
				playresultVO.setPlayresultSuccesstackle(i);
				playresultVO.setPlayresultTotalcross(i);
				playresultVO.setPlayresultSuccesscross(i);
				playresultVO.setPlayresultTotalcornerkick(i);
				playresultVO.setPlayresultSuccesscornerkick(i);
				playresultVO.setPlayresultTotalfreekick(i);
				playresultVO.setPlayresultSuccessfreekick(i);
				playresultVO.setPlayresultTotalshooting(i);
				playresultVO.setPlayresultSuccessshooting(i);
				playresultVO.setPlayresultTotalassist(i);
				playresultVO.setPlayresultSuccessassist(i);
				playresultVO.setPlayresultTotalpass(i);
				playresultVO.setPlayresultSuccesspass(i);
				playresultVO.setPlayresultTotalcontention(i);
				playresultVO.setPlayresultSuccesscontention(i);
				
				HashMap<String, Integer> playresultMap = new HashMap<>();
				
				playresultMap.put("channelPlayIdx", playresultVO.getChannelPlayIdx());
				playresultMap.put("teamIdx", playresultVO.getTeamIdx());
				playresultMap.put("teamPlayerIdx", playresultVO.getTeamPlayerIdx());
				
				teamPlayerService.insertPlayresult(playresultMap);
				
			}
			
			// 컨트롤러 확인 작업
			UrlEncodedFormEntity channelPlay = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx))
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/" + channelIdx + "/" +channelPlayIdx)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(channelPlay))
						.content("" + channelIdx)
						.content("" + channelPlayIdx))
						.andExpect(status().isOk())
//						.andExpect(model().attributeExists("channelPlayList"))
//						.andExpect(model().attributeExists("message"))
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 선수 개별 기록 수정 성공 테스트 ( 직접 확인해야할 듯? - 미완성 )
	@Test
	@Transactional
	void resultUpdateTestOk() throws Exception {
		try {
			
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx = 1;
			int channelPlayIdx = 1;
			
			// Home Team 내용들 미리 추가
			TeamVO homeTeamVO = new TeamVO();
			homeTeamVO.setChannelPlayIdx(channelPlayIdx);
			homeTeamVO.setChannelIdx(channelIdx);
			homeTeamVO.setTeamName("HOME Team");
			homeTeamVO.setTeamType("H");
			
			channelPlayMapper.insertTeam(homeTeamVO);
			
			int homeTeamIdx = homeTeamVO.getTeamIdx();
			
			for (int i = 0; i <= 10; i++) {
				TeamPlayerVO teamPlayerVO = new TeamPlayerVO();
				teamPlayerVO.setUserIdx(6);
				teamPlayerVO.setTeamIdx(homeTeamIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				teamPlayerVO.setChannelPlayIdx(channelPlayIdx);
				if (i == 0) {
					teamPlayerVO.setTeamPlayerPosition("G");
				} else if (0 < i && i <= 4) {
					teamPlayerVO.setTeamPlayerPosition("D");
				} else if (4 < i && i <= 8) {
					teamPlayerVO.setTeamPlayerPosition("M");
				} else {
					teamPlayerVO.setTeamPlayerPosition("F");
				}
				teamPlayerVO.setTeamPlayerFormationNumber(i);
				teamPlayerVO.setTeamPlayerName(i + 1 + "번째 Home team 멤버");
				
				HashMap<String, String> teamPlayerMap = new HashMap<>();
				teamPlayerMap.put("userIdx", Integer.toString(teamPlayerVO.getUserIdx()));
				teamPlayerMap.put("teamIdx", Integer.toString(teamPlayerVO.getTeamIdx()));
				teamPlayerMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
				teamPlayerMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
				
				teamPlayerService.insertTeamPlayer(teamPlayerMap);
				
//				System.out.println("teamPlayerIdx : " + String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				int teamPlayerIdx = Integer.parseInt(String.valueOf(teamPlayerMap.get("teamPlayerIdx")));
				
				PlayresultVO playresultVO = new PlayresultVO();
				playresultVO.setChannelPlayIdx(channelPlayIdx);
				playresultVO.setTeamIdx(homeTeamIdx);
				playresultVO.setTeamPlayerIdx(teamPlayerIdx);
				playresultVO.setPlayresultTotaltackle(i);
				playresultVO.setPlayresultSuccesstackle(i);
				playresultVO.setPlayresultTotalcross(i);
				playresultVO.setPlayresultSuccesscross(i);
				playresultVO.setPlayresultTotalcornerkick(i);
				playresultVO.setPlayresultSuccesscornerkick(i);
				playresultVO.setPlayresultTotalfreekick(i);
				playresultVO.setPlayresultSuccessfreekick(i);
				playresultVO.setPlayresultTotalshooting(i);
				playresultVO.setPlayresultSuccessshooting(i);
				playresultVO.setPlayresultTotalassist(i);
				playresultVO.setPlayresultSuccessassist(i);
				playresultVO.setPlayresultTotalpass(i);
				playresultVO.setPlayresultSuccesspass(i);
				playresultVO.setPlayresultTotalcontention(i);
				playresultVO.setPlayresultSuccesscontention(i);
				
				HashMap<String, Integer> playresultMap = new HashMap<>();
				
				playresultMap.put("channelPlayIdx", playresultVO.getChannelPlayIdx());
				playresultMap.put("teamIdx", playresultVO.getTeamIdx());
				playresultMap.put("teamPlayerIdx", playresultVO.getTeamPlayerIdx());
				
				teamPlayerService.insertPlayresult(playresultMap);
				
			}
			
			// DB에 어떻게 들어갔는지 확인하는 작업
			List<PlayresultVO> beforeUpdateList = teamPlayerService.selectPlayerResultVOList(channelPlayIdx);
			
			System.out.println("========= 수정 전 playresult ===========");
			for (PlayresultVO item : beforeUpdateList) {
				System.out.println("beforeUpdateList - playresultIdx : " + item.getPlayresultIdx());
				System.out.println("beforeUpdateList - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("beforeUpdateList - teamIdx : " + item.getTeamIdx());
				System.out.println("beforeUpdateList - teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("beforeUpdateList - totaltackle : " + item.getPlayresultTotaltackle());
				System.out.println("beforeUpdateList - successtackle : " + item.getPlayresultSuccesstackle());
			}
			System.out.println("========================================");
			
			// 컨트롤러 확인 작업
			UrlEncodedFormEntity result = new UrlEncodedFormEntity(Arrays.asList(
						new BasicNameValuePair("", null)
					),"UTF-8");
			
			//UrlEncodedFormEntity resultList = new UrlEncodedFormEntity(result), "UTF-8");
			
//			System.out.println("==========컨트롤러 진입 이전=========");
//			mockMvc.perform(post("/channel/play/result/")
//						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//						.content(EntityUtils.toString(resultList)))
//						.andExpect(status().isOk())
////						.andExpect(model().attributeExists("channelPlayList"))
////						.andExpect(model().attributeExists("message"))
//						.andDo(print());
//			System.out.println("==========컨트롤러 진입 이후=========");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
