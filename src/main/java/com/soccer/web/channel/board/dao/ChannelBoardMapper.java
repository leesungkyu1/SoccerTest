package com.soccer.web.channel.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.vo.ChannelVO;

@Mapper
public interface ChannelBoardMapper {
	
	List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO);
	
	int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO);
	
	ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx);
	
	int insertChannelBoard(ChannelBoardVO channelBoardVO);
	
	void updateChannelBoard(ChannelBoardVO channelBoardVO);
	
	void deleteChannelBoard(int channelBoardIdx);

	List<ChannelBoardVO> selectChannelMainBoardList(ChannelVO channelVO);
	
}
