package com.soccer.web.channel.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.vo.ChannelVO;

@Mapper
public interface ChannelBoardMapper {
	
	List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO) throws Exception;
	
	int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO) throws Exception;
	
	ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx) throws Exception;
	
	void insertChannelBoard(ChannelBoardVO channelBoardVO) throws Exception;
	
	void updateChannelBoard(ChannelBoardVO channelBoardVO) throws Exception;
	
	void deleteChannelBoard(int channelBoardIdx) throws Exception;

	List<ChannelBoardVO> selectChannelMainBoardList(ChannelVO channelVO) throws Exception;

	List<ChannelBoardVO> selectImportantChannelBoardList(ChannelBoardVO channelBoardVO) throws Exception;
	
}
