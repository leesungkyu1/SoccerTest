package com.soccer.web.user.vo;

import lombok.Data;

public @Data class UserVO {
	private int userIdx;
	private	String userId;
	private String userPassword;
	private int regionIdx;
	private String userMobile;
	private String userName;
	private String userNick;
	
	private String regionName;
}
