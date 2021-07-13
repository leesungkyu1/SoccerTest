package com.soccer.web.channel.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.member.dao.MemberMapper;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberMapper memberDAO; 
	
	//채널 회원목록
	@Override
	public List<MemberVO> memberList(ChannelVO channelVO) throws Exception{
		return memberDAO.memberList(channelVO);
	}
	
	//채널 회원삭제
	@Override
	public void memberDelete(Integer memberIdx, Integer channelIdx) throws Exception{
		memberDAO.memberDelete(memberIdx, channelIdx);
	}
	
	//채널 회원 존재여부
	@Override
	public int checkMember(MemberVO memberVO) throws Exception{
		return memberDAO.checkMember(memberVO);
	}
	
	//채널 회원 수
	@Override
	public Integer memberCount(Integer channelIdx) throws Exception{
		return memberDAO.memberCount(channelIdx);
	}

}
