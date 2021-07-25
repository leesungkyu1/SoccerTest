package com.soccer.web.channel.member.controller;

import java.io.File;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.member.apply.service.ApplyServiceImpl;
import com.soccer.web.channel.member.apply.vo.ApplyVO;
import com.soccer.web.channel.member.service.MemberServiceImpl;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.service.ChannelServiceImpl;
import com.soccer.web.channel.vo.ChannelVO;

//채널 + 로 매핑
@Controller
public class MemberController {
	//추후 멤버 이미지용 경로 지정
	private final String MEMBER_IMAGE_DIR = "c:";
	
	@Autowired
	MemberServiceImpl memberService;
	
	@Autowired
	ApplyServiceImpl applyService;
	
	@Autowired
	ChannelServiceImpl channelService;
	
	//여기
	//회원 목록 + 신청 목록
	@RequestMapping(value = "/channel/member/{channelIdx}" , method = RequestMethod.GET)
	public String channelMemberList(@PathVariable Integer channelIdx, ChannelVO channelVO, Model model) throws Exception{
		channelVO.setChannelIdx(channelIdx);
		
		List<MemberVO> memberList = memberService.memberList(channelVO);
		List<ApplyVO> applyList = applyService.applyList(channelVO);
		channelVO = channelService.selectChannel(channelVO);
		
		System.out.println(memberList.toString());
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("applyList", applyList);
		model.addAttribute("channelIdx", channelIdx);
		model.addAttribute("channelVO", channelVO);
				
		return "channel/channal_player_manager";
	}
	
