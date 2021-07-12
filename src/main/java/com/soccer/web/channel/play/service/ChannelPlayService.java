package com.soccer.web.channel.play.service;

import java.util.List;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;

public interface ChannelPlayService {

	int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception;
	
	List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception;
	
	ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception;
	
	int insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	void deleteChannelPlay(int channelPlayIdx) throws Exception;

}
