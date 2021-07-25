package com.soccer.web.channel.member.service;

import java.util.List;

import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

public interface MemberService {

	List<MemberVO> memberList(ChannelVO channelVO) throws Exception;
	
	void memberDelete(Integer memberIdx, Integer channelVO) throws Exception;

	int checkMember(MemberVO memberVO) throws Exception;

	Integer memberCount(Integer channelIdx) throws Exception;

	List<MemberVO> searchByChannel(Integer channelIdx) throws Exception;

	MemberVO selectMemberDetail(MemberVO tmpVO) throws Exception;
	
	List<MemberVO> memberList (int channelIdx) throws Exception;
	
}
