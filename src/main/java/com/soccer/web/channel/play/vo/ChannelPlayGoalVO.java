package com.soccer.web.channel.play.vo;

import lombok.Data;

public @Data class ChannelPlayGoalVO {
	private int channelPlayGoalIdx; 
	private int channelPlayIdx;
	private String channelPlayGoalName;
	private String channelPlayGoalType;
	private String channelPlayGoalTime;
}
