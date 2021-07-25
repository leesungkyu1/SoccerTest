package com.soccer.web.channel.play.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soccer.web.channel.member.service.MemberServiceImpl;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.play.service.ChannelPlayService;
import com.soccer.web.channel.play.service.TeamPlayerService;
import com.soccer.web.channel.play.vo.ChannelPlayGoalVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;
import com.soccer.web.channel.service.ChannelServiceImpl;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.user.vo.UserVO;

@Component("streamView")
@Controller
public class ChannelPlayController {
	
	private final String PLAY_VIDEO_DIR = "C:/user/lsk/video";

	@Autowired
	private ChannelPlayService channelPlayService;
	
	@Autowired
	private TeamPlayerService teamPlayerService;
	
	@Autowired
	ChannelServiceImpl channelService;
	
	@Autowired
	MemberServiceImpl memberService;
	// 채널 게시글의 리스트를 보여주는 메서드 (페이징처리는 나중에)
	@RequestMapping(value = "/channel/play/{channelIdx}", method = RequestMethod.GET)
	public String selectChannelPlayList(@PathVariable int channelIdx,
										ChannelPlayVO channelPlayVO,
										Model model,
										@RequestParam(required = false) String message) throws Exception {
		try {
			int totcnt = channelPlayService.selectChannelPlayListTotCnt(channelPlayVO);
			
			
			
			List<ChannelPlayVO> channelPlayList = channelPlayService.selectChannelPlayList(channelPlayVO);
			model.addAttribute("channelPlayList", channelPlayList);
			
			System.out.println("====================="+channelPlayList);
//			model.addAttribute("channelPlayList", channelPlayList);
		} catch (Exception e) {
			e.printStackTrace();
//			return "index";
		}
		if (message != null) {
			model.addAttribute("message", message);
		}
		
		return "channel/channel_video_index";
	}
	
