package com.soccer.web.channel.play.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayMatchingVO;
import com.soccer.web.channel.play.vo.TeamVO;
import com.soccer.web.channel.vo.ChannelVO;

@Mapper
public interface ChannelPlayMapper {
	
	// 영상 게시글 리스트의 개수를 불러오는 메서드
	int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception;
	
	// 영상 게시글의 리스트를 불러오는 메서드
	List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception;
	
	// 영상 게시글의 세부사항을 불러오는 메서드
	ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception;
	
	// 영상 게시글을 추가할 때 사용되는 메서드
	int insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	// 영상 게시글을 수정할 때 사용되는 메서드
	void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception;
	
	// 영상 게시글을 삭제할 때 사용되는 메서드
	void deleteChannelPlay(int channelPlayIdx) throws Exception;

	//채널 Info용 PlayList
	List<ChannelPlayVO> selectChannelMainPlayList(ChannelVO channelVO) throws Exception;
	
	//경기 수락이 신청되지 않은 경기 목록 가져오기
	List<ChannelPlayVO> opponentList(Integer playIdx) throws Exception;
	
	//경기 매칭(경기 게시판 연동) 신청
	void insertMatching(PlayMatchingVO playMatchingVO) throws Exception;
	
	//내가 신청한 매칭 목록
	List<PlayMatchingVO> applyingMatchingList(Integer channelIdx) throws Exception;

	//상대가 신청한 매칭 목록
	List<PlayMatchingVO> waitingMatchingList(Integer channelIdx) throws Exception;

	//매칭 수락
	void applyMatching(Integer matchingIdx) throws Exception;
	
	//매칭 거절
	void denieMatching(Integer matchingIdx) throws Exception;

	// 영상 게시글에 연관된 팀을 가져오는 메서드
	List<TeamVO> selectTeamList(int channelPlayIdx) throws Exception;

	void updateChannelPlayFormation(HashMap<String, String> formationInfoMap) throws Exception;
}
