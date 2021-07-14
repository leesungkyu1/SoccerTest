package com.soccer.web.channel.play.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.board.vo.ChannelBoardVO;
import com.soccer.web.channel.play.dao.ChannelPlayMapper;
import com.soccer.web.channel.play.vo.ChannelPlayVO;
import com.soccer.web.channel.play.vo.PlayMatchingVO;

@Service("channelPlayService")
public class ChannelPlayServiceImpl implements ChannelPlayService {
	
	@Autowired
	private ChannelPlayMapper channelPlayMapper;

	@Override
	public int selectChannelPlayListTotCnt(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.selectChannelPlayListTotCnt(channelPlayVO);
	}

	@Override
	public List<ChannelPlayVO> selectChannelPlayList(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.selectChannelPlayList(channelPlayVO);
	}

	@Override
	public ChannelPlayVO selectChannelPlayDetail(int channelPlayIdx) throws Exception {
		return channelPlayMapper.selectChannelPlayDetail(channelPlayIdx);
	}
	
	@Override
	public int insertChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
		return channelPlayMapper.insertChannelPlay(channelPlayVO);
	}
	
	@Override
	public void updateChannelPlay(ChannelPlayVO channelPlayVO) throws Exception {
		channelPlayMapper.updateChannelPlay(channelPlayVO);
	}
	
	@Override
	public void deleteChannelPlay(int channelPlayIdx) throws Exception {
		channelPlayMapper.deleteChannelPlay(channelPlayIdx);
	}

	@Override
	public List<ChannelPlayVO> opponentList(Integer channelIdx) throws Exception {
		return channelPlayMapper.opponentList(channelIdx);
	}

	@Override
	public void insertMatching(PlayMatchingVO playMatchingVO) throws Exception {
		channelPlayMapper.insertMatching(playMatchingVO);
	}

	@Override
	public PlayMatchingVO waitingMatchingList(Integer channelIdx) throws Exception {
		PlayMatchingVO waitingMatchingList = new PlayMatchingVO();
		
		waitingMatchingList.setApplyingList(channelPlayMapper.applyingMatchingList(channelIdx));
		waitingMatchingList.setWaitingList(channelPlayMapper.waitingMatchingList(channelIdx));
		
		return waitingMatchingList;
	}

	@Override
	public void applyMatching(Integer matchingIdx) throws Exception {
		channelPlayMapper.applyMatching(matchingIdx);
	}

	@Override
	public void denieMatching(Integer matchingIdx) throws Exception {
		channelPlayMapper.denieMatching(matchingIdx);
	}

}