	// 영상 게시글에 저장된 Player 리스트를 출력 + player들의 playresult 리스트 출력 + 영상 출력
	@RequestMapping(value = "channel/play/{channelIdx}/{channelPlayIdx}", method = RequestMethod.GET)
	public String selectChannelPlayDetail(	@PathVariable int channelIdx,
											@PathVariable int channelPlayIdx,
											RedirectAttributes attributes,
											Model model, HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try {
			
			ChannelPlayVO channelPlayVO = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
			
			System.out.println(channelPlayVO);
			List<TeamPlayerVO> teamPlayerVOList = teamPlayerService.selectTeamPlayerList(channelPlayIdx); // player 리스트
			List<PlayresultVO> playerResultVOList = teamPlayerService.selectPlayerResultVOList(channelPlayIdx); // player들의 playresult 리스트
	
			//파일 이름 및 경로
			String filePath = channelPlayVO.getChannelPlayVideo();
			System.out.println(filePath);
			
//			//파일의 시작위치 임의
//			RandomAccessFile randomFile = new RandomAccessFile(filePath , "r");
//			
//			 System.out.println(randomFile);
//			 
//			//파일 스트리밍 부분
//			long rangeStart =0;
//			long rangeEnd = 0;
//			boolean isPart=false; //부분 요청일 경우 true
//			long movieSize=randomFile.length();
//			String range = request.getHeader("range");
//			
//			if(range != null) {
//				if(range.endsWith("-")) {
//					range=range + (movieSize -1 );
//				}
//				int idxm = range.trim().indexOf("-");
//				rangeStart = Long.parseLong(range.substring(6, idxm));
//				rangeEnd = Long.parseLong(range.substring(idxm+1));
//				if(rangeStart>0) {
//					isPart=true;
//				}
//			}else {
//				rangeStart=0;
//				rangeEnd = movieSize-1;
//			}
//			
//			long partSize = rangeEnd - rangeStart+1;
//			
//			response.reset();
//			response.setStatus(isPart? 206: 200);	 //전체 요청 200, 부분 요청 206
//			response.setContentType("video/mp4");
//			
//			response.setHeader("Content-Range", "bytes" + rangeStart+ "-" + rangeEnd+ "/" + movieSize);
//			response.setHeader("Accept-Range","bytes");
//			response.setHeader("Content-Length",""+partSize);
//			
//			OutputStream out = response.getOutputStream();
//			//동영상 파일의 전송 시작 위치 지정
//			randomFile.seek(rangeStart);
//			
//			int bufferSize=8*1024;
//			byte[] buf = new byte[bufferSize];
//			
//			do {
//				int block=partSize>bufferSize ? bufferSize : (int)partSize;
//				int len= randomFile.read(buf,0,block);
//				out.write(buf, 0, len);
//				partSize -= block;
//			} while(partSize>0);
//			
			model.addAttribute("channelPlayVO", channelPlayVO);
			model.addAttribute("teamPlayerVOList", teamPlayerVOList);
			model.addAttribute("playerResultVOList", playerResultVOList);
			model.addAttribute("channelPlayVideo", filePath);
			System.out.println(channelPlayVO);
			System.out.println(teamPlayerVOList);
			System.out.println(playerResultVOList);
			System.out.println(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
			return "redirect:/channel/play/" + channelIdx;
		}
		return "channel/channel_video";
	}
	
	
	
	// 영상 게시글을 추가하는 메서드channel/play/{channelIdx}
//	@RequestMapping(value = "/channel/play/{channelIdx}", method = RequestMethod.POST)
//	public String insertChannelPlay(
//									@PathVariable int channelIdx,
//									@RequestParam("file")
//									ChannelPlayVO channelPlayVO,
//									RedirectAttributes attributes, MultipartFile file,
//									HttpServletRequest request) throws Exception {
//		
//		String channelPlayTitle = request.getParameter("channelPlayTitle");
//		String channelPlayVideo=request.getParameter("channelPlayVideo");
//		
//		System.out.println(channelPlayTitle);
//		System.out.println(channelPlayVideo);
//		System.out.println(file);
//		int channelPlayIdx = 0;
////		String root_path = request.getSession().getServletContext().getRealPath("");
////		String root_path2 = request.getSession().getServletContext().getRealPath("resources/static/video");
//		try {
//			
//			String root_path = Paths.get("C:","downloads","upload").toString(); 
//			System.out.println(root_path);
//			File targetFile = new File(root_path + file.getOriginalFilename());
//		
//			System.out.println("경로는 "+root_path);
//			InputStream fileStream;
//			
////			channelPlayVO.setChannelPlayTitle(multipartFile.getOriginalFilename());
////			channelPlayVO.setChannelPlayVideo(root_path);
//			channelPlayVO.setChannelPlayVideo((root_path.toString()+file.getOriginalFilename()));
//			channelPlayService.insertChannelPlay(channelPlayVO);
////			channelPlayVO.setChannelIdx(channelIdx); // 혹시나 필요할 때 사용하기
////			channelPlayIdx = channelPlayService.insertChannelPlay(channelPlayVO);
//			
//			fileStream = file.getInputStream();
//			FileUtils.copyInputStreamToFile(fileStream, targetFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//			attributes.addAttribute("message", "에러가 발생했습니다");
//		}
//		attributes.addAttribute("message", "영상이 성공적으로 등록되었습니다.");
//		return "channel/channel_video_index";
//	}
	
	//영상 게시글 추가하는 화면
	@RequestMapping(value="/channel/play/{channelIdx}/form", method=RequestMethod.GET)
	public String insertChannelPlayForm(ChannelPlayVO channelPlayVO, Model model) {
		
		int channelIdx = channelPlayVO.getChannelIdx();
		
		System.out.println(channelIdx);
		model.addAttribute("channelIdx", channelIdx);
		
		return "channel/channel_video_upload";
		
	}
	
	//영상 추가 테스트 화면
	
	@RequestMapping(value = "/channel/play/{channelIdx}", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile multipartFile, 
			@PathVariable int channelIdx,
			HttpServletRequest request, ChannelPlayVO channelPlayVO, Model model) throws Exception {
		
//		FileUtils saveDir = new FileUtils();
		System.out.println(multipartFile.getOriginalFilename());
		
		System.out.println(request.getParameter("title"));
		
//		String videoFileDir = PLAY_VIDEO_DIR + "/" + channelIdx+"/"+channelPlayVO.getChannelPlayIdx();
		
//		saveDir.forceMkdir(new File(PLAY_VIDEO_DIR + "/" + channelIdx+"/"+channelPlayVO.getChannelPlayIdx()));
		
		
		
		System.out.println(PLAY_VIDEO_DIR);
		String fileName= multipartFile.getOriginalFilename();
		String fileData = PLAY_VIDEO_DIR+ "/" + fileName;
		channelPlayVO.setChannelPlayTitle(fileName);
		channelPlayVO.setChannelPlayVideo(fileData);
		channelPlayService.insertChannelPlay(channelPlayVO);
		
		InputStream fileStream;
		
		try {
			
//			String path = new ClassPathResource("/static/upload").getFile().getAbsolutePath();
			File targetFile = new File(fileData);
			
			
//			channelPlayVO.setChannelPlayTitle();
//			channelPlayVO.setChannelPlayVideo(PLAY_VIDEO_DIR+ "/" + multipartFile.getOriginalFilename());
			
			fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);
			System.out.println("업로드에 성공했습니다.");
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("업로드에 실패했습니다.");
		}
				
		
		return "channel/channel_video_index";
	}
	
	
	
	
	
