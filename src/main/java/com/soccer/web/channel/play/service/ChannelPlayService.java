package com.soccer.web.channel.play.service;

import java.util.HashMap;
import java.util.List;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayMatchingVO;
import com.soccer.web.channel.play.vo.TeamVO;
import com.soccer.web.channel.vo.ChannelVO;

public interface ChannelPlayService {

	int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception;
	
	List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception;
	
	ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception;
	
	int insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	void deleteChannelPlay(int channelPlayIdx) throws Exception;

	List<ChannelPlayVO> opponentList(Integer playIdx) throws Exception;

	void insertMatching(PlayMatchingVO playMatchingVO) throws Exception;

	PlayMatchingVO waitingMatchingList(Integer channelIdx) throws Exception;

	void applyMatching(Integer matchingIdx) throws Exception;

	void denieMatching(Integer matchingIdx) throws Exception;

	List<TeamVO> selectTeamList(int channelPlayIdx) throws Exception;

	void updateChannelPlayFormation(HashMap<String, String> updateFormationInfoMap) throws Exception;

}
