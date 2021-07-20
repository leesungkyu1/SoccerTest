package com.soccer.web.channel.play.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import com.soccer.web.channel.vo.ChannelVO;
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
//	@RequestMapping(value = "/channel/play/player/{channelIdx}/{channelPlayIdx}", method = RequestMethod.POST)
//	public String insertTeamPlayer(RedirectAttributes attributes,
//								@PathVariable int channelIdx,
//								@PathVariable int channelPlayIdx, 
//								@RequestParam("userIdx") int userIdx,
//								TeamPlayerVO teamPlayerVO) throws Exception {
//		try {
//			HashMap<String, String> teamPlayParameterMap = new HashMap<>();
//			teamPlayParameterMap.put("userIdx", Integer.toString(userIdx));
//			teamPlayParameterMap.put("teamIdx", Integer.toString(teamIdx));
//			teamPlayParameterMap.put("channelPlayIdx", Integer.toString(channelPlayIdx));
//			teamPlayParameterMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
//			
//			int teamPlayerIdx = teamPlayerService.insertTeamPlayer(teamPlayParameterMap); // 선수 추가
//			
//			HashMap<String, Integer> playresultMap = new HashMap<>();
//			playresultMap.put("channelPlayIdx", channelPlayIdx);
//			playresultMap.put("teamIdx", teamIdx);
//			playresultMap.put("teamPlayerIdx", teamPlayerIdx);
//			
//			teamPlayerService.insertPlayresult(playresultMap); // 선수의 경기 결과 추가
//		} catch (Exception e) {
//			e.printStackTrace();
//			attributes.addAttribute("message", "에러가 발생했습니다.");
//		}
//		
//		attributes.addAttribute("message", "선수가 등록되었습니다.");
//		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
//	}
	
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
			
			List<ChannelVO> channelVOList = channelService.selectChannelList(channelPlayIdx);
			List<TeamVO> teamVOList = channelPlayService.selectTeamList(channelPlayIdx);
			List<TeamPlayerVO> homeTeamPlayerVOList = teamPlayerService.selectHomeAwayTeamPlayerList(homeTeamInfo);
			List<TeamPlayerVO> awayTeamPlayerVOList = teamPlayerService.selectHomeAwayTeamPlayerList(awayTeamInfo);
			
			model.addAttribute("channelVOList", channelVOList);
			model.addAttribute("teamVOList", teamVOList);
			model.addAttribute("homeTeamPlayerVOList", homeTeamPlayerVOList);
			model.addAttribute("awayTeamPlayerVOList", awayTeamPlayerVOList);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		return "";
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
			
			model.addAttribute("formation", formation);
			model.addAttribute("channelPlayVO", channelPlayVO);
			model.addAttribute("teamPlayerVOList", teamPlayerVOList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		return "";
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
					teamPlayerService.updateTeamPlayerFormation(tmpVO); // 선수의 formation 변경 메서드
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message", "포메이션이 변경되었습니다");
		return "redirect:/";
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
