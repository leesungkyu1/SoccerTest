package com.soccer.web.notice.vo;

import lombok.Data;

public @Data class NoticeVO extends NoticeDefaultVO{

	private int noticeIdx;
	private int userIdx;
	private String noticeTitle;
	private String noticeType;
	private String noticeDesc;
	private String noticeDate;
	
	private String newNotice;
}
