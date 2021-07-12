package com.soccer.web.channel.play.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.play.dao.ChannelPlayMapper;
import com.soccer.web.channel.play.vo.ChannelPlayVO;

@Service("channelPlayService")
public class ChannelPlayServiceImpl implements ChannelPlayService {
	
	@Autowired
	private ChannelPlayMapper channelPlayMapper;

	@Override
	public int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.selectChannelPlayListTotCnt(channelPlayVO);
	}

	@Override
	public List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.selectChannelPlayList(channelPlayVO);
	}

	@Override
	public ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception {
		return channelPlayMapper.selectChannelPlayDetail(channelPlayIdx);
	}
	
	@Override
	public int insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.insertChannelPlay(channelPlayVO);
	}
	
	@Override
	public void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
		channelPlayMapper.updateChannelPlay(channelPlayVO);
	}
	
	@Override
	public void deleteChannelPlay(int channelPlayIdx) throws Exception {
		channelPlayMapper.deleteChannelPlay(channelPlayIdx);
	}
}
