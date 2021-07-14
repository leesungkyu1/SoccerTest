package com.soccer.web.channel.play.vo;

import java.util.List;

import lombok.Data;

public @Data class PlayMatchingVO {
	private int channelPlayMatchingIdx;
	private int channelPlayMatchingPlay1;
	private int channelPlayMatchingPlay2;
	private String channelPlayMatchingStep;
	
	//적절한 이름 붙이기 내가 신청한 목록
	private List<PlayMatchingVO> applyingList;
	
	//신청 대기중 목록
	private List<PlayMatchingVO> waitingList;
}
