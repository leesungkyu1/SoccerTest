package com.soccer.web.channel.board.service;

import java.util.List;

import com.soccer.web.channel.board.vo.ChannelBoardVO;

public interface ChannelBoardService {

	List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO);
	
	int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO);
	
	ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx);
	
	void insertChannelBoard(ChannelBoardVO channelBoardVO);
	
	void updateChannelBoard(ChannelBoardVO channelBoardVO);
	
	void deleteChannelBoard(int channelBoardIdx);
}
