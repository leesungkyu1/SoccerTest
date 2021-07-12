package com.soccer.web.channel.member.apply.service;

import java.util.List;

import com.soccer.web.channel.member.apply.vo.ApplyVO;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

public interface ApplyService {

	List<ApplyVO> applyList(ChannelVO channelVO) throws Exception;

	void memberApply(MemberVO memberVO) throws Exception;

	void applyDelete(Integer memberIdx, int channelIdx) throws Exception;

	void memberAccept(Integer applyIdx) throws Exception;
	
}
