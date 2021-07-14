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
	public List<NoticeVO> selectNoticeList(NoticeVO noticeVO) {
		return noticeMapper.selectNoticeList(noticeVO);
	}

	@Override
	public int selectNoticeListTotCnt(NoticeVO noticeVO) {
		return noticeMapper.selectNoticeListTotCnt(noticeVO);
	}

	@Override
	public NoticeVO selectNoticeDetail(int noticeIdx) {
		return noticeMapper.selectNoticeDetail(noticeIdx);
	}

	@Override
	public int insertNotice(NoticeVO noticeVO) {
//		System.out.println("noticeVO - title : " + noticeVO.getNoticeTitle());
//		System.out.println("noticeVO - desc : " + noticeVO.getNoticeDesc());
//		System.out.println("noticeVO - userIdx : " + noticeVO.getUserIdx());
//		System.out.println("noticeVO - type : " + noticeVO.getNoticeTitle());
		return noticeMapper.insertNotice(noticeVO);
		
	}

	@Override
	public void updateNotice(NoticeVO noticeVO) {
		noticeMapper.updateNotice(noticeVO);
		
	}

	@Override
	public void deleteNotice(int noticeIdx) {
		noticeMapper.deleteNotice(noticeIdx);
	}
	
}
