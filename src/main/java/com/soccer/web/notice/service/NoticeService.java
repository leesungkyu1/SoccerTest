package com.soccer.web.notice.service;

import java.util.List;

import com.soccer.web.notice.vo.NoticeVO;

public interface NoticeService {

	List<NoticeVO> selectNoticeList(NoticeVO noticeVO);
	
	int selectNoticeListTotCnt(NoticeVO noticeVO);
	
	NoticeVO selectNoticeDetail(int noticeIdx);
	
	void insertNotice(NoticeVO noticeVO);
	
	void updateNotice(NoticeVO noticeVO);
	
	void deleteNotice(int noticeIdx);
}
