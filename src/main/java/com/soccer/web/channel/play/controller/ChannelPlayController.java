package com.soccer.web.channel.play.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.play.service.ChannelPlayService;
import com.soccer.web.channel.play.service.TeamPlayerService;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;

@RestController
public class ChannelPlayController {

	@Autowired
	private ChannelPlayService channelPlayService;
	
	@Autowired
	private TeamPlayerService teamPlayerService;
	
	// 채널 게시글의 리스트를 보여주는 메서드 (페이징처리는 나중에)
	@RequestMapping(value = "channel/{channelIdx}/play", method = RequestMethod.GET)
	public String selectChannelPlayList(@PathVariable int channelIdx,
										ChannelPlayVO channelPlayVO,
										Model model,
										@RequestParam(required = false) String message) throws Exception {
		try {
			int totcnt = channelPlayService.selectChannelPlayListTotCnt(channelPlayVO);
			
			List<ChannelPlayVO> channelPlayList = channelPlayService.selectChannelPlayList(channelPlayVO);
			model.addAttribute("channelPlayList", channelPlayList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (message != null) {
			model.addAttribute("message", message);
		}
		
		return "";
	}
	
	// 영상 게시글에 저장된 Player 리스트를 출력 + player들의 playresult 리스트 출력 + 영상 출력
	@RequestMapping(value = "channel/{channelIdx}/play/{channelPlayIdx}", method = RequestMethod.GET)
	public String selectChannelPlayDetail(	@PathVariable int channelIdx,
											@PathVariable int channelPlayIdx,
											RedirectAttributes attributes,
											Model model) throws Exception{
		try {
			ChannelPlayVO channelPlayVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			List<TeamPlayerVO> teamPlayerVOList = teamPlayerService.selectTeamPlayerList(channelPlayIdx); // player 리스트
			List<PlayresultVO> playerResultVOList = teamPlayerService.selectPlayerResultVOList(channelPlayIdx); // player들의 playresult 리스트
			
			//TODO 영상 게시글의 영상 작업 필요
			
			model.addAttribute("channelPlayVO", channelPlayVO);
			model.addAttribute("teamPlayerVOList", teamPlayerVOList);
			model.addAttribute("playerResultVOList", playerResultVOList);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
			return "redirect:/channel/" + channelIdx + "/play";
		}
		return "";
	}
	
	// 영상 게시글을 추가하는 메서드
	@RequestMapping(value = "channel/{channelIdx}/play", method = RequestMethod.POST)
	public String insertChannelPlay(@PathVariable int channelIdx,
									ChannelPlayVO channelPlayVO,
									RedirectAttributes attributes) throws Exception {
		int channelPlayIdx = 0;
		try {
			//TODO 영상을 실제 DB에 추가하는 메서드를 구현해야 함
//			channelPlayVO.setChannelIdx(channelIdx); // 혹시나 필요할 때 사용하기
			channelPlayIdx = channelPlayService.insertChannelPlay(channelPlayVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
		}
		attributes.addAttribute("message", "영상이 성공적으로 등록되었습니다.");
		return "redirect:/channel/" + channelIdx + "/play/" + channelPlayIdx;
	}
	
	// 영상 게시글을 수정하는 메서드 (게시글을 수정할 때 승인중 단계로 다시 돌아감)
	@RequestMapping(value = "channel/{channelIdx}/play/{channelPlayIdx}", method = RequestMethod.PUT)
	public String updateChannelPlay(@PathVariable int channelIdx,
									@PathVariable int channelPlayIdx,
									ChannelPlayVO channelPlayVO,
									RedirectAttributes attributes) throws Exception {
		try {
			//TODO 기존에 있는 영상을 삭제하고 새로운 영상을 실제 DB에 추가하는 메서드를 구현해야 함
			channelPlayService.updateChannelPlay(channelPlayVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
		}
		attributes.addAttribute("message", "영상이 성공적으로 수정되었습니다.");
		return "redirect:/channel/" + channelIdx + "/play/" + channelPlayIdx;
	}
	
	// 영상 게시글을 삭제하는 메서드
	@RequestMapping(value = "channel/{channelIdx}/play/{channelPlayIdx}", method = RequestMethod.DELETE)
	public String deleteChannelPlay(@PathVariable int channelIdx,
									@PathVariable int channelPlayIdx,
									RedirectAttributes attributes) throws Exception {
		try {
			//TODO 기존에 있는 영상을 삭제하는 메서드를 구현해야 함
			channelPlayService.deleteChannelPlay(channelPlayIdx);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
		}
		attributes.addAttribute("message", "영상이 삭제되었습니다.");
		return "redirect:/channel/" + channelIdx + "/play";
	}
}
