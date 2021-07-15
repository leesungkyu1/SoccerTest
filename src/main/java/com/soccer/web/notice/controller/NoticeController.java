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
public class NoticeController { // main 공지사항은 무조건 관리자만 쓸 수 있으므로 글쓴이는 무조건 관리자로

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
//		return "test";
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.GET)
	public String selectNoticeDetail(@PathVariable("noticeIdx") int noticeIdx, Model model) {
		
		NoticeVO noticeVO = noticeService.selectNoticeDetail(noticeIdx);
		
		model.addAttribute("noticeVO", noticeVO);
		
		return "";
//		return "test";
	}
	
	@RequestMapping(value = "/main/notice", method = RequestMethod.POST)
	public String insertNotice(NoticeVO noticeVO, RedirectAttributes attributes) {
		try {
			noticeService.insertNotice(noticeVO);
		}catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","공지를 추가했습니다.");
//		System.out.println("noticeVO - noticeIdx : " + noticeVO.getNoticeIdx());
		return "redirect:/main/notice/" + noticeVO.getNoticeIdx();
//		return "test";
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.PUT)
	public String updateNotice(@PathVariable int noticeIdx, NoticeVO noticeVO, RedirectAttributes attributes) {
		try {
			noticeService.updateNotice(noticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		attributes.addAttribute("message","공지를 수정했습니다.");
		return "redirect:/main/notice/" + noticeIdx;
//		return "test";
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.DELETE)
	public String deleteNotice(@PathVariable int noticeIdx, RedirectAttributes attributes) {
		try {
			noticeService.deleteNotice(noticeIdx);			
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
//			return "index";
		}
		attributes.addAttribute("message","공지를 삭제했습니다.");
		return "redirect:/main/notice";
//		return "test";
	}
}
