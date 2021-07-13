package com.soccer.web.channel.member.apply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.member.apply.dao.ApplyMapper;
import com.soccer.web.channel.member.apply.vo.ApplyVO;
import com.soccer.web.channel.member.dao.MemberMapper;
import com.soccer.web.channel.member.vo.MemberVO;
import com.soccer.web.channel.vo.ChannelVO;

@Service
public class ApplyServiceImpl implements ApplyService{
	
	@Autowired
	ApplyMapper applyDAO;
	
	@Autowired
	MemberMapper memberDAO;
	
	//채널 회원가입 신청 목록
	@Override
	public List<ApplyVO> applyList(ChannelVO channelVO) throws Exception{
		return applyDAO.applyList(channelVO);
	}
	
	//채널 회원가입 신청
	@Override
	public void memberApply(MemberVO memberVO) throws Exception{
		//임시 회원정보 저장
		memberDAO.memberInsert(memberVO);
		applyDAO.memberApply(memberVO);
	}
	
	//승인 정보 삭제
	@Override
	public void applyDelete(Integer memberIdx, int channelIdx) throws Exception{
		applyDAO.applyDelete(memberIdx, channelIdx);
	}
	
	//멤버 가입 승인
	@Override
	public void memberAccept(Integer applyIdx) throws Exception{
		applyDAO.memberAccept(applyIdx);
	}
	
	//멤버 가입 거절
	public void memberDenie(Integer applyIdx) throws Exception{
		MemberVO searchMember = applyDAO.searchMember(applyIdx);
		memberDAO.memberDelete(searchMember.getMemberIdx(), searchMember.getChannelIdx());
		
		applyDAO.memberDenie(applyIdx);
	}
}
