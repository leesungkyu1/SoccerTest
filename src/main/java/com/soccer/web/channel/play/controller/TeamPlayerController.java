package com.soccer.web.channel.play.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.member.service.MemberService;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.play.service.TeamPlayerService;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.vo.ChannelVO;

@RestController
public class TeamPlayerController {

	@Autowired
	private TeamPlayerService teamPlayerService;
	
	@Autowired
	private MemberService memberService;
	
	// 영상 게시글에서 Player를 추가할 때 나오는 채널 멤버의 리스트 출력
	@RequestMapping(value = "channel/play/member/{channelIdx}/{channelPlayIdx}", method = RequestMethod.GET)
	public String selectMemberList(@PathVariable int channelIdx, @PathVariable int channelPlayIdx, Model model) throws Exception {
		try {
			ChannelVO channelVO = new ChannelVO();
			channelVO.setChannelIdx(channelIdx);
			List<MemberVO> memberVOList = memberService.memberList(channelVO);
			model.addAttribute("memberList", memberVOList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "에러가 발생했습니다.");
		}
		return "";
	}
	
	// 영상 게시글에서 Player를 추가하는 메서드
	@RequestMapping(value = "channel/play/player/{channelIdx}/{channelPlayIdx}", method = RequestMethod.POST)
	public String insertTeamPlayer(RedirectAttributes attributes,
								@PathVariable int channelIdx,
								@PathVariable int channelPlayIdx, 
								@RequestParam("userIdx") int userIdx,
								TeamPlayerVO teamPlayerVO) throws Exception {
		try {
			HashMap<String, String> teamPlayParameterMap = new HashMap<>();
			teamPlayParameterMap.put("userIdx", Integer.toString(userIdx));
			teamPlayParameterMap.put("userIdx", Integer.toString(channelPlayIdx));
			teamPlayParameterMap.put("teamPlayerPosition", teamPlayerVO.getTeamPlayerPosition());
			
			int teamPlayerIdx = teamPlayerService.insertTeamPlayer(teamPlayParameterMap); // 선수 추가
			
			HashMap<String, Integer> playresultMap = new HashMap<>();
			playresultMap.put("channelPlayIdx", channelPlayIdx);
			playresultMap.put("teamPlayerIdx", teamPlayerIdx);
			
			teamPlayerService.insertPlayresult(playresultMap); // 선수의 경기 결과 추가
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		
		attributes.addAttribute("message", "선수가 등록되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
	}
	
	// 영상 게시글에서 Player의 position을 변경하는 메서드
	@RequestMapping(value = "channel/play/player/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.PUT)
	public String updateTeamPlayerPosition(	RedirectAttributes attributes,
											@PathVariable int channelIdx,
											@PathVariable int channelPlayIdx,
											@PathVariable int teamPlayerIdx,
											TeamPlayerVO teamPlayerVO) throws Exception {
		try {
			
			teamPlayerService.updateTeamPlayerPosition(teamPlayerVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message", "선수의 포지션이 수정되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
	}
	
	// 영상 게시글에서 Player를 삭제하는 메서드
	@RequestMapping(value = "channel/play/player/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.DELETE)
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
		}
		attributes.addAttribute("message", "선수가 삭제되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
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
	@RequestMapping(value = "channel/play/player/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.GET)
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
			
			model.addAttribute("currentPlayresultVO", currentPlayresultVO);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "오류가 발생했습니다.");
		}
		
		return "";
	}
	
	// 영상 게시글에 저장된 Player의 경기 기록을 수정하는 메서드
	@RequestMapping(value = "channel/play/player/result/{channelIdx}/{channelPlayIdx}/{teamPlayerIdx}", method = RequestMethod.PUT)
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
