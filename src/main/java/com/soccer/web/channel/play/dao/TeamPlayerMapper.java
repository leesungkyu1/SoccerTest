package com.soccer.web.channel.play.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;

@Mapper
public interface TeamPlayerMapper {

	// 경기의 선수 리스트를 가져오는 메서드
	List<TeamPlayerVO> selectTeamPlayerList(int channelPlayIdx) throws Exception;
	
	// 경기에서 선수를 추가할 때 사용되는 메서드 (player 추가)
	int insertTeamPlayer(HashMap<String, String> teamPlayParameterMap) throws Exception;
	
	// 경기에서 선수를 추가할 때 사용되는 메서드 (playresult 추가)
	void insertPlayresult(HashMap<String, Integer> playresultMap) throws Exception;
	
	// 경기에서 선수의 포지션을 변경할 때 사용되는 메서드
	void updateTeamPlayerPosition(TeamPlayerVO teamPlayerVO) throws Exception;
	
	// 경기에서 선수를 삭제할 때 사용되는 메서드
	void deleteTeamPlayer(int teamPlayerIdx) throws Exception;
	
	// 경기에서 선수를 눌렀을 때 사용되는 메서드 (선수의 정보)
	TeamPlayerVO selectTeamPlayerDetail(HashMap<String, Integer> teamPlayerRequireMap) throws Exception;
	
	// 선수의 최근 5경기 결과를 가져오는 메서드
	List<PlayresultVO> selectTeamPlayerCurrentResult(TeamPlayerVO teamPlayerVO) throws Exception;
	
	// 경기를 뛴 선수들의 playresult를 가져오는 메서드
	List<PlayresultVO> selectPlayerResultList(int channelPlayIdx) throws Exception;
	
	// 경기를 뛴 선수의 기록을 변경하는 메서드
	void updatePlayerPlayresult(PlayresultVO playresultVO) throws Exception;
	
}
