package com.soccer.web.channel.play.service;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.play.dao.ChannelPlayMapper;
import com.soccer.web.channel.play.dao.TeamPlayerMapper;
import com.soccer.web.channel.play.vo.ChannelPlayGoalVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;

@Service("channelPlayService")
public class ChannelPlayServiceImpl implements ChannelPlayService {
	
	@Autowired
	private ChannelPlayMapper channelPlayMapper;
	
	@Autowired
	private TeamPlayerMapper teamPlayerMapper;

	@Override
	public int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.selectChannelPlayListTotCnt(channelPlayVO);
	}

	@Override
	public List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.selectChannelPlayList(channelPlayVO);
	}

	@Override
	public ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception {
		return channelPlayMapper.selectChannelPlayDetail(channelPlayIdx);
	}
	
//	@Override
//	public int insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
//		return channelPlayMapper.insertChannelPlay(channelPlayVO);
//	}
	
	@Override
	public void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
		channelPlayMapper.updateChannelPlay(channelPlayVO);
	}
	
	@Override
	public void deleteChannelPlay(int channelPlayIdx) throws Exception {
		channelPlayMapper.deleteChannelPlay(channelPlayIdx);
	}

	@Override
	public void insertGoal(ChannelPlayGoalVO goalVO) throws Exception {
		channelPlayMapper.insertGoal(goalVO);
	}

	@Override
	public List<ChannelPlayGoalVO> goalList(int channelPlayIdx) throws Exception {
		return channelPlayMapper.goalList(channelPlayIdx);
	}

	@Override
	public void updateGoal(ChannelPlayGoalVO goalVO) throws Exception {
		channelPlayMapper.updateGoal(goalVO);
	}

	@Override
	public void deleteGoal(int channelPlayGoalIdx) throws Exception {
		channelPlayMapper.deleteGoal(channelPlayGoalIdx);
	}

	@Override
	public PlayresultVO totalScore(int channelPlayIdx) throws Exception {
		PlayresultVO totalScoreVO = new PlayresultVO();
		
		totalScoreVO.setChannelPlayIdx(channelPlayIdx);
		totalScoreVO.setSearchCode("H");
		
		totalScoreVO.setHome(channelPlayMapper.totalScore(totalScoreVO));
		
		totalScoreVO.setSearchCode("A");
		
		totalScoreVO.setAway(channelPlayMapper.totalScore(totalScoreVO));
		
		return totalScoreVO;
	}
	
	@Override
	public List<TeamVO> selectTeamList(int channelPlayIdx) throws Exception {
		return channelPlayMapper.selectTeamList(channelPlayIdx);
	}
	
	@Override
	public void updateChannelPlayFormation(HashMap<String, String> formationInfoMap) throws Exception {
		channelPlayMapper.updateChannelPlayFormation(formationInfoMap);
	}

	@Override
	public void createPlayInfo(TeamVO teamVO, List<TeamPlayerVO> playerList) throws Exception {
		int teamIdx = channelPlayMapper.insertTeam(teamVO);
		
		for(int i = 0; i<playerList.size(); i++) {
			playerList.get(i).setTeamIdx(teamIdx);
		}
		
		teamPlayerMapper.insertPlayerList(playerList);
		
		teamVO.setTeamIdx(teamIdx);
		
		List<Integer> playerIndexList = teamPlayerMapper.playerIndexList(teamVO);
		
		List<PlayresultVO> tempPlayresultList = new ArrayList<PlayresultVO>();
		
		for(int i = 0; i<playerIndexList.size(); i++) {
			PlayresultVO tempPlayresultVO = new PlayresultVO();
			
			tempPlayresultVO.setChannelPlayIdx(teamVO.getChannelPlayIdx());
			tempPlayresultVO.setTeamIdx(teamIdx);
			tempPlayresultVO.setTeamPlayerIdx(playerIndexList.get(i));
		}
		
		teamPlayerMapper.tempPlayresult(tempPlayresultList);
	}

	@Override
	public void insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
		channelPlayMapper.insertChannelPlay(channelPlayVO);
		
	}
}
