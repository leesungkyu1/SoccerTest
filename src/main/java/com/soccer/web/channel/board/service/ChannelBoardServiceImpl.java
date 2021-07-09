package com.soccer.web.channel.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.board.dao.ChannelBoardMapper;
import com.soccer.web.channel.board.vo.ChannelBoardVO;

@Service("channelBoard")
public class ChannelBoardServiceImpl implements ChannelBoardService{
	
	@Autowired
	private ChannelBoardMapper channelBoardMapper;

	@Override
	public List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO) {
		return channelBoardMapper.selectChannelBoardList(channelBoardVO);
	}

	@Override
	public int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO) {
		return channelBoardMapper.selectChannelBoardListTotCnt(channelBoardVO);
	}

	@Override
	public ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx) {
		return channelBoardMapper.selectChannelBoardDetail(channelBoardIdx);
	}

	@Override
	public void insertChannelBoard(ChannelBoardVO channelBoardVO) {
		channelBoardMapper.insertChannelBoard(channelBoardVO);
		
	}

	@Override
	public void updateChannelBoard(ChannelBoardVO channelBoardVO) {
		channelBoardMapper.updateChannelBoard(channelBoardVO);
		
	}

	@Override
	public void deleteChannelBoard(int channelBoardIdx) {
		channelBoardMapper.deleteChannelBoard(channelBoardIdx);
	}
	
}
