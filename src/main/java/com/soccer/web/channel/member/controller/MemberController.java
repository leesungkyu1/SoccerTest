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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.member.apply.service.ApplyServiceImpl;
import com.soccer.web.channel.member.apply.vo.ApplyVO;
import com.soccer.web.channel.member.service.MemberServiceImpl;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

//채널 + 로 매핑
@Controller
public class MemberController {
	private final String MEMBER_IMAGE_DIR = "c:";
	
	@Autowired
	MemberServiceImpl memberService;
	
	@Autowired
	ApplyServiceImpl applyService;
	
	//회원 목록 + 신청 목록
	@RequestMapping(value = "" , method = RequestMethod.GET)
	public String channelMemberList(ChannelVO channelVO, Model model) throws Exception{
		List<MemberVO> memberList = memberService.memberList(channelVO); 
		List<ApplyVO> applyList = applyService.applyList(channelVO);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("applyList", applyList);
				
		return "";
	}
	
	//회원 강퇴
	@RequestMapping(value = "{memberIdx}", method = RequestMethod.DELETE)
	public String memberDelete (@PathVariable("memberIdx") Integer memberIdx, ChannelVO channelVO, RedirectAttributes attributes ) throws Exception{
		try {
			memberService.memberDelete(memberIdx, channelVO.getChannelIdx());
			applyService.applyDelete(memberIdx, channelVO.getChannelIdx());
			
			String imageFileDir = MEMBER_IMAGE_DIR + "/" + channelVO.getChannelIdx() + "/" + memberIdx; 
			
			File imageFile = new File(imageFileDir);
			
			if(imageFile.exists()) {
				imageFile.delete();
			}
			
			attributes.addAttribute("message", "회원을 탈퇴시켰습니다..");
		}catch(Exception e) {
			attributes.addAttribute("code", 301);
			attributes.addAttribute("message", "회원탈퇴 처리 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		
		return "redirect:";
	}
	
	//회원가입 신청
	@SuppressWarnings("static-access")
	@RequestMapping(value ="" , method = RequestMethod.POST)
	public String memberApply(MemberVO memberVO, @RequestParam("imageFile")MultipartFile imageFile, 
			RedirectAttributes attributes) throws Exception{
		FileUtils saveDir = new FileUtils();
		
		try {
			saveDir.forceMkdir(new File(MEMBER_IMAGE_DIR + "/" + memberVO.getChannelIdx() + "/" + memberVO.getMemberIdx()));
			
			String fileName = imageFile.getOriginalFilename();
			String saveImageFileDir = MEMBER_IMAGE_DIR + "/" + memberVO.getChannelIdx() + "/" + memberVO.getMemberIdx() + fileName;
			
			File saveImageFile = new File(saveImageFileDir); 
			
			imageFile.transferTo(saveImageFile);
			
			memberVO.setMemberImage(saveImageFileDir);
			
			int checkMember = memberService.checkMember(memberVO);
			
			if(checkMember == 0) {
				applyService.memberApply(memberVO);
				
				attributes.addAttribute("message", "채널가입이 신청되었습니다.");
			}else {
				attributes.addAttribute("message", "가입 거부되신 채널입니다.");
			}
		}catch (Exception e) {
			e.printStackTrace();
			
			File deleteDir = new File(MEMBER_IMAGE_DIR + "/" + memberVO.getMemberIdx());
			if(deleteDir.exists()) {
				deleteDir.delete();
			}
			
			attributes.addAttribute("message", "채널가입 신청 중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		
		return "redirect:";
	}
	
	//회원 가입신청 승인
	@RequestMapping(value = "{applyIdx}", method = RequestMethod.PUT)
	public String memberAccept(@PathVariable("applyIdx") Integer applyIdx, RedirectAttributes attributes) throws Exception{
		try {
			applyService.memberAccept(applyIdx);
			
			attributes.addAttribute("message", "회원가입이 승인되었습니다.");
		}catch(Exception e) {
			attributes.addAttribute("code", 302);
			attributes.addAttribute("message", "회원가입이 승인 처리중 에러가 발생했습니다.");
			
			return "redirect:";
		}
		
		return "redirect:";
	}
	
	//회원 가입신청 거절 
	@RequestMapping(value = "{applyIdx}" , method = RequestMethod.DELETE)
	public String memberDinie (@PathVariable("applyIdx") Integer applyIdx, RedirectAttributes attributes) throws Exception{
		try {
			applyService.memberDinie(applyIdx);
			
			attributes.addAttribute("message", "회원가입이 거절되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 303);
			attributes.addAttribute("message", "회원가입 거절 중 에러가 발생했습니다.");
		}
		
		return "redirect:";
	}
}
