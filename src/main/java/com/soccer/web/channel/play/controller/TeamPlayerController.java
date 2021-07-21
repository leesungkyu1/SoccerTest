package com.soccer.web.channel.play.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.member.service.MemberService;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.play.service.ChannelPlayService;
import com.soccer.web.channel.play.service.TeamPlayerService;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;
import com.soccer.web.channel.service.ChannelService;
import com.soccer.web.user.service.UserService;
import com.soccer.web.user.vo.UserVO;

@Controller
public class TeamPlayerController {

	@Autowired
	private TeamPlayerService teamPlayerService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ChannelPlayService channelPlayService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private UserService userService;
	
	// 영상 게시글에서 Player를 추가할 때 나오는 채널 멤버의 리스트 출력
//	@RequestMapping(value = "channel/play/member/{channelIdx}/{channelPlayIdx}", method = RequestMethod.GET)
//	public String selectMemberList(@PathVariable int channelIdx, @PathVariable int channelPlayIdx, Model model) throws Exception {
//		try {
//			ChannelVO channelVO = new ChannelVO();
//			channelVO.setChannelIdx(channelIdx);
//			List<MemberVO> memberVOList = memberService.memberList(channelVO);
//			model.addAttribute("memberList", memberVOList);
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("message", "에러가 발생했습니다.");
//		}
//		return "";
//	}
	
