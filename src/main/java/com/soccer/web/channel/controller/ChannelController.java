package com.soccer.web.channel.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.board.service.ChannelBoardServiceImpl;
import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.member.service.MemberServiceImpl;
import com.soccer.web.channel.play.service.ChannelPlayServiceImpl;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.ViewResultColumnVO;
import com.soccer.web.channel.service.ChannelServiceImpl;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.payment.service.PaymentServiceImpl;
import com.soccer.web.payment.vo.PaymentVO;
import com.soccer.web.user.vo.UserVO;

@Controller
public class ChannelController {
	
	@Autowired
	ChannelServiceImpl channelService;
	
	@Autowired
	PaymentServiceImpl paymentService;
	
	@Autowired
	MemberServiceImpl memberService;
	
	@Autowired
	ChannelBoardServiceImpl channelBoardService;
	
	@Autowired
	ChannelPlayServiceImpl channelPlayService;
	
	//추후 채널용 이미지 파일 경로
	private final String CHANNEL_IMAGE_DIR = "c:"; 
	
	//채널 목록, 검색 목록 (페이징 처리 안했음) 쿼리 전달
	@RequestMapping(value = "/main/channel", method = RequestMethod.GET)
	public String channelList(ChannelVO channelVO, Model model) throws Exception {
		List<ChannelVO> channelList = channelService.channelList(channelVO);
		
		if(channelList == null) {
			model.addAttribute("message", "검색 결과가 없습니다.");
		}else {
			model.addAttribute("channelList", channelList);			
		}
		
		return "";
	}
	
	//채널 생성
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/channel", method = RequestMethod.POST)
	public String channelInsert (ChannelVO channelVO, @RequestParam("imageFile")MultipartFile imageFile, 
			RedirectAttributes attributes) throws Exception {
		FileUtils saveDir = new FileUtils();
		
		try {
			saveDir.forceMkdir(new File(CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx()));
			
			String fileName = imageFile.getOriginalFilename();
			String saveImageFileDir = CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx() + "/" +  fileName;
			
			File saveImageFile = new File(saveImageFileDir); 
			
			imageFile.transferTo(saveImageFile);
			
			channelVO.setChannelImage(saveImageFileDir);
			
			//페이 체크
			PaymentVO payCheck = paymentService.payCheck(channelVO.getUserIdx());
			
			if(payCheck.getPaymentCount() == null || payCheck.getPaymentCount() == 0) {
				attributes.addAttribute("message", "결제가 필요한 서비스입니다.");
			}else {
				paymentService.paymentUpdate(channelVO);

				int channelIdx = channelService.channelInsert(channelVO);
				
				channelVO.setChannelIdx(channelIdx);
				channelService.insertViewResultColumn(channelVO);
				
				attributes.addAttribute("message", "채널이 생성되었습니다.");
			}			
		}catch (Exception e) {
			e.printStackTrace();
			
			File deleteDir = new File(CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx());
			if(deleteDir.exists()) {
				deleteDir.delete();
			}
			
			attributes.addAttribute("message", "채널생성 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		return "redirect:";
	}
	
	//채널 수정
	@SuppressWarnings("static-access")
	@RequestMapping(value = "channel/{channelIdx}", method = RequestMethod.PUT)
	public String channelUpdate(@PathVariable Integer channelIdx, ChannelVO channelVO, @RequestParam("imageFile")MultipartFile imageFile, 
			RedirectAttributes attributes) throws Exception {
		channelVO.setChannelIdx(channelIdx);
		
		FileUtils saveDir = new FileUtils();
		
		try {
			Integer memberCount = memberService.memberCount(channelVO.getChannelIdx());
			
			if(channelVO.getChannelMax() < memberCount) {
				attributes.addAttribute("message", "채널에 가입한 회원수가 수정하신 최대 멤버수를 초과합니다.");
				return "redirect:";
			}
			
			File deleteDir = new File(CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx());
			
			if(deleteDir.exists()) {
				deleteDir.delete();
			}
			
			saveDir.forceMkdir(new File(CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx()));
			
			String fileName = imageFile.getOriginalFilename();
			String saveImageFileDir = CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx() + "/" +  fileName;
			
			File saveImageFile = new File(saveImageFileDir); 
			
			imageFile.transferTo(saveImageFile);
			
			channelVO.setChannelImage(saveImageFileDir);
			
			channelService.channelUpdate(channelVO);
			
			attributes.addAttribute("message", "채널이 수정되었습니다.");			
		}catch (Exception e) {
			e.printStackTrace();
			
			File deleteDir = new File(CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx());
			if(deleteDir.exists()) {
				deleteDir.delete();
			}
			
			attributes.addAttribute("message", "채널수정 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		return "redirect:";
	}
	
	//채널 상세
	@RequestMapping(value = "channel/{channelIdx}", method = RequestMethod.GET)
	public String channelInfo(@PathVariable Integer channelIdx, ChannelVO channelVO, Model model) throws Exception{
		channelVO.setChannelIdx(channelIdx);
		
		ChannelVO channelInfoVO = channelService.channelInfo(channelVO);
		
		model.addAttribute("channelInfo", channelInfoVO);
		
		return "";
	}
	
	//결과 컬럼 설정
	@RequestMapping(value ="channel/viewResultColumn/{channelIdx}", method = RequestMethod.GET)
	public String viewResultColumn(@PathVariable Integer channelIdx, HttpSession session, 
			Model model, RedirectAttributes attributes) throws Exception{
		UserVO loginUser = (UserVO)session.getAttribute("loginUser");
		
		ChannelVO channelVO = channelService.channelSelect(channelIdx);
		
		if(loginUser.getUserIdx() != channelVO.getChannelIdx()) {
			attributes.addAttribute("message", "권한이 없습니다.");
			
			return "redirect:";
		}else {
			ViewResultColumnVO colVO = channelService.selectViewResultColumn(channelIdx);
			
			model.addAttribute("viewResultColumn", colVO);
			
			return "";
		}		
	}
	
	//결과 컬럼 수정
	@RequestMapping(value = "channel/viewResultColumn/{channelIdx}/{viewResultColumnIdx}" , method = RequestMethod.PUT)
	public String viewResultColumnUpdate(@PathVariable Integer channelIdx, @PathVariable Integer viewResultColumnIdx, 
			ViewResultColumnVO colVO, RedirectAttributes attributes) throws Exception {
		colVO.setChannelIdx(viewResultColumnIdx);
		
		try {
			channelService.viewResultColumnUpdate(colVO);
			
			attributes.addAttribute("message", "보여줄 행 정보를 수정했습니다.");
		}catch (Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("message", "에러가 발생했습니다.");
		}
		
		return "redirect:";
	}
	
}
