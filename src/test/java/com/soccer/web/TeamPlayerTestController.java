package com.soccer.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;

import com.soccer.web.channel.play.controller.TeamPlayerController;
import com.soccer.web.channel.play.dao.ChannelPlayMapper;
import com.soccer.web.channel.play.service.TeamPlayerService;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamPlayerTestController { // 삭제 써놓은 부분은 진행 X

	@Autowired
	TeamPlayerController teamPlayerController;
	
	@Autowired
	TeamPlayerService teamPlayerService;
	
	@Autowired
	ChannelPlayMapper channelPlayMapper;
	
	@Autowired
	MockMvc mockMvc;
	
	// 포메이션 디테일 성공 테스트 (완료)
	@Test
	@Transactional
	void selectChannelPlayFormationTestOk() throws Exception {
		try {
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx = 1;
			int channelPlayIdx = 1;
			
			// 팀, 선수를 미리 넣어놓기
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
			
			List<TeamVO> teamList = channelPlayMapper.selectTeamList(channelPlayIdx);
			
			System.out.println("========= 연결된 팀 확인 =======");
			for (TeamVO item : teamList) {
				System.out.println("Team - teamIdx : " + item.getTeamIdx());
				System.out.println("Team - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("Team - channelIdx : " + item.getChannelIdx());
				System.out.println("Team - teamName : " + item.getTeamName());
				System.out.println("Team - teamType : " + item.getTeamType());
			}
			System.out.println("================================");
			
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
				
			}
			
			System.out.println("=============== Home 선수 리스트 ===============");
			
			HashMap<String, String> homeTeamInfo = new HashMap<>();
			homeTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			homeTeamInfo.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo);
			
			for (TeamPlayerVO item : homeTeamPlayerList) {
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			System.out.println("=============== Away 선수 리스트 ===============");
			
			HashMap<String, String> awayTeamInfo = new HashMap<>();
			awayTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			awayTeamInfo.put("teamType", "A");
			
			List<TeamPlayerVO> awayTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(awayTeamInfo);
			
			for (TeamPlayerVO item : awayTeamPlayerList) {
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			// 컨트롤러 확인 작업
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/formation/" + channelIdx + "/" +channelPlayIdx)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content("" + channelIdx)
						.content("" + channelPlayIdx))
						.andExpect(status().isOk())
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 포메이션 변경 전 정보 디테일 성공 테스트 (완료)
	@Test
	@Transactional
	void selectChannelPlayFormationDetailTestOk() throws Exception {
		try {
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx = 1;
			int channelPlayIdx = 1;
			
			// 팀, 선수를 미리 넣어놓기
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
			
			List<TeamVO> teamList = channelPlayMapper.selectTeamList(channelPlayIdx);
			
			System.out.println("========= 연결된 팀 확인 =======");
			for (TeamVO item : teamList) {
				System.out.println("Team - teamIdx : " + item.getTeamIdx());
				System.out.println("Team - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("Team - channelIdx : " + item.getChannelIdx());
				System.out.println("Team - teamName : " + item.getTeamName());
				System.out.println("Team - teamType : " + item.getTeamType());
			}
			System.out.println("================================");
			
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
				
			}
			
			System.out.println("=============== Home 선수 리스트 ===============");
			
			HashMap<String, String> homeTeamInfo = new HashMap<>();
			homeTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			homeTeamInfo.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo);
			
			for (TeamPlayerVO item : homeTeamPlayerList) {
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			System.out.println("=============== Away 선수 리스트 ===============");
			
			HashMap<String, String> awayTeamInfo = new HashMap<>();
			awayTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			awayTeamInfo.put("teamType", "A");
			
			List<TeamPlayerVO> awayTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(awayTeamInfo);
			
			for (TeamPlayerVO item : awayTeamPlayerList) {
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			// 컨트롤러 확인 작업
			
			// homeTeam의 경우
			String homeTeamType = "H";
			
			UrlEncodedFormEntity teamInfo = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
					new BasicNameValuePair("teamType", homeTeamType)
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/formation/" + channelIdx + "/" + channelPlayIdx + "/" + homeTeamType)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(teamInfo)))
						.andExpect(status().isOk())
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			// awayTeam의 경우
//			String awayTeamType = "A";
//			
//			UrlEncodedFormEntity teamInfo = new UrlEncodedFormEntity(Arrays.asList(
//					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
//					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
//					new BasicNameValuePair("teamType", awayTeamType)
//			), "UTF-8");
//			
//			System.out.println("==========컨트롤러 진입 이전=========");
//			mockMvc.perform(get("/channel/play/formation/" + channelIdx + "/" +channelPlayIdx + "/" + awayTeamType)
//						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//						.content(EntityUtils.toString(teamInfo)))
//						.andExpect(status().isOk())
//						.andDo(print());
//			System.out.println("==========컨트롤러 진입 이후=========");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 포메이션 변경 성공 테스트 
	// TODO view 연결 된 후 view에서 테스트 해보기
	@Test
	@Transactional
	void updateChannelPlayFormationDetailTestOk() throws Exception {
		try {
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx = 1;
			int channelPlayIdx = 1;
			
			// 팀, 선수를 미리 넣어놓기
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
			
			List<TeamVO> teamList = channelPlayMapper.selectTeamList(channelPlayIdx);
			
			System.out.println("========= 연결된 팀 확인 =======");
			for (TeamVO item : teamList) {
				System.out.println("Team - teamIdx : " + item.getTeamIdx());
				System.out.println("Team - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("Team - channelIdx : " + item.getChannelIdx());
				System.out.println("Team - teamName : " + item.getTeamName());
				System.out.println("Team - teamType : " + item.getTeamType());
			}
			System.out.println("================================");
			
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
				
			}
			
			System.out.println("=============== Home 선수 리스트 ===============");
			
			HashMap<String, String> homeTeamInfo = new HashMap<>();
			homeTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			homeTeamInfo.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo);
			
			for (TeamPlayerVO item : homeTeamPlayerList) {
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			System.out.println("=============== Away 선수 리스트 ===============");
			
			HashMap<String, String> awayTeamInfo = new HashMap<>();
			awayTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			awayTeamInfo.put("teamType", "A");
			
			List<TeamPlayerVO> awayTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(awayTeamInfo);
			
			for (TeamPlayerVO item : awayTeamPlayerList) {
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			// 컨트롤러 확인 작업
			
			// homeTeam의 경우
			String homeTeamType = "H";
			
			UrlEncodedFormEntity teamInfo = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
					new BasicNameValuePair("teamType", homeTeamType),
					new BasicNameValuePair("formation", "424")
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(put("/channel/play/formation/" + channelIdx + "/" + channelPlayIdx + "/" + homeTeamType)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(teamInfo)))
						.andExpect(status().isOk())
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			// awayTeam의 경우
//			String awayTeamType = "A";
//			
//			UrlEncodedFormEntity teamInfo = new UrlEncodedFormEntity(Arrays.asList(
//					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
//					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
//					new BasicNameValuePair("teamType", awayTeamType)
//			), "UTF-8");
//			
//			System.out.println("==========컨트롤러 진입 이전=========");
//			mockMvc.perform(get("/channel/play/formation/" + channelIdx + "/" +channelPlayIdx + "/" + awayTeamType)
//						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//						.content(EntityUtils.toString(teamInfo)))
//						.andExpect(status().isOk())
//						.andDo(print());
//			System.out.println("==========컨트롤러 진입 이후=========");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 선수 삭제 성공 테스트 (완료)
	@Test
	@Transactional
	void deleteTeamPlayerDetailTestOk() throws Exception {
		try {
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx = 1;
			int channelPlayIdx = 1;
			
			// 팀, 선수를 미리 넣어놓기
			TeamVO homeTeamVO = new TeamVO();
			homeTeamVO.setChannelPlayIdx(channelPlayIdx);
			homeTeamVO.setChannelIdx(channelIdx);
			homeTeamVO.setTeamName("HOME Team");
			homeTeamVO.setTeamType("H");
			
			channelPlayMapper.insertTeam(homeTeamVO);
			
			int homeTeamIdx = homeTeamVO.getTeamIdx();
			
			System.out.println("homeTeamIdx : " + homeTeamIdx);
			
			List<TeamVO> teamList = channelPlayMapper.selectTeamList(channelPlayIdx);
			
			System.out.println("========= 연결된 팀 확인 =======");
			for (TeamVO item : teamList) {
				System.out.println("Team - teamIdx : " + item.getTeamIdx());
				System.out.println("Team - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("Team - channelIdx : " + item.getChannelIdx());
				System.out.println("Team - teamName : " + item.getTeamName());
				System.out.println("Team - teamType : " + item.getTeamType());
			}
			System.out.println("================================");
			
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
				
			}
			
			int teamPlayerIdx = 0;
			
			System.out.println("=============== 삭제전 Home 선수 리스트 ===============");
			
			HashMap<String, String> homeTeamInfo = new HashMap<>();
			homeTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			homeTeamInfo.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo);
			
			for (TeamPlayerVO item : homeTeamPlayerList) {
				teamPlayerIdx = item.getTeamPlayerIdx();
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
			// 컨트롤러 확인 작업
			
			UrlEncodedFormEntity teamPlayer = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx)),
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx)),
					new BasicNameValuePair("teamPlayerIdx", String.valueOf(teamPlayerIdx))
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(delete("/channel/play/player/" + channelIdx + "/" + channelPlayIdx + "/" + teamPlayerIdx)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(teamPlayer)))
						.andExpect(status().isOk())
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
			
			System.out.println("=============== 삭제 후 Home 선수 리스트 ===============");
			
			HashMap<String, String> homeTeamInfo1 = new HashMap<>();
			homeTeamInfo1.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			homeTeamInfo1.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList1 = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo1);
			
			for (TeamPlayerVO item : homeTeamPlayerList1) {
				teamPlayerIdx = item.getTeamPlayerIdx();
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("userIdx : " + item.getUserIdx());
				System.out.println("teamIdx : " + item.getTeamIdx());
				System.out.println("channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("teamPlayerPosition : " + item.getTeamPlayerPosition());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				System.out.println("teamPlayerName : " + item.getTeamPlayerName());
			}
			
			System.out.println("================================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 선수 세부사항 성공 테스트
	@Test
	@Transactional
	void selectTeamPlayerDetailTestOk() throws Exception {
		try {
			// 1번째
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx1 = 1;
			int channelPlayIdx1 = 1;
			
			// 팀, 선수를 미리 넣어놓기
			TeamVO homeTeamVO1 = new TeamVO();
			homeTeamVO1.setChannelPlayIdx(channelPlayIdx1);
			homeTeamVO1.setChannelIdx(channelIdx1);
			homeTeamVO1.setTeamName("HOME Team");
			homeTeamVO1.setTeamType("H");
			
			channelPlayMapper.insertTeam(homeTeamVO1);
			
			int homeTeamIdx1 = homeTeamVO1.getTeamIdx();
			
			System.out.println("homeTeamIdx : " + homeTeamIdx1);
			
			List<TeamVO> teamList1 = channelPlayMapper.selectTeamList(channelPlayIdx1);
			
			System.out.println("========= 연결된 팀 확인 =======");
			for (TeamVO item : teamList1) {
				System.out.println("Team - teamIdx : " + item.getTeamIdx());
				System.out.println("Team - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("Team - channelIdx : " + item.getChannelIdx());
				System.out.println("Team - teamName : " + item.getTeamName());
				System.out.println("Team - teamType : " + item.getTeamType());
			}
			System.out.println("================================");
			
			TeamPlayerVO teamPlayerVO1 = new TeamPlayerVO();
			teamPlayerVO1.setUserIdx(6);
			teamPlayerVO1.setTeamIdx(homeTeamIdx1);
			teamPlayerVO1.setChannelPlayIdx(channelPlayIdx1);
			teamPlayerVO1.setTeamPlayerPosition("G");
			
			teamPlayerVO1.setTeamPlayerFormationNumber(0);
			teamPlayerVO1.setTeamPlayerName(1 + "번째 Home team 멤버");
			
			HashMap<String, String> teamPlayerMap1 = new HashMap<>();
			teamPlayerMap1.put("userIdx", Integer.toString(teamPlayerVO1.getUserIdx()));
			teamPlayerMap1.put("teamIdx", Integer.toString(teamPlayerVO1.getTeamIdx()));
			teamPlayerMap1.put("channelPlayIdx", Integer.toString(channelPlayIdx1));
			teamPlayerMap1.put("teamPlayerPosition", teamPlayerVO1.getTeamPlayerPosition());
			
			teamPlayerService.insertTeamPlayer(teamPlayerMap1);
			
			int teamPlayerIdx1 = 0;
			
			HashMap<String, String> homeTeamInfo1 = new HashMap<>();
			homeTeamInfo1.put("channelPlayIdx", Integer.toString(channelPlayIdx1));
			homeTeamInfo1.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList1 = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo1);
			
			for (TeamPlayerVO item : homeTeamPlayerList1) {
				teamPlayerIdx1 = item.getTeamPlayerIdx();
			}
			
			PlayresultVO playresultVO1 = new PlayresultVO();
			playresultVO1.setChannelPlayIdx(channelPlayIdx1);
			playresultVO1.setTeamIdx(homeTeamIdx1);
			playresultVO1.setTeamPlayerIdx(teamPlayerIdx1);
			playresultVO1.setPlayresultTotaltackle(5);
			playresultVO1.setPlayresultSuccesstackle(5);
			playresultVO1.setPlayresultTotalcross(5);
			playresultVO1.setPlayresultSuccesscross(5);
			playresultVO1.setPlayresultTotalcornerkick(5);
			playresultVO1.setPlayresultSuccesscornerkick(5);
			playresultVO1.setPlayresultTotalfreekick(5);
			playresultVO1.setPlayresultSuccessfreekick(5);
			playresultVO1.setPlayresultTotalshooting(5);
			playresultVO1.setPlayresultSuccessshooting(5);
			playresultVO1.setPlayresultTotalassist(5);
			playresultVO1.setPlayresultSuccessassist(5);
			playresultVO1.setPlayresultTotalpass(5);
			playresultVO1.setPlayresultSuccesspass(5);
			playresultVO1.setPlayresultTotalcontention(5);
			playresultVO1.setPlayresultSuccesscontention(5);
			
			HashMap<String, Integer> playresultMap1 = new HashMap<>();
			
			playresultMap1.put("channelPlayIdx", playresultVO1.getChannelPlayIdx());
			playresultMap1.put("teamIdx", playresultVO1.getTeamIdx());
			playresultMap1.put("teamPlayerIdx", playresultVO1.getTeamPlayerIdx());
			
			teamPlayerService.insertPlayresult(playresultMap1);

			int playresultIdx1 = Integer.parseInt(String.valueOf(playresultMap1.get("playresultIdx")));
			playresultVO1.setPlayresultIdx(playresultIdx1);
			
			teamPlayerService.updatePlayresult(playresultVO1);
			
			//2번째
			// DB에 들어가있는 테스트용 channelPlay에 연결
			int channelIdx2 = 3;
			int channelPlayIdx2 = 211;
			
			// 팀, 선수를 미리 넣어놓기
			TeamVO homeTeamVO2 = new TeamVO();
			homeTeamVO2.setChannelPlayIdx(channelPlayIdx2);
			homeTeamVO2.setChannelIdx(channelIdx2);
			homeTeamVO2.setTeamName("HOME Team");
			homeTeamVO2.setTeamType("H");
			
			channelPlayMapper.insertTeam(homeTeamVO2);
			
			int homeTeamIdx2 = homeTeamVO2.getTeamIdx();
			
			System.out.println("homeTeamIdx : " + homeTeamIdx2);
			
			List<TeamVO> teamList2 = channelPlayMapper.selectTeamList(channelPlayIdx2);
			
			System.out.println("========= 연결된 팀 확인 =======");
			for (TeamVO item : teamList2) {
				System.out.println("Team - teamIdx : " + item.getTeamIdx());
				System.out.println("Team - channelPlayIdx : " + item.getChannelPlayIdx());
				System.out.println("Team - channelIdx : " + item.getChannelIdx());
				System.out.println("Team - teamName : " + item.getTeamName());
				System.out.println("Team - teamType : " + item.getTeamType());
			}
			System.out.println("================================");
			
			TeamPlayerVO teamPlayerVO2 = new TeamPlayerVO();
			teamPlayerVO2.setUserIdx(6);
			teamPlayerVO2.setTeamIdx(homeTeamIdx2);
			teamPlayerVO2.setChannelPlayIdx(channelPlayIdx2);
			teamPlayerVO2.setTeamPlayerPosition("G");
			
			teamPlayerVO2.setTeamPlayerFormationNumber(0);
			teamPlayerVO2.setTeamPlayerName(1 + "번째 Home team 멤버");
			
			HashMap<String, String> teamPlayerMap2 = new HashMap<>();
			teamPlayerMap2.put("userIdx", Integer.toString(teamPlayerVO2.getUserIdx()));
			teamPlayerMap2.put("teamIdx", Integer.toString(teamPlayerVO2.getTeamIdx()));
			teamPlayerMap2.put("channelPlayIdx", Integer.toString(channelPlayIdx2));
			teamPlayerMap2.put("teamPlayerPosition", teamPlayerVO2.getTeamPlayerPosition());
			
			teamPlayerService.insertTeamPlayer(teamPlayerMap2);
			
			int teamPlayerIdx2 = 0;
			
			HashMap<String, String> homeTeamInfo2 = new HashMap<>();
			homeTeamInfo2.put("channelPlayIdx", Integer.toString(channelPlayIdx2));
			homeTeamInfo2.put("teamType", "H");
			
			List<TeamPlayerVO> homeTeamPlayerList2 = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo2);
			
			for (TeamPlayerVO item : homeTeamPlayerList2) {
				teamPlayerIdx2 = item.getTeamPlayerIdx();
			}
			
			PlayresultVO playresultVO2 = new PlayresultVO();
			playresultVO2.setChannelPlayIdx(channelPlayIdx2);
			playresultVO2.setTeamIdx(homeTeamIdx2);
			playresultVO2.setTeamPlayerIdx(teamPlayerIdx2);
			playresultVO2.setPlayresultTotaltackle(15);
			playresultVO2.setPlayresultSuccesstackle(15);
			playresultVO2.setPlayresultTotalcross(15);
			playresultVO2.setPlayresultSuccesscross(15);
			playresultVO2.setPlayresultTotalcornerkick(25);
			playresultVO2.setPlayresultSuccesscornerkick(15);
			playresultVO2.setPlayresultTotalfreekick(15);
			playresultVO2.setPlayresultSuccessfreekick(25);
			playresultVO2.setPlayresultTotalshooting(51);
			playresultVO2.setPlayresultSuccessshooting(25);
			playresultVO2.setPlayresultTotalassist(35);
			playresultVO2.setPlayresultSuccessassist(35);
			playresultVO2.setPlayresultTotalpass(52);
			playresultVO2.setPlayresultSuccesspass(15);
			playresultVO2.setPlayresultTotalcontention(25);
			playresultVO2.setPlayresultSuccesscontention(35);
			
			HashMap<String, Integer> playresultMap2 = new HashMap<>();
			
			playresultMap2.put("channelPlayIdx", playresultVO2.getChannelPlayIdx());
			playresultMap2.put("teamIdx", playresultVO2.getTeamIdx());
			playresultMap2.put("teamPlayerIdx", playresultVO2.getTeamPlayerIdx());
			
			teamPlayerService.insertPlayresult(playresultMap2);

			int playresultIdx2 = Integer.parseInt(String.valueOf(playresultMap2.get("playresultIdx")));
			playresultVO2.setPlayresultIdx(playresultIdx2);
			
			teamPlayerService.updatePlayresult(playresultVO2);
			
			// 컨트롤러 확인 작업
			
			UrlEncodedFormEntity teamPlayer = new UrlEncodedFormEntity(Arrays.asList(
					new BasicNameValuePair("channelIdx", String.valueOf(channelIdx1)),
					new BasicNameValuePair("channelPlayIdx", String.valueOf(channelPlayIdx1)),
					new BasicNameValuePair("teamPlayerIdx", String.valueOf(teamPlayerIdx1))
			), "UTF-8");
			
			System.out.println("==========컨트롤러 진입 이전=========");
			mockMvc.perform(get("/channel/play/player/" + channelIdx1 + "/" + channelPlayIdx1 + "/" + teamPlayerIdx1)
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.content(EntityUtils.toString(teamPlayer)))
						.andExpect(status().isOk())
						.andDo(print());
			System.out.println("==========컨트롤러 진입 이후=========");
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 이 이상은 channelPlayController와 겹치는 듯?
	
}
