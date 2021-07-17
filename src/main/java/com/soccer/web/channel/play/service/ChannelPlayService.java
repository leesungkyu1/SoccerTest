package com.soccer.web.channel.play.service;

import java.util.HashMap;
import java.util.List;

import com.soccer.web.channel.play.vo.ChannelPlayGoalVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;

public interface ChannelPlayService {

	int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception;
	
	List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception;
	
	ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception;
	
	void insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	void deleteChannelPlay(int channelPlayIdx) throws Exception;

	void insertGoal(ChannelPlayGoalVO goalVO) throws Exception;

	List<ChannelPlayGoalVO> goalList(int channelPlayIdx) throws Exception;

	void updateGoal(ChannelPlayGoalVO goalVO) throws Exception;

	void deleteGoal(int channelPlayGoalIdx) throws Exception;

	PlayresultVO totalScore(int channelPlayIdx) throws Exception;

	List<TeamVO> selectTeamList(int channelPlayIdx) throws Exception;

	void updateChannelPlayFormation(HashMap<String, String> updateFormationInfoMap) throws Exception;

	void createPlayInfo(TeamVO teamVO, List<TeamPlayerVO> playerList) throws Exception;
}
