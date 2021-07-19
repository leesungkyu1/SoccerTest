package com.soccer.web.channel.member.apply.vo;

import lombok.Data;

public @Data class ApplyVO {
	private int applyIdx;
	private int userIdx;
	private int channelIdx;
	private String applyDate;
	private String applyStep;
	
	private String memberNick;
	private String memberImage;
}
