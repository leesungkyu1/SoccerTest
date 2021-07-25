package com.soccer.web.channel.play.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.play.dao.TeamPlayerMapper;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;

@Service("teamPlayerService")
public class TeamPlayerServiceImpl implements TeamPlayerService{
	
	@Autowired
	private TeamPlayerMapper teamPlayerMapper;
	
	@Override // 경기의 선수들의 리스트를 출력하는 메서드
	public List<TeamPlayerVO> selectTeamPlayerList(int channelPlayIdx) throws Exception {
		return teamPlayerMapper.selectTeamPlayerList(channelPlayIdx);
	}
	
	@Override
	public List<TeamPlayerVO> selectHomeAwayTeamPlayerList(HashMap<String, String> teamInfoMap) throws Exception {
		return teamPlayerMapper.selectHomeAwayTeamPlayerList(teamInfoMap);
	}
	
	@Override
	public List<PlayresultVO> selectPlayerResultVOList(int channelPlayIdx) throws Exception {
		return teamPlayerMapper.selectPlayerResultList(channelPlayIdx);
	}

//	@Override
//	public int insertTeamPlayer(HashMap<String, String> teamPlayParameterMap) throws Exception {
//		return teamPlayerMapper.insertTeamPlayer(teamPlayParameterMap);
//	}
	
	@Override
	public void insertPlayresult(HashMap<String, Integer> playresultMap) throws Exception {
		teamPlayerMapper.insertPlayresult(playresultMap);
	}
	
	@Override
	public void updateTeamPlayerPosition(TeamPlayerVO teamPlayerVO) throws Exception {
		teamPlayerMapper.updateTeamPlayerPosition(teamPlayerVO);
	}

	@Override
	public void deleteTeamPlayer(TeamPlayerVO teamPlayerVO) throws Exception {
		int teamPlayerIdx = teamPlayerVO.getTeamPlayerIdx();
		teamPlayerMapper.deleteTeamPlayer(teamPlayerIdx);
	}
	
	@Override
	public TeamPlayerVO selectTeamPlayerDetail(HashMap<String, Integer> teamPlayerRequireMap) throws Exception {
		return teamPlayerMapper.selectTeamPlayerDetail(teamPlayerRequireMap);
	}

