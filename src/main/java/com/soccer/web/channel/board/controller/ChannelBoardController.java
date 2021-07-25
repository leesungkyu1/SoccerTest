package com.soccer.web.channel.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.board.service.ChannelBoardService;
import com.soccer.web.channel.board.vo.ChannelBoardVO;

@Controller
public class ChannelBoardController {

	@Autowired
	private ChannelBoardService channelBoardService;
	
	@RequestMapping(value = "/channel/board/{channelIdx}", method = RequestMethod.GET)
	public String selectChannelBoardList(	ChannelBoardVO pageVO,
											@PathVariable int channelIdx,
											Model model,
											@RequestParam(required = false) String message) throws Exception {
		try {
			ChannelBoardVO channelBoardVO = new ChannelBoardVO();
			channelBoardVO.setChannelIdx(channelIdx);
			
			int totcnt = channelBoardService.selectChannelBoardListTotCnt(channelBoardVO);
			
			//총 게시물
			pageVO.setTotalListCnt(totcnt);
			//총 페이지
			pageVO.setTotalPageCnt((int) Math.ceil(pageVO.getTotalListCnt() * 1.0 / pageVO.getPageSize()));
			//게시글 번호
			pageVO.setStartIndex((pageVO.getPage() - 1) * pageVO.getBlockSize()); 
			//시작 페이지 번호
			pageVO.setStartPage(pageVO.getPage() - (pageVO.getPage() - 1) % pageVO.getBlockSize());
			//끌 페이지 번호
			pageVO.setEndPage(pageVO.getStartPage() + pageVO.getBlockSize() - 1);
			//가져오는 게시물 인덱스 시작번호
			pageVO.setStartIndex((pageVO.getPage() - 1) * pageVO.getPageSize());
			
			if(pageVO.getEndPage() > pageVO.getTotalPageCnt()) {
				pageVO.setEndPage(pageVO.getTotalPageCnt());
			}
			
			List<ChannelBoardVO> channelBoardList = channelBoardService.selectChannelBoardList(pageVO);
			List<ChannelBoardVO> importantChannelBoardList = channelBoardService.selectImportantChannelBoardList(channelBoardVO);
			
			model.addAttribute("importantChannelBoardList", importantChannelBoardList);
			model.addAttribute("channelBoardList", channelBoardList);
			model.addAttribute("page", pageVO);
			
			if (message != null) {
				model.addAttribute("message", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "channel/board/channel_board";
	}
	
	// 글 작성 폼
	@RequestMapping(value = {"/channel/board/write/{channelIdx}/{channelBoardIdx}", "/channel/board/write/{channelIdx}"}, method = RequestMethod.GET)
	public String selectChannelBoardDetailWrite(@PathVariable Integer channelIdx,
												@PathVariable(required = false) Integer channelBoardIdx,
												Model model
												) throws Exception {
		try {
			if (channelBoardIdx != null) {
				ChannelBoardVO channelBoardVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
				model.addAttribute("channelBoardVO", channelBoardVO);

				String nlString = "<br>";
				model.addAttribute("nlString", nlString);
				String nrString = "\r\n";
				model.addAttribute("nrString", nrString);
			} else {
				ChannelBoardVO channelBoardVO = new ChannelBoardVO();
				channelBoardVO.setChannelIdx(channelIdx);
				model.addAttribute("channelBoardVO", channelBoardVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "channel/board/channel_board_writing";
	}
	
	@RequestMapping(value = "/channel/board/{channelIdx}/{channelBoardIdx}", method = RequestMethod.GET)
	public String selectChannelBoardDetail(@PathVariable int channelIdx, @PathVariable("channelBoardIdx") int channelBoardIdx, Model model, RedirectAttributes attributes) throws Exception {
		try {
			ChannelBoardVO channelBoardVO = channelBoardService.selectChannelBoardDetail(channelBoardIdx);
			
			model.addAttribute("channelBoardVO", channelBoardVO);
			System.out.println(channelBoardVO.toString());
			String nlString = "\"&lt;br&gt\"";
			model.addAttribute("nlString", nlString);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "등록된 글이 없습니다");
			return "redirect:/channel/board/" + channelIdx;
		}
		return "channel/board/channel_board_detail";
	}
	
	@RequestMapping(value = "/channel/board/{channelIdx}", method = RequestMethod.POST)
	public String insertChannelBoard(@PathVariable int channelIdx, ChannelBoardVO channelBoardVO, RedirectAttributes attributes) throws Exception {
		try {
			String channelBoardDesc = channelBoardVO.getChannelBoardDesc().replaceAll("\r\n", "<br>");
			channelBoardVO.setChannelBoardDesc(channelBoardDesc);
			channelBoardService.insertChannelBoard(channelBoardVO);
		}catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		int channelBoardIdx = channelBoardVO.getChannelBoardIdx();
		attributes.addAttribute("message","게시글을 추가했습니다.");
		
		return "redirect:/channel/board/" + channelIdx + "/" + channelBoardIdx;
	}
	
	@RequestMapping(value = "/channel/board/{channelIdx}/{channelBoardIdx}", method = RequestMethod.PUT)
	public String updateChannelBoard(@PathVariable int channelIdx, @PathVariable int channelBoardIdx, ChannelBoardVO channelBoardVO, RedirectAttributes attributes) throws Exception {
		try {
			String channelBoardDesc = channelBoardVO.getChannelBoardDesc().replaceAll("\r\n", "<br>");
			channelBoardVO.setChannelBoardDesc(channelBoardDesc);
			channelBoardService.updateChannelBoard(channelBoardVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","게시글을 수정했습니다.");
		return "redirect:/channel/board/" + channelIdx + "/" + channelBoardIdx;
	}
	
	@RequestMapping(value = "/channel/board/{channelIdx}/{channelBoardIdx}", method = RequestMethod.DELETE)
	public String deleteChannelBoard(@PathVariable int channelIdx, @PathVariable int channelBoardIdx, RedirectAttributes attributes) throws Exception {
		try {
			channelBoardService.deleteChannelBoard(channelBoardIdx);			
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","게시글을 삭제했습니다.");
		return "redirect:/channel/board/" + channelIdx;
	}
}
