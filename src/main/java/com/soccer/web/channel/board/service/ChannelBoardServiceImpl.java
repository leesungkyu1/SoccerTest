package com.soccer.web.channel.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.board.dao.ChannelBoardMapper;
import com.soccer.web.channel.board.vo.ChannelBoardVO;

@Service("channelBoardService")
public class ChannelBoardServiceImpl implements ChannelBoardService{
	
	@Autowired
	private ChannelBoardMapper channelBoardMapper;

	@Override
	public List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO) throws Exception {
		return channelBoardMapper.selectChannelBoardList(channelBoardVO);
	}

	@Override
	public int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO) throws Exception {
		return channelBoardMapper.selectChannelBoardListTotCnt(channelBoardVO);
	}

	@Override
	public ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx) throws Exception {
		return channelBoardMapper.selectChannelBoardDetail(channelBoardIdx);
	}

	@Override
	public void insertChannelBoard(ChannelBoardVO channelBoardVO) throws Exception {
		channelBoardMapper.insertChannelBoard(channelBoardVO);
	}

	@Override
	public void updateChannelBoard(ChannelBoardVO channelBoardVO) throws Exception {
		channelBoardMapper.updateChannelBoard(channelBoardVO);
		
	}

	@Override
	public void deleteChannelBoard(int channelBoardIdx) throws Exception {
		channelBoardMapper.deleteChannelBoard(channelBoardIdx);
	}
	
}
