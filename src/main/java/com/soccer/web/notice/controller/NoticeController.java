package com.soccer.web.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.notice.service.NoticeService;
import com.soccer.web.notice.vo.NoticeVO;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping(value = "/main/notice", method = RequestMethod.GET)
	public String selectNoticeList(NoticeVO noticeVO, Model model, @RequestParam(required = false) String message) {
		int totcnt = noticeService.selectNoticeListTotCnt(noticeVO);
		
		List<NoticeVO> noticeList = noticeService.selectNoticeList(noticeVO);
		model.addAttribute("noticeList", noticeList);
		if (message != null) {
			model.addAttribute("message", message);
		}
		
		return "";
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.GET)
	public String selectNoticeDetail(@PathVariable("noticeIdx") int noticeIdx, Model model) {
		
		NoticeVO noticeVO = noticeService.selectNoticeDetail(noticeIdx);
		
		model.addAttribute("noticeVO", noticeVO);
		
		return "";
	}
	
	@RequestMapping(value = "/main/notice", method = RequestMethod.POST)
	public String insertNotice(NoticeVO noticeVO, RedirectAttributes attributes) {
		int noticeIdx = 0;
		try {
			noticeIdx = noticeService.insertNotice(noticeVO);
		}catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","공지를 추가했습니다.");
		
		return "redirect:/main/notice/" + noticeIdx;
//		return "test";
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.PUT)
	public String updateNotice(@PathVariable int noticeIdx, NoticeVO noticeVO, RedirectAttributes attributes) {
		try {
			noticeService.updateNotice(noticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","공지를 수정했습니다.");
		return "redirect:/main/notice/" + noticeIdx;
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.DELETE)
	public String deleteNotice(@PathVariable int noticeIdx, RedirectAttributes attributes) {
		try {
			noticeService.deleteNotice(noticeIdx);			
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","공지를 삭제했습니다.");
		return "redirect:/main/notice";
	}
}
