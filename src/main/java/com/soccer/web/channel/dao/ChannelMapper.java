package com.soccer.web.channel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.vo.ChannelVO;

@Mapper
public interface ChannelMapper {

	//채널 목록
	List<ChannelVO> channelList(ChannelVO channelVO) throws Exception;
	
	//채널 회원가입 가능 수
	Integer channelMemeberMax(Integer channelIdx) throws Exception;
	
	//채널 생성
	void channelInsert(ChannelVO channelVO) throws Exception;

	//채널 수정
	void channelUpdate(ChannelVO channelVO) throws Exception;
}
