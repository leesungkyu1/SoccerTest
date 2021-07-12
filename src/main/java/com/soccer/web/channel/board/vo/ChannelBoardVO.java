package com.soccer.web.channel.board.vo;

import lombok.Data;

public @Data class ChannelBoardVO extends ChannelBoardDefaultVO{

	private int channelBoardIdx;
	private int memberIdx;
	private int cahnnelIdx;
	private String channelBoardTitle;
	private String channelBoardType;
	private String channelBoardDesc;
	private String channelBoardDate;
}
