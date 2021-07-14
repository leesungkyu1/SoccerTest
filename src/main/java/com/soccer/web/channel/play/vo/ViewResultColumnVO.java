package com.soccer.web.channel.play.vo;

import lombok.Data;

public @Data class ViewResultColumnVO {
	private int viewResultColumnIdx;
	private int channelIdx;
	private String viewResultColumnTackle;
	private String viewResultColumnCross;
	private String viewResultColumnCornerKick;
	private String viewResultColumnFreeKick;
	private String viewResultColumnShooting;
	private String viewResultColumnAssist;
	private String viewResultColumnPass;
	private String viewResultColumnContention;
}