	@Override
	public PlayresultVO selectTeamPlayerCurrentResult(TeamPlayerVO teamPlayerVO) throws Exception {
		List<PlayresultVO> playResultVOList = teamPlayerMapper.selectTeamPlayerCurrentResult(teamPlayerVO);
		PlayresultVO resultVO = new PlayresultVO();
		
		// list size 확인
		System.out.println("playResultVOList.size() : " + playResultVOList.size());
		for (int i = 0; i < playResultVOList.size(); i++) {
			PlayresultVO tmpVO = playResultVOList.get(i);
			if (i == 0) {
				resultVO.setPlayresultTotaltackle(tmpVO.getPlayresultTotaltackle());
				resultVO.setPlayresultSuccesstackle(tmpVO.getPlayresultSuccesstackle());
				resultVO.setPlayresultTotalcross(tmpVO.getPlayresultTotalcross());
				resultVO.setPlayresultSuccesscross(tmpVO.getPlayresultSuccesscross());
				resultVO.setPlayresultTotalcornerkick(tmpVO.getPlayresultTotalcornerkick());
				resultVO.setPlayresultSuccesscornerkick(tmpVO.getPlayresultSuccesscornerkick());
				resultVO.setPlayresultTotalfreekick(tmpVO.getPlayresultTotalfreekick());
				resultVO.setPlayresultSuccessfreekick(tmpVO.getPlayresultSuccessfreekick());
				resultVO.setPlayresultTotalshooting(tmpVO.getPlayresultTotalshooting());
				resultVO.setPlayresultSuccessshooting(tmpVO.getPlayresultSuccessshooting());
				resultVO.setPlayresultTotalassist(tmpVO.getPlayresultTotalassist());
				resultVO.setPlayresultSuccessassist(tmpVO.getPlayresultSuccessassist());
				resultVO.setPlayresultTotalpass(tmpVO.getPlayresultTotalpass());
				resultVO.setPlayresultSuccesspass(tmpVO.getPlayresultSuccesspass());
				resultVO.setPlayresultTotalcontention(tmpVO.getPlayresultTotalcontention());
				resultVO.setPlayresultSuccesscontention(tmpVO.getPlayresultSuccesscontention());
			} else {
				resultVO.setPlayresultTotaltackle(tmpVO.getPlayresultTotaltackle() + resultVO.getPlayresultTotaltackle());
				resultVO.setPlayresultSuccesstackle(tmpVO.getPlayresultSuccesstackle() + resultVO.getPlayresultSuccesstackle());
				resultVO.setPlayresultTotalcross(tmpVO.getPlayresultTotalcross() + resultVO.getPlayresultTotalcross());
				resultVO.setPlayresultSuccesscross(tmpVO.getPlayresultSuccesscross() + resultVO.getPlayresultSuccesscross());
				resultVO.setPlayresultTotalcornerkick(tmpVO.getPlayresultTotalcornerkick() + resultVO.getPlayresultTotalcornerkick());
				resultVO.setPlayresultSuccesscornerkick(tmpVO.getPlayresultSuccesscornerkick() + resultVO.getPlayresultSuccesscornerkick());
				resultVO.setPlayresultTotalfreekick(tmpVO.getPlayresultTotalfreekick() + resultVO.getPlayresultTotalfreekick());
				resultVO.setPlayresultSuccessfreekick(tmpVO.getPlayresultSuccessfreekick() + resultVO.getPlayresultSuccessfreekick());
				resultVO.setPlayresultTotalshooting(tmpVO.getPlayresultTotalshooting() + resultVO.getPlayresultTotalshooting());
				resultVO.setPlayresultSuccessshooting(tmpVO.getPlayresultSuccessshooting() + resultVO.getPlayresultSuccessshooting());
				resultVO.setPlayresultTotalassist(tmpVO.getPlayresultTotalassist() + resultVO.getPlayresultTotalassist());
				resultVO.setPlayresultSuccessassist(tmpVO.getPlayresultSuccessassist() + resultVO.getPlayresultSuccessassist());
				resultVO.setPlayresultTotalpass(tmpVO.getPlayresultTotalpass() + resultVO.getPlayresultTotalpass());
				resultVO.setPlayresultSuccesspass(tmpVO.getPlayresultSuccesspass() + resultVO.getPlayresultSuccesspass());
				resultVO.setPlayresultTotalcontention(tmpVO.getPlayresultTotalcontention() + resultVO.getPlayresultTotalcontention());
				resultVO.setPlayresultSuccesscontention(tmpVO.getPlayresultSuccesscontention() + resultVO.getPlayresultSuccesscontention());
			}
		}
		return resultVO;
	}
	
