package com.soccer.web.notice.service;

import java.util.List;

import com.soccer.web.notice.vo.NoticeVO;

public interface NoticeService {

	List<NoticeVO> selectNoticeList(NoticeVO noticeVO) throws Exception;
	
	int selectNoticeListTotCnt(NoticeVO noticeVO) throws Exception;
	
	NoticeVO selectNoticeDetail(int noticeIdx) throws Exception;
	
	void insertNotice(NoticeVO noticeVO) throws Exception;
	
	void updateNotice(NoticeVO noticeVO) throws Exception;
	
	void deleteNotice(int noticeIdx) throws Exception;
}
