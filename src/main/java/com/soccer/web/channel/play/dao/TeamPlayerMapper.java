package com.soccer.web.channel.play.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamPlayerMapper {

	// 경기에서 선수를 추가할 때 사용되는 메서드
	void insertTeamPlayer(HashMap<String, String> teamPlayParameterMap);
	
	
}
