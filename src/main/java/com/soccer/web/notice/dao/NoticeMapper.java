package com.soccer.web.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.notice.vo.NoticeVO;

@Mapper
public interface NoticeMapper {
	
	
	List<NoticeVO> selectNoticeList(NoticeVO noticeVO) throws Exception;
	
	List<NoticeVO> selectImportantNoticeList(NoticeVO noticeVO) throws Exception;
	
	int selectNoticeListTotCnt(NoticeVO noticeVO) throws Exception;
	
	NoticeVO selectNoticeDetail(int noticeIdx) throws Exception;
	
	void insertNotice(NoticeVO noticeVO) throws Exception;
	
	void updateNotice(NoticeVO noticeVO) throws Exception;
	
	void deleteNotice(int noticeIdx) throws Exception;
	
}