	@Override // 경기 선수들의 총 결과를 출력하는 메서드
	public PlayresultVO selectPlayerResultVO(int channelPlayIdx) throws Exception {
		List<PlayresultVO> playerResultVOList = teamPlayerMapper.selectPlayerResultList(channelPlayIdx);
		PlayresultVO playerResultVO = new PlayresultVO();
		
		if (playerResultVOList.size() != 0) {
			playerResultVO.setChannelPlayIdx(channelPlayIdx);
			for (int i = 0; i < playerResultVOList.size(); i++) {
				PlayresultVO tmpVO = playerResultVOList.get(i);
				if (i == 0) {
					playerResultVO.setPlayresultTotaltackle(tmpVO.getPlayresultTotaltackle());
					playerResultVO.setPlayresultSuccesstackle(tmpVO.getPlayresultSuccesstackle());
					playerResultVO.setPlayresultTotalcross(tmpVO.getPlayresultTotalcross());
					playerResultVO.setPlayresultSuccesscross(tmpVO.getPlayresultSuccesscross());
					playerResultVO.setPlayresultTotalcornerkick(tmpVO.getPlayresultTotalcornerkick());
					playerResultVO.setPlayresultSuccesscornerkick(tmpVO.getPlayresultSuccesscornerkick());
					playerResultVO.setPlayresultTotalfreekick(tmpVO.getPlayresultTotalfreekick());
					playerResultVO.setPlayresultSuccessfreekick(tmpVO.getPlayresultSuccessfreekick());
					playerResultVO.setPlayresultTotalshooting(tmpVO.getPlayresultTotalshooting());
					playerResultVO.setPlayresultSuccessshooting(tmpVO.getPlayresultSuccessshooting());
					playerResultVO.setPlayresultTotalassist(tmpVO.getPlayresultTotalassist());
					playerResultVO.setPlayresultSuccessassist(tmpVO.getPlayresultSuccessassist());
					playerResultVO.setPlayresultTotalpass(tmpVO.getPlayresultTotalpass());
					playerResultVO.setPlayresultSuccesspass(tmpVO.getPlayresultSuccesspass());
					playerResultVO.setPlayresultTotalcontention(tmpVO.getPlayresultTotalcontention());
					playerResultVO.setPlayresultSuccesscontention(tmpVO.getPlayresultSuccesscontention());
				} else {
					playerResultVO.setPlayresultTotaltackle(tmpVO.getPlayresultTotaltackle() + playerResultVO.getPlayresultTotaltackle());
					playerResultVO.setPlayresultSuccesstackle(tmpVO.getPlayresultSuccesstackle() + playerResultVO.getPlayresultSuccesstackle());
					playerResultVO.setPlayresultTotalcross(tmpVO.getPlayresultTotalcross() + playerResultVO.getPlayresultTotalcross());
					playerResultVO.setPlayresultSuccesscross(tmpVO.getPlayresultSuccesscross() + playerResultVO.getPlayresultSuccesscross());
					playerResultVO.setPlayresultTotalcornerkick(tmpVO.getPlayresultTotalcornerkick() + playerResultVO.getPlayresultTotalcornerkick());
					playerResultVO.setPlayresultSuccesscornerkick(tmpVO.getPlayresultSuccesscornerkick() + playerResultVO.getPlayresultSuccesscornerkick());
					playerResultVO.setPlayresultTotalfreekick(tmpVO.getPlayresultTotalfreekick() + playerResultVO.getPlayresultTotalfreekick());
					playerResultVO.setPlayresultSuccessfreekick(tmpVO.getPlayresultSuccessfreekick() + playerResultVO.getPlayresultSuccessfreekick());
					playerResultVO.setPlayresultTotalshooting(tmpVO.getPlayresultTotalshooting() + playerResultVO.getPlayresultTotalshooting());
					playerResultVO.setPlayresultSuccessshooting(tmpVO.getPlayresultSuccessshooting() + playerResultVO.getPlayresultSuccessshooting());
					playerResultVO.setPlayresultTotalassist(tmpVO.getPlayresultTotalassist() + playerResultVO.getPlayresultTotalassist());
					playerResultVO.setPlayresultSuccessassist(tmpVO.getPlayresultSuccessassist() + playerResultVO.getPlayresultSuccessassist());
					playerResultVO.setPlayresultTotalpass(tmpVO.getPlayresultTotalpass() + playerResultVO.getPlayresultTotalpass());
					playerResultVO.setPlayresultSuccesspass(tmpVO.getPlayresultSuccesspass() + playerResultVO.getPlayresultSuccesspass());
					playerResultVO.setPlayresultTotalcontention(tmpVO.getPlayresultTotalcontention() + playerResultVO.getPlayresultTotalcontention());
					playerResultVO.setPlayresultSuccesscontention(tmpVO.getPlayresultSuccesscontention() + playerResultVO.getPlayresultSuccesscontention());
				}
			}
		}
		return playerResultVO;
	}
	
	@Override // 경기를 뛴 선수의 경기 결과를 수정하는 메서드
	public void updatePlayresult(PlayresultVO playresultVO) throws Exception {
		teamPlayerMapper.updatePlayerPlayresult(playresultVO);
	}

	@Override
	public void resultUpdate(List<PlayresultVO> resultVO) throws Exception {
		teamPlayerMapper.resultUpdate(resultVO);
	}
	
	// 선수의 formation을 변경
	@Override
	public void updateTeamPlayerFormation(TeamPlayerVO teamPlayerVO) throws Exception {
		teamPlayerMapper.updateTeamPlayerFormation(teamPlayerVO);
	}
	
	// 선수의 경기 결과를 수정하기 전 들고오는 메서드
	@Override
	public PlayresultVO selectPlayerresultVODetail(int teamPlayerIdx) throws Exception {
		return teamPlayerMapper.selectPlayerresultVODetail(teamPlayerIdx);
	}

	//기존
//	@Override
//	public void insertTeamPlayer(TeamPlayerVO teamPlayerVO) {
//		teamPlayerMapper.insertTeamPlayer(teamPlayerVO);
//		
//	}

	@Override
	public void insertTeam(TeamVO teamVO) {
		teamPlayerMapper.insertTeam(teamVO);
		
	}

	@Override
	public void insertTeamPlayer(HashMap<Object, Object> map) {
		// TODO Auto-generated method stub
		for(int i = 0; i < (int) map.get("userIdxSize") ; i++) {
			//teamPlayerMapper.insertTeamPlayers(map);
			map.put("userIdx",  ((List<TeamPlayerVO>) map.get("selectPlayer")).get(i));
			teamPlayerMapper.insertTeamPlayers(map);
		}
	}
	
	
	
}
