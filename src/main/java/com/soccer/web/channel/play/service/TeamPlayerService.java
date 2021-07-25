package com.soccer.web.channel.play.service;

import java.util.HashMap;
import java.util.List;

import com.soccer.web.channel.play.vo.PlayresultVO;
import com.soccer.web.channel.play.vo.TeamPlayerVO;
import com.soccer.web.channel.play.vo.TeamVO;

public interface TeamPlayerService {
	
	List<TeamPlayerVO> selectTeamPlayerList(int channelPlayIdx) throws Exception;
	
	List<TeamPlayerVO> selectHomeAwayTeamPlayerList(HashMap<String, String> teamInfoMap) throws Exception;
	
	List<PlayresultVO> selectPlayerResultVOList(int channelPlayIdx) throws Exception;

//	int insertTeamPlayer(TeamPlayerVO teamPlayerVO) throws Exception;
	
	void insertPlayresult(HashMap<String, Integer> playresultMap) throws Exception;
	
	void updateTeamPlayerPosition(TeamPlayerVO teamPlayerVO) throws Exception;
	
	void deleteTeamPlayer(TeamPlayerVO teamPlayerVO) throws Exception;
	
	TeamPlayerVO selectTeamPlayerDetail(HashMap<String, Integer> teamPlayerRequireMap) throws Exception;
	
	PlayresultVO selectTeamPlayerCurrentResult(TeamPlayerVO teamPlayerVO) throws Exception;
	
	PlayresultVO selectPlayerResultVO(int channelPlayIdx) throws Exception;
	
	void updatePlayresult(PlayresultVO playresultVO) throws Exception;

	void updateTeamPlayerFormation(TeamPlayerVO teamPlayerVO) throws Exception;
	
	void resultUpdate(List<PlayresultVO> resultVO) throws Exception;

	PlayresultVO selectPlayerresultVODetail(int teamPlayerIdx) throws Exception;

	PlayresultVO resultList(Integer playIdx) throws Exception;
	//추가하는것
	void insertTeamPlayer(HashMap<Object, Object> map);

	void insertTeam(TeamVO teamVO);

	
}
