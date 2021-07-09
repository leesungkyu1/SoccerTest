package com.soccer.web.channel.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.board.service.ChannelBoardService;
import com.soccer.web.channel.board.vo.ChannelBoardVO;

@RestController
public class ChannelBoardController {

	@Autowired
	private ChannelBoardService channelBoardService;
	
	@RequestMapping(value = "/channel/board", method = RequestMethod.GET)
	public String selectChannelBoardList(ChannelBoardVO channelBoardVO, Model model, @RequestParam(required = false) String message) {
		int totcnt = channelBoardService.selectChannelBoardListTotCnt(channelBoardVO);
		
		List<ChannelBoardVO> channelBoardList = channelBoardService.selectChannelBoardList(channelBoardVO);
		model.addAttribute("channelBoardList", channelBoardList);
		if (message != null) {
			model.addAttribute("message", message);
		}
		
		return "";
	}
	
	@RequestMapping(value = "/channel/board/{channelBoardIdx}", method = RequestMethod.GET)
	public String selectChannelBoardDetail(@PathVariable("channelBoardIdx") int channelBoardIdx, Model model) {
		
		ChannelBoardVO channelBoardVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
		
		model.addAttribute("channelBoardVO", channelBoardVO);
		
		return "";
	}
	
	@RequestMapping(value = "/channel/board", method = RequestMethod.POST)
	public String insertChannelBoard(ChannelBoardVO channelBoardVO, RedirectAttributes attributes) {
		try {
			channelBoardService.insertChannelBoard(channelBoardVO);			
		}catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","게시글을 추가했습니다.");
		
		return "redirect:/channel/board";
	}
	
	@RequestMapping(value = "/channel/board", method = RequestMethod.PUT)
	public String updateChannelBoard(ChannelBoardVO channelBoardVO, RedirectAttributes attributes) {
		try {
			channelBoardService.updateChannelBoard(channelBoardVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","게시글을 수정했습니다.");
		return "redirect:/channel/board/" + channelBoardVO.getChannelBoardIdx();
	}
	
	@RequestMapping(value = "/channel/board", method = RequestMethod.DELETE)
	public String deleteChannelBoard(int channelBoardIdx, RedirectAttributes attributes) {
		try {
			channelBoardService.deleteChannelBoard(channelBoardIdx);			
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","게시글을 삭제했습니다.");
		return "redirect:/channel/board";
	}
}
