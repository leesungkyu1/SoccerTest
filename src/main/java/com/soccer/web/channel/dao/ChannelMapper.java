package com.soccer.web.channel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.play.vo.ViewResultColumnVO;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.user.vo.UserVO;

@Mapper
public interface ChannelMapper {

	//채널 목록
	List<ChannelVO> channelList(ChannelVO channelVO) throws Exception;
	
	//채널 회원가입 가능 수
	Integer channelMemberMax(Integer channelIdx) throws Exception;
	
	//채널 생성
	int channelInsert(ChannelVO channelVO) throws Exception;

	//채널 수정
	void channelUpdate(ChannelVO channelVO) throws Exception;
	
	//컬럼 보여주는 설정 정보 저장
	void insertViewResultColumn(ChannelVO channelVO) throws Exception;

	//채널 기본 정보 가져오기
	ChannelVO channelSelect(Integer channelIdx) throws Exception;

	//채널 컬럼 정보 가져오기
	ViewResultColumnVO selectViewResultColumn(Integer channelIdx) throws Exception;
	
	//채널 컬럼 정보 수정
	void viewResultColumnUpdate(ViewResultColumnVO colVO) throws Exception;

	// channelPlayIdx로 채널 리스트를 가져옴
	List<ChannelVO> selectChannelList(int channelPlayIdx);

	int selectChannelListTotCnt(ChannelVO channelVO) throws Exception;

	ChannelVO selectChannel(ChannelVO channelVO) throws Exception;

	List<Integer> joinChannelList(UserVO userVO) throws Exception;
}
