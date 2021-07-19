package com.soccer.web.channel.play.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.play.vo.ChannelPlayGoalVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayresultVO;
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

	//득점 기록 입력
	void insertGoal(ChannelPlayGoalVO goalVO) throws Exception;
	
	//득점 기록 리스트 가져오기
	List<ChannelPlayGoalVO> goalList(int channelPlayIdx) throws Exception;
	
	//득점 기록 수정
	void updateGoal(ChannelPlayGoalVO goalVO) throws Exception;
	
	//득점 기록 삭제
	void deleteGoal(int channelPlayGoalIdx) throws Exception;
	
	//우리팀,상대팀 기록 총점 가져오기
	PlayresultVO totalScore(PlayresultVO totalScoreVO) throws Exception;

	//우리팀 생성
	int insertTeam(TeamVO teamVO) throws Exception;

	// 영상 게시글에 연관된 팀을 가져오는 메서드
	List<TeamVO> selectTeamList(int channelPlayIdx) throws Exception;

	// 채널의 포메이션을 변경하는 메서드
	void updateChannelPlayFormation(HashMap<String, String> formationInfoMap) throws Exception;
}
