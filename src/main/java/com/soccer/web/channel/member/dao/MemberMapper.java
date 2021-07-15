package com.soccer.web.channel.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

@Mapper
public interface MemberMapper {
	
	//채널 회원목록
	List<MemberVO> memberList(ChannelVO channelVO) throws Exception;
	
	//채널 회원삭제
	void memberDelete(Integer memberIdx, Integer channelIdx) throws Exception;
	
	//채널 회원 존재여부
	int checkMember(MemberVO memberVO) throws Exception;
	
	//회원 저장
	void memberInsert(MemberVO memberVO) throws Exception;
	
	//회원수 
	Integer memberCount(Integer channelIdx) throws Exception;

	//경기용 - 채널로 멤버 검색
	List<MemberVO> searchByChannel(Integer channelIdx) throws Exception;

}