package com.soccer.web.channel.play.vo;

import java.util.List;

import lombok.Data;

public @Data class TeamPlayerVO {

	private int teamPlayerIdx;
	private int userIdx;
	private int teamIdx;
	private int channelPlayIdx;
	private String teamPlayerPosition;
	private int teamPlayerFormationNumber;
	
	private List<TeamPlayerVO> teamPlayerVOList;
	private String memberImage;
	
}
