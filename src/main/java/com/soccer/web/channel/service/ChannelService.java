package com.soccer.web.channel.service;

import java.util.List;

import com.soccer.web.channel.vo.ChannelVO;

public interface ChannelService {

	List<ChannelVO> channelList(ChannelVO channelVO) throws Exception;

	Integer channelMemeberMax(Integer channelIdx) throws Exception;

	void channelInsert(ChannelVO channelVO) throws Exception;

	void channelUpdate(ChannelVO channelVO) throws Exception;

	ChannelVO channelInfo(ChannelVO channelVO) throws Exception;
}
