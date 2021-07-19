package com.soccer.web.channel.member.vo;

import lombok.Data;

public @Data class MemberVO {
	private int memberIdx;
	private int userIdx;
	private int channelIdx;
	private String memberDate;
	private String memberNick;
	private String memberPosition;
	private String memberImage;
}
