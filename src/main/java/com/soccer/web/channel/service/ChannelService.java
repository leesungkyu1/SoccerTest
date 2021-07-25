package com.soccer.web.channel.service;

import java.util.List;

import com.soccer.web.channel.play.vo.ViewResultColumnVO;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.user.vo.UserVO;

public interface ChannelService {

	List<ChannelVO> channelList(ChannelVO channelVO) throws Exception;

	Integer channelMemberMax(Integer channelIdx) throws Exception;

	int channelInsert(ChannelVO channelVO) throws Exception;

	void channelUpdate(ChannelVO channelVO) throws Exception;

	ChannelVO channelInfo(ChannelVO channelVO) throws Exception;

	void insertViewResultColumn(ChannelVO channelVO) throws Exception;

	ChannelVO channelSelect(Integer channelIdx) throws Exception;

	ViewResultColumnVO selectViewResultColumn(Integer channelIdx) throws Exception;

	void viewResultColumnUpdate(ViewResultColumnVO colVO) throws Exception;

	List<ChannelVO> selectChannelList(int channelPlayIdx);

	int selectChannelListTotCnt(ChannelVO channelVO) throws Exception;

	ChannelVO selectChannel(ChannelVO channelVO) throws Exception;

	List<Integer> joinChannelList(UserVO userVO) throws Exception;
}