	// 영상 게시글을 수정하는 메서드 (게시글을 수정할 때 승인중 단계로 다시 돌아감)
	@RequestMapping(value = "/channel/play/{channelIdx}/{channelPlayIdx}", method = RequestMethod.PUT)
	public String updateChannelPlay(@PathVariable int channelIdx,
									@PathVariable int channelPlayIdx,
									ChannelPlayVO channelPlayVO,
									RedirectAttributes attributes) throws Exception {
		try {
			//TODO 기존에 있는 영상을 삭제하고 새로운 영상을 실제 DB에 추가하는 메서드를 구현해야 함
			channelPlayService.updateChannelPlay(channelPlayVO);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
//			return "index";
		}
		attributes.addAttribute("message", "영상이 성공적으로 수정되었습니다.");
		return "redirect:/channel/play/" + channelIdx + "/" + channelPlayIdx;
//		return "test";
	}
	
	// 영상 게시글을 삭제하는 메서드
	@RequestMapping(value = "/channel/play/{channelIdx}/{channelPlayIdx}", method = RequestMethod.DELETE)
	public String deleteChannelPlay(@PathVariable int channelIdx,
									@PathVariable int channelPlayIdx,
									RedirectAttributes attributes) throws Exception {
		try {
			//TODO 기존에 있는 영상을 삭제하는 메서드를 구현해야 함
			channelPlayService.deleteChannelPlay(channelPlayIdx);
		} catch (Exception e) {
			e.printStackTrace();
			attributes.addAttribute("message", "에러가 발생했습니다");
//			return "index";
		}
		attributes.addAttribute("message", "영상이 삭제되었습니다.");
		return "redirect:/channel/play/" + channelIdx;
//		return "test";
	}
	


	
	//득점 정보 입력
	@RequestMapping(value = "/channel/play/goal/{channelPlayIdx}", method = RequestMethod.POST)
	public String insertGoal(@PathVariable int channelPlayIdx, ChannelPlayGoalVO goalVO,
			RedirectAttributes attributes) throws Exception {
		goalVO.setChannelPlayIdx(channelPlayIdx);
		
		channelPlayService.insertGoal(goalVO);
		
		
		
		attributes.addAttribute("message", "득점 정보를 입력했습니다.");
				
		return "";
	}
	

