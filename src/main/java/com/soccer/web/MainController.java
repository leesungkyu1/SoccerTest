package com.soccer.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soccer.web.channel.service.ChannelServiceImpl;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.notice.service.NoticeService;
import com.soccer.web.notice.service.NoticeServiceImpl;
import com.soccer.web.notice.vo.NoticeDefaultVO;
import com.soccer.web.notice.vo.NoticeVO;
import com.soccer.web.user.vo.UserVO;

@Controller
public class MainController {
	
	@Autowired
	ChannelServiceImpl channelService;
	
	@Autowired
	NoticeServiceImpl noticeService;
	
	//로그인 페이지
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpSession session) throws Exception{
		UserVO userVO = (UserVO)session.getAttribute("loginUser");
		
		if(userVO != null) {
			session.removeAttribute("loginUser");
		}
		
		return "main/welcome";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main (Model model, NoticeVO pageVO) throws Exception {
		ChannelVO channelVO = new ChannelVO();
		NoticeVO noticeVO = new NoticeVO();
		
		int totcnt = noticeService.selectNoticeListTotCnt(noticeVO);
		
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
			
		List<ChannelVO> channelList = channelService.channelList(channelVO);
		List<NoticeVO> noticeList = noticeService.selectNoticeList(pageVO);
		List<NoticeVO> importantNoticeList = noticeService.selectImportantNoticeList(noticeVO);
		
		model.addAttribute("channelList", channelList);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("importantNoticeList",importantNoticeList);
		model.addAttribute("page", pageVO);
		
		return "main/main";
	}
	
	@ResponseBody
	@RequestMapping(value = "/mainChannelImage", method = RequestMethod.GET)
	public ResponseEntity<byte[]> mainChannelImage(String channelImage) throws Exception {
		HttpHeaders header = new HttpHeaders();
		
		header.setContentType(MediaType.IMAGE_PNG);
		
		return new ResponseEntity<byte[]>(IOUtils.toByteArray(new FileInputStream(new File(channelImage))), header, HttpStatus.CREATED);
	}
}
