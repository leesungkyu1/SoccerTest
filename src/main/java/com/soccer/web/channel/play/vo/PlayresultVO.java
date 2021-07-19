package com.soccer.web.channel.play.vo;

import lombok.Data;

public @Data class PlayresultVO {
	
	private int playresultIdx;
	private int channelPlayIdx;
	private int teamIdx;
	private int teamPlayerIdx;
	private int playresultTotaltackle;
	private int playresultSuccesstackle;
	private int playresultTotalcross;
	private int playresultSuccesscross;
	private int playresultTotalcornerkick;
	private int playresultSuccesscornerkick;
	private int playresultTotalfreekick;
	private int playresultSuccessfreekick;
	private int playresultTotalshooting;
	private int playresultSuccessshooting;
	private int playresultTotalassist;
	private int playresultSuccessassist;
	private int playresultTotalpass;
	private int playresultSuccesspass;
	private int playresultTotalcontention;
	private int playresultSuccesscontention;
	
	private String searchCode;
	
	private String channelName;
	private String channelImage;
	private String teamName;
	private String regionName;
	
	private PlayresultVO home;
	private PlayresultVO away;
}
