package com.soccer.web.channel.member.apply.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.channel.member.apply.vo.ApplyVO;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;
import com.soccer.web.user.vo.UserVO;

@Mapper
public interface ApplyMapper {
	
	//채널 회원가입 신청 목록
	List<ApplyVO> applyList(ChannelVO channelVO) throws Exception;
	
	//채널 회원가입 신청
	void memberApply(MemberVO memberVO) throws Exception;
	
	//승인정보 삭제
	void applyDelete(Integer memberIdx, int channelIdx) throws Exception;
	
	//채널 회원가입 승인
	void memberAccept(Integer applyIdx) throws Exception;

	//채널 회원가입 거절
	void memberDenie(Integer applyIdx) throws Exception;
	
	//채널 회원가입 신청 유저 고유번호, 채널 고유번호 추출
	MemberVO searchMember(Integer applyIdx);
}
