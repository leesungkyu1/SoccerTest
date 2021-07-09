package com.soccer.web.channel.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.board.vo.ChannelBoardVO;

@Mapper
public interface ChannelBoardMapper {
	
	List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO);
	
	int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO);
	
	ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx);
	
	void insertChannelBoard(ChannelBoardVO channelBoardVO);
	
	void updateChannelBoard(ChannelBoardVO channelBoardVO);
	
	void deleteChannelBoard(int channelBoardIdx);
	
}
