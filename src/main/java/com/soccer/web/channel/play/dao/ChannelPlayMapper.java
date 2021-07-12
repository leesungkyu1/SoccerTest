package com.soccer.web.channel.play.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;

@Mapper
public interface ChannelPlayMapper {
	
	// 영상 게시글 리스트의 개수를 불러오는 메서드
	int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception;
	
	// 영상 게시글의 리스트를 불러오는 메서드
	List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception;
	
	// 영상 게시글의 세부사항을 불러오는 메서드
	ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception;
	
	// 영상 게시글을 추가할 때 사용되는 메서드
	void insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;

	//채널 Info용 PlayList
	List<ChannelPlayVO> selectChannelMainPlayList(ChannelBoardVO channelBoardVO) throws Exception;
}
