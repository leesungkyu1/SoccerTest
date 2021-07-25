package com.soccer.web.channel.board.vo;

import lombok.Data;

public @Data class ChannelBoardVO extends ChannelBoardDefaultVO{

	private int channelBoardIdx;
	private int memberIdx;
	private int channelIdx;
	private String channelBoardTitle;
	private String channelBoardType;
	private String channelBoardDesc;
	private String channelBoardDate;
	private String memberNick;
	private int userIdx;
	
	private String newChannelBoard;
}
