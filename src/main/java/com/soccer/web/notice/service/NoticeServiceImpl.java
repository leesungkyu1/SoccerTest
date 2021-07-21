package com.soccer.web.notice.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
		List<NoticeVO> selectNoticeList = noticeMapper.selectNoticeList(noticeVO); 
		
		return diffDay(selectNoticeList);
	}
	
	@Override
	public List<NoticeVO> selectImportantNoticeList(NoticeVO noticeVO) throws Exception {
		List<NoticeVO> selectImportantNoticeList = noticeMapper.selectImportantNoticeList(noticeVO);
		
		return diffDay(selectImportantNoticeList);
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
	
	private List<NoticeVO> diffDay(List<NoticeVO> noticeList) throws Exception {
		for(int i=0; i<noticeList.size(); i++) {
			//System.out.println(selectImportantNoticeList.get(i).getNoticeDate());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date noticeDate = sdf.parse(noticeList.get(i).getNoticeDate());
			Date nowDate = new Date();
					
			long diff = nowDate.getTime() - noticeDate.getTime();
			
			TimeUnit time = TimeUnit.DAYS;
			long diffDay = time.convert(diff, TimeUnit.MILLISECONDS);
			
			if(diffDay > 7) {
				noticeList.get(i).setNewNotice("N");
			}
		}
		
		return noticeList;
	}
}
