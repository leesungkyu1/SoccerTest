package com.soccer.web.channel.vo;

import lombok.Data;

public @Data class ChannelVO extends ChannelDefaultVO{
	private int channelIdx;
	private int userIdx;
	private String channelName;
	private String channelImage;
	private int regionIdx;
	private int channelMax;
}
