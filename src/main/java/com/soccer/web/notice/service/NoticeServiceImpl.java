package com.soccer.web.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soccer.web.notice.dao.NoticeMapper;
import com.soccer.web.notice.vo.NoticeVO;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public List<NoticeVO> selectNoticeList(NoticeVO noticeVO) throws Exception {
		return noticeMapper.selectNoticeList(noticeVO);
	}

	@Override
	public int selectNoticeListTotCnt(NoticeVO noticeVO) throws Exception {
		return noticeMapper.selectNoticeListTotCnt(noticeVO);
	}

	@Override
	public NoticeVO selectNoticeDetail(int noticeIdx) throws Exception {
		return noticeMapper.selectNoticeDetail(noticeIdx);
	}

	@Override
	public void insertNotice(NoticeVO noticeVO) throws Exception {
		noticeMapper.insertNotice(noticeVO);
	}

	@Override
	public void updateNotice(NoticeVO noticeVO) throws Exception {
		noticeMapper.updateNotice(noticeVO);
		
	}

	@Override
	public void deleteNotice(int noticeIdx) throws Exception {
		noticeMapper.deleteNotice(noticeIdx);
	}
	
}
