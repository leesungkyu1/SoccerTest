package com.soccer.web.channel.play.vo;

import lombok.Data;

public @Data class TeamPlayerVO {

	private int teamPlayerIdx;
	private int userIdx;
	private int teamIdx;
	private int channelPlayIdx;
	private String teamPlayerPosition;
	private int teamPlayerFormationNumber;
	private String memberImage;
	
}
