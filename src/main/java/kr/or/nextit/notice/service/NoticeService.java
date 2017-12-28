package kr.or.nextit.notice.service;

import java.util.List;
import java.util.Map;

import kr.or.nextit.notice.model.Notice;

public interface NoticeService {

	
	public List<Notice> getNoticeList(Map<String, Object> paramMap) throws Exception ;
	
	public Notice getNotice(int notice_seq_no) throws Exception;
	
	public int insertNotice(Notice notice) throws Exception;

	public int updateNotice(Notice notice) throws Exception;
	
	public int deleteNotice(int notice_seq_no) throws Exception;
}
