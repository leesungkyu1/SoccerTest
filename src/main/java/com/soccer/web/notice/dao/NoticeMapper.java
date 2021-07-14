package com.soccer.web.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soccer.web.notice.vo.NoticeVO;

@Mapper
public interface NoticeMapper {
	
	
	List<NoticeVO> selectNoticeList(NoticeVO noticeVO);
	
	int selectNoticeListTotCnt(NoticeVO noticeVO);
	
	NoticeVO selectNoticeDetail(int noticeIdx);
	
	int insertNotice(NoticeVO noticeVO);
	
	void updateNotice(NoticeVO noticeVO);
	
	void deleteNotice(int noticeIdx);
	
}