	//우리팀, 상대팀 득점 정보 얻어오기
	@RequestMapping(value = "/channel/play/goal/{channelPlayIdx}", method = RequestMethod.GET)
	public String goalList(@PathVariable int channelPlayIdx, Model model, ChannelPlayVO channelPlayVO) throws Exception {
		//프론트에서 뿌릴때 나눌필요 없이 순서대로 뿌리되 타입에 따라 위치 조절
		List<ChannelPlayGoalVO> sortedByTimeGoalList = channelPlayService.goalList(channelPlayIdx);
		
		ChannelPlayVO goalListVideo = channelPlayService.selectChannelPlayDetail(channelPlayIdx);
		
		model.addAttribute("goalList", sortedByTimeGoalList);
		
		model.addAttribute("videoList", goalListVideo);
		System.out.println(sortedByTimeGoalList);
		System.out.println("======================================"+goalListVideo);
		return "channel/channel_game_record";
	}
	
	//득점 기록 수정
	@RequestMapping(value = "/channel/play/goal/{channelPlayIdx}/{channelPlayGoalIdx}", method = RequestMethod.PUT)
	public String updateGoal(@PathVariable int channelPlayGoalIdx, ChannelPlayGoalVO goalVO,
			RedirectAttributes attributes) throws Exception {
		goalVO.setChannelPlayGoalIdx(channelPlayGoalIdx);
		
		channelPlayService.updateGoal(goalVO);
		
		attributes.addAttribute("message", "득점 기록을 수정했습니다.");
		return "";
//		return "test";
	}
	
	//득점 기록 삭제
	@RequestMapping(value = "/channel/play/goal/{channelPlayIdx}/{channelPlayGoalIdx}", method = RequestMethod.DELETE)
	public String deleteGoal(@PathVariable int channelPlayGoalIdx, RedirectAttributes attributes) throws Exception {
		channelPlayService.deleteGoal(channelPlayGoalIdx);
		
		attributes.addAttribute("message", "득점 기록을 삭제했습니다.");
		
		return "";
//		return "test";
	}
	
	//우리팀 총점 상대팀 성적 기능
	@RequestMapping(value = "/channel/play/team/result/{channelPlayIdx}", method = RequestMethod.GET)
	public String totalScore(@PathVariable int channelPlayIdx, Model model) throws Exception {
		PlayresultVO totalScoreAndTeamInfo = channelPlayService.totalScore(channelPlayIdx);
		
		model.addAttribute("totalScoreAndTeamInfo", totalScoreAndTeamInfo);
		return "channel/channel_team_performance";
//		return "test";
	}
	
	//채널 검색 기능 channelController에 존재
	//채널 멤버 목록 가져오기 memberController 에 존재
	//팀 생성 , player 생성, result 생성만 default로  
	//result 수정 기능 
	//상대팀 생성 로직도 똑같음
	
	//선수 입력 GET 화면
	@RequestMapping(value="/channel/play/team/{channelIdx}/{channelPlayIdx}", method=RequestMethod.GET)
	public String selectTeam(@PathVariable int channelIdx, 
							@PathVariable int channelPlayIdx, TeamVO teamVO,   ChannelVO channelVO, UserVO userVO, Model model) throws Exception {
		
		
		
	
		List<TeamPlayerVO> playerList = teamPlayerService.selectTeamPlayerList(channelPlayIdx);
		List<ChannelVO> channelList = channelService.channelList(channelVO);

	
		teamVO.setChannelIdx(channelIdx);
		teamVO.setChannelPlayIdx(channelPlayIdx);
		
		
		model.addAttribute("channelVO", channelVO);
		model.addAttribute("channelList", channelList);
		model.addAttribute("playerList", playerList);
		model.addAttribute("teamVO", teamVO);
		System.out.println(channelVO);
		System.out.println(channelList);
		System.out.println(playerList);
		System.out.println(teamVO);
		
		model.addAttribute("channelIdx" , channelIdx);
		model.addAttribute("channelPlayIdx" , channelPlayIdx);
		
		return "channel/channel_video_player_add2";
	}
	
