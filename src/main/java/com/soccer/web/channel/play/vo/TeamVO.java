package com.soccer.web.channel.play.vo;

import lombok.Data;

public @Data class TeamVO {

	private int teamIdx;
	private int channelPlayIdx;
	private int channelIdx;
	private String teamName;
	private String teamType;
}