	//회원 강퇴
	@RequestMapping(value = "/channel/member/{channelIdx}/{memberIdx}", method = RequestMethod.DELETE)
	public String memberDelete (@PathVariable Integer channelIdx, @PathVariable("memberIdx") Integer memberIdx, ChannelVO channelVO, 
			RedirectAttributes attributes) throws Exception{
		channelVO.setChannelIdx(channelIdx);
		
		try {
			memberService.memberDelete(memberIdx, channelVO.getChannelIdx());
			applyService.applyDelete(memberIdx, channelVO.getChannelIdx());
			
			String imageFileDir = MEMBER_IMAGE_DIR + "/" + channelVO.getChannelIdx() + "/" + memberIdx; 
			
			System.out.println(imageFileDir);
			
			File imageFile = new File(imageFileDir);
			
			if(imageFile.exists()) {
				imageFile.delete();
			}
			
			attributes.addAttribute("message", "회원을 탈퇴시켰습니다.");
		}catch(Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 301);
			attributes.addAttribute("message", "회원탈퇴 처리 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		
		return "redirect:/channel/member/" + channelIdx;
	}
	
	//회원가입 신청 뷰
	@RequestMapping(value = "/channel/apply/{channelIdx}", method = RequestMethod.GET)
	public String memberApplyView(@PathVariable Integer channelIdx,ChannelVO page, Model model) throws Exception {
		ChannelVO channelVO = new ChannelVO();
		
		channelVO.setChannelIdx(channelIdx);
		
		channelVO = channelService.selectChannel(channelVO);
		
		model.addAttribute("page", page);
		model.addAttribute("channelVO", channelVO);
		
		return "channel/channel_sign_up";
	}
	
	//회원가입 신청
	@SuppressWarnings("static-access")
	@RequestMapping(value ="/channel/member/{channelIdx}" , method = RequestMethod.POST)
	public String memberApply(MemberVO memberVO, @RequestParam("imageFile")MultipartFile imageFile, 
			RedirectAttributes attributes) throws Exception{
		FileUtils saveDir = new FileUtils();
		
		try {
			saveDir.forceMkdir(new File(MEMBER_IMAGE_DIR + "/" + memberVO.getChannelIdx() + "/" + memberVO.getMemberIdx()));
			
			String fileName = imageFile.getOriginalFilename();
			String saveImageFileDir = MEMBER_IMAGE_DIR + "/" + memberVO.getChannelIdx() + "/" + memberVO.getMemberIdx() + "/" + fileName;
			
			File saveImageFile = new File(saveImageFileDir); 
			
			imageFile.transferTo(saveImageFile);
			
			memberVO.setMemberImage(saveImageFileDir);
			
			int checkMember = memberService.checkMember(memberVO);
			
			if(checkMember == 0) {
				applyService.memberApply(memberVO);
				
				ChannelVO channelVO = new ChannelVO();
				channelVO.setChannelIdx(memberVO.getChannelIdx());
				
				channelVO = channelService.selectChannel(channelVO);
				
				attributes.addFlashAttribute("channelVO", channelVO);
				//attributes.addAttribute("message", "채널가입이 신청되었습니다.");
			}else {
				attributes.addAttribute("message", "가입 거부되신 채널입니다.");
			}
		}catch (Exception e) {
			e.printStackTrace();
			FileUtils fileUtils = new FileUtils();
			
			File deleteDir = new File(MEMBER_IMAGE_DIR + "/" + memberVO.getChannelIdx() + "/" + memberVO.getMemberIdx());
			
			if(deleteDir.exists()) {
				fileUtils.forceDelete(deleteDir);
			}
			
			attributes.addAttribute("code", 301);
			attributes.addAttribute("message", "채널가입 신청 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		return "redirect:/channel/singUpCompleView";
	}
	
	@RequestMapping(value = "/channel/singUpCompleView", method = RequestMethod.GET)
	public String channelSingUpCompleteView (ChannelVO channelVO, @RequestParam(required = false) String message, Model model) throws Exception {
		System.out.println(channelVO);
		
		model.addAttribute("channelVO", channelVO);
		
		if(message != null) {
			model.addAttribute("message", message);
		}
		
		return "channel/channel_sign_up_comple";
	}
	
	//회원 가입신청 승인
	@RequestMapping(value = "/channel/apply/{channelIdx}/{applyIdx}", method = RequestMethod.PUT)
	public String memberAccept(@PathVariable("applyIdx") Integer applyIdx, @PathVariable("channelIdx") Integer channelIdx,
			RedirectAttributes attributes) throws Exception{
		try {
			Integer memberMax = channelService.channelMemberMax(channelIdx);
			Integer memberCount = memberService.memberCount(channelIdx);
			
			System.out.println("memberMax = " + memberMax);
			System.out.println("memberCount = " + memberCount);
			
			if(memberCount != null && memberCount >= memberMax) {
				attributes.addAttribute("message", "채널 최대 회원수를 초과합니다.");
			}else {
				applyService.memberAccept(applyIdx);
				
				attributes.addAttribute("message", "회원가입이 승인되었습니다.");				
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 302);
			attributes.addAttribute("message", "회원가입이 승인 처리중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		
		return "redirect:/channel/member/" + channelIdx;
	}
	
	//회원 가입신청 거절 
	@RequestMapping(value = "/channel/apply/{channelIdx}/{applyIdx}" , method = RequestMethod.DELETE)
	public String memberDenie (@PathVariable("channelIdx") Integer channelIdx, @PathVariable("applyIdx") Integer applyIdx, RedirectAttributes attributes) throws Exception{
		try {
			applyService.memberDenie(applyIdx);
			
			attributes.addAttribute("message", "회원가입이 거절되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 303);
			attributes.addAttribute("message", "회원가입 거절 중 에러가 발생했습니다.");
		}
		
		return "redirect:/channel/member/" + channelIdx;
	}
	
	//경기용 채널로 회원목록 가져오기
	@ResponseBody
	@RequestMapping(value = "/channel/play/member/{channelIdx}", method = RequestMethod.GET)
	public List<MemberVO> searchByChannel(@PathVariable Integer channelIdx) throws Exception {
		List<MemberVO> searchMemberList = memberService.searchByChannel(channelIdx);
		
		return searchMemberList;
	}
}
