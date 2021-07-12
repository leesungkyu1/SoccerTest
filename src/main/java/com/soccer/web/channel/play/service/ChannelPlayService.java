package com.soccer.web.channel.play.service;

import java.util.List;

import com.soccer.web.channel.play.vo.ChannelPlayVO;

public interface ChannelPlayService {

	int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception;
	
	List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception;
	
	ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception;
	
	void insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
}
