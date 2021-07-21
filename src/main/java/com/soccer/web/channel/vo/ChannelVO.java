package com.soccer.web.channel.vo;

import java.util.List;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.play.vo.ChannelPlayVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

public @Data class ChannelVO extends ChannelDefaultVO{
	private int channelIdx;
	private int userIdx;
	private String channelName;
	private String channelImage;
	private int regionIdx;
	private int channelMax;
	
	private String regionName;
	private int channelMemberCount;
	
	private String joinCheck = "F";
	
	private List<ChannelBoardVO> channelBoardList;
	private List<ChannelPlayVO> channelPlayList;
	
	private String searchWord;
}
