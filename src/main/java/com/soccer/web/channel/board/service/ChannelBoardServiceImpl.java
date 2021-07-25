package com.soccer.web.channel.board.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.channel.board.dao.ChannelBoardMapper;
import com.soccer.web.channel.board.vo.ChannelBoardVO;

@Service("channelBoardService")
public class ChannelBoardServiceImpl implements ChannelBoardService{
	
	@Autowired
	private ChannelBoardMapper channelBoardMapper;

	@Override
	public List<ChannelBoardVO> selectChannelBoardList(ChannelBoardVO channelBoardVO) throws Exception {
		List<ChannelBoardVO> selectChannelBoardList = channelBoardMapper.selectChannelBoardList(channelBoardVO);
		
		return diffDay(selectChannelBoardList);
	}
	
	@Override
	public List<ChannelBoardVO> selectImportantChannelBoardList(ChannelBoardVO channelBoardVO) throws Exception {
		List<ChannelBoardVO> selectImportantChannelBoardList = channelBoardMapper.selectImportantChannelBoardList(channelBoardVO);
		
		return diffDay(selectImportantChannelBoardList);
	}

	@Override
	public int selectChannelBoardListTotCnt(ChannelBoardVO channelBoardVO) throws Exception {
		return channelBoardMapper.selectChannelBoardListTotCnt(channelBoardVO);
	}

	@Override
	public ChannelBoardVO selectChannelBoardDetail(int channelBoardIdx) throws Exception {
		return channelBoardMapper.selectChannelBoardDetail(channelBoardIdx);
	}

	@Override
	public void insertChannelBoard(ChannelBoardVO channelBoardVO) throws Exception {
		channelBoardMapper.insertChannelBoard(channelBoardVO);
	}

	@Override
	public void updateChannelBoard(ChannelBoardVO channelBoardVO) throws Exception {
		channelBoardMapper.updateChannelBoard(channelBoardVO);
		
	}

	@Override
	public void deleteChannelBoard(int channelBoardIdx) throws Exception {
		channelBoardMapper.deleteChannelBoard(channelBoardIdx);
	}
	
	private List<ChannelBoardVO> diffDay(List<ChannelBoardVO> channelBoardList) throws Exception {
		for(int i=0; i<channelBoardList.size(); i++) {
			//System.out.println(selectImportantNoticeList.get(i).getNoticeDate());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date channelBoardDate = sdf.parse(channelBoardList.get(i).getChannelBoardDate());
			Date nowDate = new Date();
					
			long diff = nowDate.getTime() - channelBoardDate.getTime();
			
			TimeUnit time = TimeUnit.DAYS;
			long diffDay = time.convert(diff, TimeUnit.MILLISECONDS);
			
			if(diffDay > 7) {
				channelBoardList.get(i).setNewChannelBoard("N");
			}
		}
		
		return channelBoardList;
	}
	
}
