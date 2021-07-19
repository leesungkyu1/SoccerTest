package com.soccer.web.channel.board.service;

import java.util.List;

import com.soccer.web.channel.board.vo.ChannelBoardVO;

public interface ChannelBoardService {

	List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO) throws Exception;
	
	int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO) throws Exception;
	
	ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx) throws Exception;
	
	void insertChannelBoard(ChannelBoardVO channelBoardVO) throws Exception;
	
	void updateChannelBoard(ChannelBoardVO channelBoardVO) throws Exception;
	
	void deleteChannelBoard(int channelBoardIdx) throws Exception;
}