	//삭제
	// 영상 게시글에서 Player를 추가하는 메서드
	@RequestMapping(value = "/channel/play/player/{channelIdx}/{channelPlayIdx}", method = RequestMethod.POST)
	public String insertTeamPlayer(RedirectAttributes attributes,
								@PathVariable int channelIdx,
								@PathVariable int channelPlayIdx, 
								@RequestParam("userIdx") int userIdx,
								TeamPlayerVO teamPlayerVO) throws Exception {
		try {
			HashMap<String, String> teamPlayParameterMap = new HashMap<>();
			teamPlayParameterMap.put("userIdx", Integer.toString(userIdx));
			teamPlayParameterMap.put("teamIdx", Integer.toString(teamIdx));
			teamPlayParameterMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			teamPlayParameterMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
			
			int teamPlayerIdx = teamPlayerService.insertTeamPlayer(teamPlayParameterMap); // 선수 추가
			
			HashMap<String, Integer> playresultMap = new HashMap<>();
			playresultMap.put("channelPlayIdx", channelPlayIdx);
			playresultMap.put("teamIdx", teamIdx);
			playresultMap.put("teamPlayerIdx", teamPlayerIdx);
			
			teamPlayerService.insertPlayresult(playresultMap); // 선수의 경기 결과 추가
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		
		attributes.addAttribute("message", "선수가 등록되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
	}
	
//	// 영상 게시글에서 Player의 position을 변경하는 메서드
//	@RequestMapping(value = "channel/play/player/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.PUT)
//	public String updateTeamPlayerPosition(	RedirectAttributes attributes,
//											@PathVariable int channelIdx,
//											@PathVariable int channelPlayIdx,
//											@PathVariable int teamPlayerIdx,
//											TeamPlayerVO teamPlayerVO) throws Exception {
//		try {
//			
//			teamPlayerService.updateTeamPlayerPosition(teamPlayerVO);
//		} catch (Exception e) {
//			e.printStackTrace();
//			attributes.addAttribute("message", "에러가 발생했습니다.");
//		}
//		attributes.addAttribute("message", "선수의 포지션이 수정되었습니다.");
//		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
//	}
	
	// 영상 게시글에서 포메이션을 불러오는 메서드 (player의 formationIdx가 있는 경우 매칭 포함)
	@RequestMapping(value = "/channel/play/formation/{channelIdx}/{channelPlayIdx}", method = RequestMethod.GET)
	public String selectChannelPlayFormation(	@PathVariable int channelIdx,
												@PathVariable int channelPlayIdx,
												Model model
												) throws Exception {
		try {
			// 영상 게시글에 엮인 채널, 팀, 선수 가져와야함
			HashMap<String, String> homeTeamInfo = new HashMap<>();
			HashMap<String, String> awayTeamInfo = new HashMap<>();
			homeTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			homeTeamInfo.put("teamType", "H");
			awayTeamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			awayTeamInfo.put("teamType", "A");
			
			List<TeamVO> teamVOList = channelPlayService.selectTeamList(channelPlayIdx);
			
			TeamVO homeTeam = new TeamVO();
			TeamVO awayTeam = new TeamVO();
			
			for (TeamVO item : teamVOList) {
				if (item.getTeamType().equals("H")) {
					homeTeam = item;
				} else {
					awayTeam = item;
				}
			}
			
			ChannelPlayVO channelPlayVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			String homeFormation = channelPlayVO.getChannelPlayHomeFormation();
			String awayFormation = channelPlayVO.getChannelPlayAwayFormation();
			
			List<TeamPlayerVO> homeTeamPlayerVOList = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo);
			List<TeamPlayerVO> awayTeamPlayerVOList = teamPlayerService.selectHomeAwayTeamPlayerList(awayTeamInfo);
			
			int homeGoalkeeper = 1;
			int homeDefender = 0;
			int homeMidfilder = 0;
			int homeForward = 0;
			
			if (homeFormation.length() == 1) {
				homeForward = Integer.parseInt(homeFormation);
			} else if (homeFormation.length() == 2) {
				homeDefender = Character.getNumericValue(homeFormation.charAt(0));
				homeForward = Character.getNumericValue(homeFormation.charAt(1));
			} else if (homeFormation.length() >= 3) {
				homeDefender = Character.getNumericValue(homeFormation.charAt(0));
				homeMidfilder = Character.getNumericValue(homeFormation.charAt(1));
				for (int i = 3; i <= homeFormation.length(); i++) {
					homeForward += Character.getNumericValue(homeFormation.charAt(i - 1));
				}
			}
			
			int awayGoalkeeper = 1;
			int awayDefender = 0;
			int awayMidfilder = 0;
			int awayForward = 0;
			
			if (awayFormation.length() == 1) {
				awayForward = Integer.parseInt(awayFormation);
			} else if (awayFormation.length() == 2) {
				awayDefender = Character.getNumericValue(awayFormation.charAt(0));
				awayForward = Character.getNumericValue(awayFormation.charAt(1));
			} else if (awayFormation.length() >= 3) {
				awayDefender = Character.getNumericValue(awayFormation.charAt(0));
				awayMidfilder = Character.getNumericValue(awayFormation.charAt(1));
				for (int i = 3; i <= awayFormation.length(); i++) {
					awayForward += Character.getNumericValue(awayFormation.charAt(i - 1));
				}
			}
			
			TeamPlayerVO homeGoalkeeperVO = homeTeamPlayerVOList.get(0);
			List<TeamPlayerVO> homeDefenderVOList = new ArrayList<>();
			List<TeamPlayerVO> homeMidfilderVOList = new ArrayList<>();
			List<TeamPlayerVO> homeForwardVOList = new ArrayList<>();
			for (int i = 0; i < homeTeamPlayerVOList.size(); i++) {
				TeamPlayerVO tmpVO = homeTeamPlayerVOList.get(i);
				if (i == 0) {
					continue;
				} else if ( 1 <= i && i <=homeDefender) {
					homeDefenderVOList.add(tmpVO);
				} else if ( homeDefender < i && i <= homeDefender + homeMidfilder) {
					homeMidfilderVOList.add(tmpVO);
				} else if (homeDefender + homeMidfilder < i && i <= homeDefender + homeMidfilder + homeForward) {
					homeForwardVOList.add(tmpVO);
				}
			}
			
			TeamPlayerVO awayGoalkeeperVO = awayTeamPlayerVOList.get(0);
			List<TeamPlayerVO> awayDefenderVOList = new ArrayList<>();
			List<TeamPlayerVO> awayMidfilderVOList = new ArrayList<>();
			List<TeamPlayerVO> awayForwardVOList = new ArrayList<>();
			for (int i = 0; i < awayTeamPlayerVOList.size(); i++) {
				TeamPlayerVO tmpVO = awayTeamPlayerVOList.get(i);
				if (i == 0) {
					continue;
				} else if ( 1 <= i && i <=awayDefender) {
					awayDefenderVOList.add(tmpVO);
				} else if ( awayDefender < i && i <= awayDefender + awayMidfilder) {
					awayMidfilderVOList.add(tmpVO);
				} else if (awayDefender + awayMidfilder < i && i <= awayDefender + awayMidfilder + awayForward) {
					awayForwardVOList.add(tmpVO);
				}
			}
			
			model.addAttribute("homeTeam", homeTeam);
			model.addAttribute("awayTeam", awayTeam);
			model.addAttribute("homeGoalkeeperVO", homeGoalkeeperVO);
			model.addAttribute("homeDefenderVOList", homeDefenderVOList);
			model.addAttribute("homeMidfilderVOList", homeMidfilderVOList);
			model.addAttribute("homeForwardVOList", homeForwardVOList);
			model.addAttribute("awayGoalkeeperVO", awayGoalkeeperVO);
			model.addAttribute("awayDefenderVOList", awayDefenderVOList);
			model.addAttribute("awayMidfilderVOList", awayMidfilderVOList);
			model.addAttribute("awayForwardVOList", awayForwardVOList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		return "channel/channel_formation";
//		return "test";
	}
	
	// 영상 게시글에서 포메이션을 변경하기 전 정보를 받아오는 메서드
	@RequestMapping(value = "/channel/play/formation/{channelIdx}/{channelPlayIdx}/{teamType}", method = RequestMethod.GET)
	public String selectChannelPlayFormationDetail(	@PathVariable int channelIdx,
													@PathVariable int channelPlayIdx,
													@PathVariable String teamType,
													Model model) throws Exception {
		try {
			ChannelPlayVO channelPlayVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			String formation = "";
			HashMap<String, String> teamInfo = new HashMap<>();
			teamInfo.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			teamInfo.put("teamType", teamType);
			if (teamType.equals("H")) {
				formation = channelPlayVO.getChannelPlayHomeFormation();
			} else {
				formation = channelPlayVO.getChannelPlayAwayFormation();
			}
			List<TeamPlayerVO> teamPlayerVOList = teamPlayerService.selectHomeAwayTeamPlayerList(teamInfo);
			
			
			int goalkeeper = 1;
			int defender = 0;
			int midfilder = 0;
			int forward = 0;
			
			if (formation.length() == 1) {
				forward = Integer.parseInt(formation);
			} else if (formation.length() == 2) {
				defender = Character.getNumericValue(formation.charAt(0));
				forward = Character.getNumericValue(formation.charAt(1));
			} else if (formation.length() >= 3) {
				defender = Character.getNumericValue(formation.charAt(0));
				midfilder = Character.getNumericValue(formation.charAt(1));
				for (int i = 3; i <= formation.length(); i++) {
					forward += Character.getNumericValue(formation.charAt(i - 1));
				}
			}
			
			TeamPlayerVO goalkeeperVO = teamPlayerVOList.get(0);
			List<TeamPlayerVO> defenderVOList = new ArrayList<>();
			List<TeamPlayerVO> midfilderVOList = new ArrayList<>();
			List<TeamPlayerVO> forwardVOList = new ArrayList<>();
			for (int i = 0; i < teamPlayerVOList.size(); i++) {
				TeamPlayerVO tmpVO = teamPlayerVOList.get(i);
				if (i == 0) {
					continue;
				} else if ( 1 <= i && i <=defender) {
					defenderVOList.add(tmpVO);
				} else if ( defender < i && i <= defender + midfilder) {
					midfilderVOList.add(tmpVO);
				} else if (defender + midfilder < i && i <= defender + midfilder + forward) {
					forwardVOList.add(tmpVO);
				}
			}
			
			model.addAttribute("teamType", teamType);
			model.addAttribute("formation", formation);
			model.addAttribute("channelPlayVO", channelPlayVO);
			model.addAttribute("teamPlayerVOList", teamPlayerVOList);
			model.addAttribute("goalkeeperVO", goalkeeperVO);
			model.addAttribute("defenderVOList", defenderVOList);
			model.addAttribute("midfilderVOList", midfilderVOList);
			model.addAttribute("forwardVOList", forwardVOList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		return "channel/channel_formation_modify";
//		return "test";
	}
	
	// 영상 게시글에서 포메이션을 변경하는 메서드 (player의 formationIdx 변경 포함)
	@RequestMapping(value = "/channel/play/formation/{channelIdx}/{channelPlayIdx}/{teamType}", method = RequestMethod.PUT)
	public String updateChannelPlayFormation(	@PathVariable int channelIdx,
												@PathVariable int channelPlayIdx,
												@PathVariable String teamType,
												String formation,
												TeamPlayerVO teamPlayerVO,
												RedirectAttributes attributes) throws Exception{
		try {
			// 잘 들어왔는지 확인
			System.out.println("==========파라미터 확인=============");
			System.out.println("channelIdx : " + channelIdx);
			System.out.println("channelPlayIdx : " + channelPlayIdx);
			System.out.println("teamType : " + teamType);
			System.out.println("formation : " + formation); //?
			int x = 0;
			for (TeamPlayerVO item : teamPlayerVO.getTeamPlayerVOList()) {
				System.out.println(x + "번째");
				System.out.println("teamPlayerIdx : " + item.getTeamPlayerIdx());
				System.out.println("teamPlayerFormationNumber : " + item.getTeamPlayerFormationNumber());
				x++;
			}
			
			HashMap<String, String> updateFormationInfoMap = new HashMap<>();
			updateFormationInfoMap.put("teamType", teamType);
			updateFormationInfoMap.put("formation", formation);
			updateFormationInfoMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
			channelPlayService.updateChannelPlayFormation(updateFormationInfoMap); // 경기의 formation 변경 메서드
			
			// formation에 맞게 선수들의 position 변경
			String forward = "F";
			String midfilder = "M";
			String defender = "D";
			String goalkeeper = "G";
			
			// formation을 나누는 작업
			// formation이 1자리인 경우 : 전부 공격수
			// formation이 2자리인 경우 : 1자리 - 수비수, 2자리 - 공격수
			// formation이 3자리 이상인 경우 : 1자리 - 수비수, 2자리 - 미드필더 3자리 이상은 무조건 공격수
			
			List<TeamPlayerVO> teamPlayerVOList = teamPlayerVO.getTeamPlayerVOList();
			for (int i = 0; i < teamPlayerVOList.size(); i++) {
				TeamPlayerVO tmpVO = teamPlayerVOList.get(i);
				if (tmpVO.getTeamPlayerIdx() <= 0) {
					continue;
				} else {
					// formation을 나누는 작업
					// formation이 1자리인 경우 : 전부 공격수
					// formation이 2자리인 경우 : 1자리 - 수비수, 2자리 - 공격수
					// formation이 3자리 이상인 경우 : 1자리 - 수비수, 2자리 - 미드필더 3자리 이상은 무조건 공격수
					
					if (formation.length() == 1) {
						if (i == 0) {
							tmpVO.setTeamPlayerPosition(goalkeeper);
						} else if (i > 0) {
							tmpVO.setTeamPlayerPosition(forward);
						}
					} else if (formation.length() == 2) {
						if (i == 0) {
							tmpVO.setTeamPlayerPosition(goalkeeper);
						} else if (0 < i && i <= Character.getNumericValue(formation.charAt(1))) {
							tmpVO.setTeamPlayerPosition(defender);
						} else if (Character.getNumericValue(formation.charAt(1)) < i) {
							tmpVO.setTeamPlayerPosition(forward);
						}
					} else if (formation.length() >= 3) {
						if (i == 0) {
							tmpVO.setTeamPlayerPosition(goalkeeper);
						} else if (0 < i && i <= Character.getNumericValue(formation.charAt(1))) {
							tmpVO.setTeamPlayerPosition(defender);
						} else if (Character.getNumericValue(formation.charAt(1)) < i && i <=Character.getNumericValue(formation.charAt(1)) + Character.getNumericValue(formation.charAt(2))) {
							tmpVO.setTeamPlayerPosition(midfilder);
						} else if (Character.getNumericValue(formation.charAt(1)) + Character.getNumericValue(formation.charAt(2)) < i) {
							tmpVO.setTeamPlayerPosition(forward);
						}
					}
					System.out.println("tmpVO - position : " + tmpVO.getTeamPlayerPosition());
					teamPlayerService.updateTeamPlayerFormation(tmpVO); // 선수의 formation 변경 메서드
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
			return "index";
		}
		attributes.addAttribute("message", "포메이션이 변경되었습니다");
		return "redirect:/channel/play/formation/" + channelIdx + "/" + channelPlayIdx;
	}
	
	// 영상 게시글에서 Player를 삭제하는 메서드
	@RequestMapping(value = "/channel/play/player/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.DELETE)
	public String deleteTeamPlayer(	RedirectAttributes attributes,
									@PathVariable int channelIdx,
									@PathVariable int channelPlayIdx,
									@PathVariable int teamPlayerIdx,
									TeamPlayerVO teamPlayerVO) throws Exception{
		try {
			teamPlayerService.deleteTeamPlayer(teamPlayerVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		attributes.addAttribute("message", "선수가 삭제되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
//		return "test";
	}
	
	// 영상 게시글에 저장된 Player 리스트를 출력 + player들의 playresult 리스트 출력(다른 부분에 들어갈 수 있으므로 축소화)
//	@RequestMapping(value = "channel/{channelIdx}/play/{channelPlayIdx}/player", method = RequestMethod.GET)
//	public String selectTeamPlayerList(Model model, @PathVariable int channelPlayIdx) throws Exception{
//		try {
//			List<TeamPlayerVO> teamPlayerVOList = teamPlayerService.selectTeamPlayerList(channelPlayIdx); // player 리스트
//			List<PlayresultVO> playerResultVOList = teamPlayerService.selectPlayerResultVOList(channelPlayIdx); // player들의 playresult 리스트
//			
//			model.addAttribute("teamPlayerVOList", teamPlayerVOList);
//			model.addAttribute("playerResultVOList", playerResultVOList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
	
	// 영상 게시글에 저장된 Player 리스트에서 Player를 클릭했을 때 나타나는 Player의 세부사항
	@RequestMapping(value = "/channel/play/player/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.GET)
	public String selectTeamPlayerDetail(	Model model,
											@PathVariable int channelIdx,
											@PathVariable int channelPlayIdx,
											@PathVariable int teamPlayerIdx) throws Exception {
		try {
			HashMap<String, Integer> teamPlayerRequireMap = new HashMap<>();
			teamPlayerRequireMap.put("channelPlayIdx", channelPlayIdx);
			teamPlayerRequireMap.put("teamPlayerIdx", teamPlayerIdx);
			
			TeamPlayerVO teamPlayerVO = teamPlayerService.selectTeamPlayerDetail(teamPlayerRequireMap);
			
			PlayresultVO currentPlayresultVO = teamPlayerService.selectTeamPlayerCurrentResult(teamPlayerVO);
			
			model.addAttribute("teamPlayerVO", teamPlayerVO);
			model.addAttribute("currentPlayresultVO", currentPlayresultVO);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "오류가 발생했습니다.");
//			return "index";
		}
		
		return "";
//		return "test";
	}
	
	// 영상 게시글에 저장된 Player의 경기 기록을 보여주는 메서드
	@RequestMapping(value = "/channel/play/player/result/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.GET)
	public String selectPlayresultBeforeUpdate(	@PathVariable int channelIdx,
												@PathVariable int channelPlayIdx,
												@PathVariable int teamPlayerIdx,
												RedirectAttributes attributes, 
												Model model) throws Exception {
		try {
			HashMap<String, Integer> teamPlayerRequireMap = new HashMap<>();
			teamPlayerRequireMap.put("channelPlayIdx", channelPlayIdx);
			teamPlayerRequireMap.put("teamPlayerIdx", teamPlayerIdx);
			
			TeamPlayerVO teamPlayerVO = teamPlayerService.selectTeamPlayerDetail(teamPlayerRequireMap); // 선수의 정보를 가져옴
			UserVO userVO = userService.userInfo(teamPlayerVO.getUserIdx()); // 선수로 연결된 사용자의 정보 (전화번호, 이메일)를 가져옴
			
			MemberVO tmpVO = new MemberVO();
			tmpVO.setChannelIdx(channelIdx);
			tmpVO.setUserIdx(teamPlayerVO.getUserIdx());
			
			MemberVO memberVO = memberService.selectMemberDetail(tmpVO);
			PlayresultVO playresultVO = teamPlayerService.selectPlayerresultVODetail(teamPlayerIdx);
			
			model.addAttribute("teamPlayerVO", teamPlayerVO);
			model.addAttribute("userVO", userVO);
			model.addAttribute("memberVO", memberVO);
			model.addAttribute("playresultVO", playresultVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "오류가 발생했습니다.");
			return "redirect:/channel/play/player/" + channelIdx + "/" + channelPlayIdx + "/" + teamPlayerIdx;
		}
		return "";
	}
	
	// 영상 게시글에 저장된 Player의 경기 기록을 수정하는 메서드
	@RequestMapping(value = "/channel/play/player/result/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.PUT)
	public String updatePlayresult(	RedirectAttributes attributes,
									@PathVariable int channelIdx,
									@PathVariable int channelPlayIdx,
									@PathVariable int teamPlayerIdx,
									PlayresultVO playresultVO) throws Exception {
		try {
			teamPlayerService.updatePlayresult(playresultVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "오류가 발생했습니다.");
		}
		attributes.addAttribute("message", "선수의 기록이 수정되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx + "/" + teamPlayerIdx;
	}
	
}
