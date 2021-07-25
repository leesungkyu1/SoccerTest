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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.board.service.ChannelBoardServiceImpl;
import com.soccer.web.channel.member.service.MemberServiceImpl;
import com.soccer.web.channel.play.service.ChannelPlayServiceImpl;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.ViewResultColumnVO;
import com.soccer.web.channel.service.ChannelServiceImpl;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.payment.service.PaymentServiceImpl;
import com.soccer.web.payment.vo.PaymentVO;
import com.soccer.web.region.service.RegionServiceImpl;
import com.soccer.web.region.vo.RegionVO;
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
	
	@Autowired
	RegionServiceImpl regionService;
	
	//추후 채널용 이미지 파일 경로
	private final String CHANNEL_IMAGE_DIR = "c:"; 
	
	//채널 목록, 검색 목록 (페이징 처리 안했음) 쿼리 전달
	@RequestMapping(value = "/main/channel", method = RequestMethod.GET)
	public String channelList(ChannelVO channelVO, Model model, HttpSession session, RedirectAttributes attributes) throws Exception {
		try {
			int totcnt = channelService.selectChannelListTotCnt(channelVO);
			//총 게시물
			channelVO.setTotalListCnt(totcnt);
			//총 페이지
			channelVO.setTotalPageCnt((int) Math.ceil(channelVO.getTotalListCnt() * 1.0 / channelVO.getPageSize()));
			//게시글 번호
			channelVO.setStartIndex((channelVO.getPage() - 1) * channelVO.getBlockSize()); 
			//시작 페이지 번호
			channelVO.setStartPage(channelVO.getPage() - (channelVO.getPage() - 1) % channelVO.getBlockSize());
			//끌 페이지 번호
			channelVO.setEndPage(channelVO.getStartPage() + channelVO.getBlockSize() - 1);
			//가져오는 게시물 인덱스 시작번호
			channelVO.setStartIndex((channelVO.getPage() - 1) * channelVO.getPageSize());
			
			if(channelVO.getEndPage() > channelVO.getTotalPageCnt()) {
				channelVO.setEndPage(channelVO.getTotalPageCnt());
			}
			
			List<ChannelVO> channelList = channelService.channelList(channelVO);
			
			UserVO userVO = (UserVO)session.getAttribute("loginUser");
			
			List<Integer> joinChannelList = channelService.joinChannelList(userVO);
			
			if(channelList == null) {
				model.addAttribute("message", "검색 결과가 없습니다.");
			}else {
				for(int i=0; i<channelList.size(); i++) {
					boolean joinSw = joinChannelList.contains(channelList.get(i).getChannelIdx());
					
					if(joinSw) {
						channelList.get(i).setJoinCheck("T");
					}
				}
				
				model.addAttribute("page", channelVO);
				model.addAttribute("channelList", channelList);
			}
			
			return "channel/channel_list";
		}catch (Exception e) {
			e.printStackTrace();
			
			attributes.addAttribute("code", 401);
			attributes.addAttribute("message", "정보를 불러오는 중 에러가 발생했습니다.");
			
			return "redirect:/error/errorMessage";
		}
	}
	
	//경기용 채널 검색 기능
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ChannelVO> asyncChannelList(ChannelVO channelVO) throws Exception {
		List<ChannelVO> channelList = channelService.channelList(channelVO);
		
		return channelList;
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
			
			attributes.addAttribute("code", 402);
			attributes.addAttribute("message", "채널 생성 중 에러가 발생했습니다.");
			
			return "redirect:/error/errorMessage";
		}
		return "redirect:";
	}
	
	//채널 수정 뷰
	@RequestMapping(value = "/channel/view/{channelIdx}", method = RequestMethod.GET)
	public String channelUpdateView(@PathVariable Integer channelIdx, Model model,
			@RequestParam(required = false) String message) throws Exception {
		ChannelVO channelVO = new ChannelVO();
		channelVO.setChannelIdx(channelIdx);
		
		channelVO = channelService.selectChannel(channelVO);
		List<RegionVO> regionList = regionService.regionList();
		
		model.addAttribute("channelVO", channelVO);
		model.addAttribute("regionList", regionList);
		
		if(message != null) {
			model.addAttribute("message", message);
		}
		
		return "channel/channel_modify";
	}
	
	//채널 수정
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/channel/{channelIdx}", method = RequestMethod.PUT)
	public String channelUpdate(@PathVariable Integer channelIdx, ChannelVO channelVO, 
			@RequestParam(value = "imageFile", required = false)MultipartFile imageFile,
			RedirectAttributes attributes) throws Exception {
		channelVO.setChannelIdx(channelIdx);
		
		try {
			Integer memberCount = memberService.memberCount(channelVO.getChannelIdx());
			
			if(channelVO.getChannelMax() < memberCount) {
				attributes.addAttribute("message", "채널에 가입한 회원수가 수정하신 최대 멤버수를 초과합니다.");
				
				return "redirect:/channel/view/" + channelIdx;
			}
			
			if(!imageFile.isEmpty()) {
				FileUtils saveDir = new FileUtils();
				
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
			}
			
			channelService.channelUpdate(channelVO);
			
			attributes.addAttribute("message", "채널이 수정되었습니다.");			
		}catch (Exception e) {
			e.printStackTrace();
			
			if(!imageFile.isEmpty()) {
				File deleteDir = new File(CHANNEL_IMAGE_DIR + "/" + channelVO.getChannelIdx());
				
				if(deleteDir.exists()) {
					deleteDir.delete();
				}
			}
			attributes.addAttribute("code", 403);
			attributes.addAttribute("message", "채널 수정 중 에러가 발생했습니다.");
			
			return "redirect:/error/errorMessage";
		}
		return "redirect:/channel/member/" + channelIdx;
	}
	
	//채널 상세
	@RequestMapping(value = "/channel/{channelIdx}", method = RequestMethod.GET)
	public String channelInfo(@PathVariable int channelIdx, ChannelPlayVO channelPlayVO, ChannelVO channelVO, Model model) throws Exception{
		
		
		List<ChannelPlayVO> channelPlayList = channelPlayService.selectChannelPlayList(channelPlayVO);
		channelVO.setChannelIdx(channelIdx);
		
		model.addAttribute("channelVO", channelVO);
		model.addAttribute("channelPlayVO", channelPlayList);
//		ChannelVO channelInfoVO = channelService.channelInfo(channelVO);
//		
//		model.addAttribute("channelInfo", channelInfoVO);
		
		System.out.println(channelPlayList);
		System.out.println(channelVO);
		return "channel/channel_main";
	}
	
	//결과 컬럼 설정
	@RequestMapping(value ="channel/viewResultColumn/{channelIdx}", method = RequestMethod.GET)
	public String viewResultColumn(@PathVariable Integer channelIdx, HttpSession session, 
			Model model, RedirectAttributes attributes) throws Exception{
		try {
			UserVO loginUser = (UserVO)session.getAttribute("loginUser");
			
			ChannelVO channelVO = channelService.channelSelect(channelIdx);
			
			if(loginUser.getUserIdx() != channelVO.getChannelIdx()) {
				throw new Exception("404");
			}else {
				ViewResultColumnVO colVO = channelService.selectViewResultColumn(channelIdx);
				
				model.addAttribute("viewResultColumn", colVO);
				
				return "";
			}
		}catch (Exception e) {
			e.printStackTrace();
			
			if(e.getMessage().equals("404")){
				attributes.addAttribute("code", 404);
				attributes.addAttribute("message", "권한이 없습니다.");
			}
			return "redirect:/error/errorMessage";
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
			
			attributes.addAttribute("code", 405);
			attributes.addAttribute("message", "에러가 발생했습니다.");
			
			return "redirect:/error/errorMessage";
		}
		
		return "redirect:";
	}
	
}