	//팀 생성(상대팀도 같음 테스트 필요!!!) 현재 사용 X
	
	@RequestMapping(value = "/channel/play/team/{channelIdx}/{channelPlayIdx}", method = RequestMethod.POST)
	public String createPlayInfo(
			@PathVariable int channelIdx, @PathVariable int channelPlayIdx,
			TeamVO teamVO, RedirectAttributes attributes, 
			HttpServletRequest request, UserVO userVO, Model model, @RequestParam TeamPlayerVO teamPlayerVO) throws Exception {
			
//		teamVO.setChannelIdx(channelIdx);
//		teamVO.setChannelPlayIdx(channelPlayIdx);
//	
//		for(int i = 0; i<playerList.size(); i++) {
//			playerList.get(i).setChannelPlayIdx(channelPlayIdx);
//		}
//		
//	
//		
//		
//		channelPlayService.createPlayInfo(teamVO, playerList);

		
		
		
		attributes.addAttribute("message", "경기 기록을 등록했습니다.");
		
		return "channel/channel_video_player_add2";
	}
	
	//선수 개별 기록 한번에 수정
	@RequestMapping(value = "/channel/play/result", method = RequestMethod.PUT)
	public String resultUpdate(List<PlayresultVO> resultVO, RedirectAttributes attributes) throws Exception {
		teamPlayerService.resultUpdate(resultVO);
		
		
		attributes.addAttribute("message", "선수 기록을 수정했습니다.");
		return "";
	}
	
	
	//팀 생성시 채널검색
	@ResponseBody
	@RequestMapping(value="/channel/play/team/search", method = RequestMethod.POST)
	public List<ChannelVO> teamCreate(HttpServletRequest request, ChannelVO channelVO, Model model) throws Exception {
		String searchWord = request.getParameter("searchWord");
		System.out.println(searchWord);
		List<ChannelVO> channelList = channelService.channelPlayList(searchWord);

//		System.out.println(channelList);
		return channelList;
	}

	//팀 추가화면 및 멤버 리스트 받아오기
	
	@ResponseBody
	@RequestMapping(value="/channel/play/team/teaminsert", method = RequestMethod.POST)
	public HashMap<Object, Object> testInsertCode1(@RequestBody TeamVO teamVO, Model model, HttpServletRequest request) throws Exception {
//		System.out.println("==========================");
//		System.out.println("=================================="+teamVO);
//		System.out.println(teamPlayerVO);
		
		System.out.println(request.getParameter("selectPlayer"));
		
		teamPlayerService.insertTeam(teamVO);
		List<MemberVO> searchMemberList = memberService.searchByChannel(teamVO.getChannelIdx());
		
		
		System.out.println(teamVO);
		System.out.println(searchMemberList);
		
		HashMap<Object, Object> map = new HashMap<>();
		map.put("teamVO", teamVO);
		map.put("searchMemberList", searchMemberList);
		return map; 
		
	}
	
	
	//선수 등록
	@RequestMapping(value="/channel/play/team/playerinsert", method = RequestMethod.POST)
	public String testInsertCode(@RequestBody HashMap<Object, Object> map, TeamPlayerVO teamPlayerVO) throws Exception {
		
		teamPlayerService.insertTeamPlayer(map);
		System.out.println(map);
		//System.out.println(map.get("teamIdx"));
//		teamPlayerVO.setTeamIdx((int)map.get("teamIdx") );
//		teamPlayerVO.setChannelPlayIdx((int)map.get("channelPlayIdx") );
		
//		List<TeamPlayerVO> playerList = new ArrayList<TeamPlayerVO>();
//		
//		
//		
//		
//		for(int i = 0; i <= (int) map.get("userIdxSize") ; i++) {
//			playerList.get(i).setChannelPlayIdx((int)map.get("channelPlayIdx")); 
//		}
		
		
		return "channel/channel_video_player_add2"; 
	}
	
	@RequestMapping(value="/channel/play/record/modify")
	public String columnModify() {
		
		return "channel_video_record_modify";
	}
}
