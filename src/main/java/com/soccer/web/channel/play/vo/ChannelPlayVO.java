package com.soccer.web.channel.play.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChannelPlayVO extends ChannelPlayDefaultVO{

	private int channelPlayIdx;
	private int memberIdx;
	private int channelIdx;
	private String channelPlayTitle;
	private String channelPlayDate;
	private String channelPlayImage;
	private String channelPlayVideo;
	private String channelPlayStep;
	private String channelPlayHomeFormation;
	private String channelPlayAwayFormation;
	private String memberNick;
	
	private String channelName;
}
