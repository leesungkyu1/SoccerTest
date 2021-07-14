package com.soccer.web.channel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.board.dao.ChannelBoardMapper;
import com.soccer.web.channel.dao.ChannelMapper;
import com.soccer.web.channel.play.dao.ChannelPlayMapper;
import com.soccer.web.channel.play.vo.ViewResultColumnVO;
import com.soccer.web.channel.vo.ChannelVO;

@Service("channelService")
public class ChannelServiceImpl implements ChannelService{
	
	@Autowired
	ChannelMapper channelDAO;
	
	@Autowired
	ChannelBoardMapper channelBoardDAO;
	
	@Autowired
	ChannelPlayMapper ChannelPlayDAO;
	
	//채널 목록
	@Override
	public List<ChannelVO> channelList(ChannelVO channelVO) throws Exception{
		return channelDAO.channelList(channelVO);
	}
	
	//채널 가입가능 회원 수
	@Override
	public Integer channelMemeberMax(Integer channelIdx) throws Exception{
		return channelDAO.channelMemeberMax(channelIdx);
	}

	//채널 생성
	@Override
	public int channelInsert(ChannelVO channelVO) throws Exception{
		return channelDAO.channelInsert(channelVO);
	}
	
	//채널 수정
	@Override
	public void channelUpdate(ChannelVO channelVO) throws Exception{
		channelDAO.channelUpdate(channelVO);
	}
	
	//채널 모든 정보
	@Override
	public ChannelVO channelInfo(ChannelVO channelVO) throws Exception {	
		ChannelVO channelInfo = new ChannelVO();
		
		channelInfo.setChannelBoardList(channelBoardDAO.selectChannelMainBoardList(channelVO));
		channelInfo.setChannelPlayList(ChannelPlayDAO.selectChannelMainPlayList(channelVO));
		
		return channelInfo;
	}
	
	//컬럼 보여주는 설정 정보 저장
	@Override
	public void insertViewResultColumn(ChannelVO channelVO) throws Exception{
		channelDAO.insertViewResultColumn(channelVO);
	}
	
	//채널 기본정보 가져오기
	@Override
	public ChannelVO channelSelect(Integer channelIdx) throws Exception{
		return channelDAO.channelSelect(channelIdx);
	}
	
	//저장된 컬럼정보 가져오기
	@Override
	public ViewResultColumnVO selectViewResultColumn(Integer channelIdx) throws Exception{
		return channelDAO.selectViewResultColumn(channelIdx);
	}
	
	//컬럼 정보 수정하기
	@Override
	public void viewResultColumnUpdate(ViewResultColumnVO colVO) throws Exception{
		channelDAO.viewResultColumnUpdate(colVO);
	}
}
