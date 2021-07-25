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
	public String selectNoticeList(NoticeVO pageVO, Model model, @RequestParam(required = false) String message) throws Exception {
		try {
			NoticeVO noticeVO = new NoticeVO();
			int totcnt = noticeService.selectNoticeListTotCnt(noticeVO);
			
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
			
			List<NoticeVO> noticeList = noticeService.selectNoticeList(pageVO);
			List<NoticeVO> importantNoticeList = noticeService.selectImportantNoticeList(noticeVO);
			
			model.addAttribute("importantNoticeList", importantNoticeList);
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("page", pageVO);
			
			if (message != null) {
				model.addAttribute("message", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main/notice/notice";
	}
	
	// 글 작성 폼
	@RequestMapping(value = {"/main/notice/write/{noticeIdx}", "/main/notice/write"}, method = RequestMethod.GET)
	public String selectNoticeDetailWrite(	@PathVariable(required = false) Integer noticeIdx,
											Model model
											) throws Exception {
		try {
			if (noticeIdx != null) {
				NoticeVO noticeVO = noticeService.selectNoticeDetail(noticeIdx);
				model.addAttribute("noticeVO", noticeVO);

				String nlString = "<br>";
				model.addAttribute("nlString", nlString);
				String nrString = "\r\n";
				model.addAttribute("nrString", nrString);
			} else {
				NoticeVO noticeVO = new NoticeVO();
				model.addAttribute("noticeVO", noticeVO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "main/notice/notice_writing";
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.GET)
	public String selectNoticeDetail(@PathVariable("noticeIdx") int noticeIdx, Model model, RedirectAttributes attributes) throws Exception {
		try {
			NoticeVO noticeVO = noticeService.selectNoticeDetail(noticeIdx);
			String nlString = "\"&lt;br&gt\"";
			model.addAttribute("nlString", nlString);
			if (noticeVO.getNoticeTitle() == null) {
				attributes.addAttribute("message", "존재하지 않는 글입니다.");
				return "redirect:/main/notice";
			}
			model.addAttribute("noticeVO", noticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
			return "redirect:/main/notice";
		}
		return "/main/notice/notice_detail";
	}
	
	@RequestMapping(value = "/main/notice", method = RequestMethod.POST)
	public String insertNotice(NoticeVO noticeVO, RedirectAttributes attributes) throws Exception {
		try {
			String noticeDesc = noticeVO.getNoticeDesc().replaceAll("\r\n", "<br>");
			noticeVO.setNoticeDesc(noticeDesc);
			noticeService.insertNotice(noticeVO);
		}catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		int noticeIdx = noticeVO.getNoticeIdx();
		attributes.addAttribute("message","공지를 추가했습니다.");
		return "redirect:/main/notice/" + noticeIdx;
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.PUT)
	public String updateNotice(@PathVariable int noticeIdx, NoticeVO noticeVO, RedirectAttributes attributes) throws Exception {
		try {
			String noticeDesc = noticeVO.getNoticeDesc().replaceAll("\r\n", "<br>");
			noticeVO.setNoticeDesc(noticeDesc);
			noticeService.updateNotice(noticeVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		attributes.addAttribute("message","공지를 수정했습니다.");
		return "redirect:/main/notice/" + noticeIdx;
	}
	
	@RequestMapping(value = "/main/notice/{noticeIdx}", method = RequestMethod.DELETE)
	public String deleteNotice(@PathVariable int noticeIdx, RedirectAttributes attributes) throws Exception {
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
