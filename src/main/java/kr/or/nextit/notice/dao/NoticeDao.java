package kr.or.nextit.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.nextit.notice.model.Notice;

public class NoticeDao {

	private static NoticeDao instance = new NoticeDao();
	
	private NoticeDao () {}
	
	public static NoticeDao getInstance() {
		
		if(instance == null) {
			instance = new NoticeDao();
		}
		
		return instance;
	}
	
	// 회원 목록 조회
	public List<Notice> selectNoticeList(Map<String, Object> paramMap, Connection conn) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
			query.append(" select ");
			query.append(" notice_seq_no,			");
			query.append(" notice_title,            ");
			query.append(" notice_content,          ");
			query.append(" notice_user_name ,       ");
			query.append(" notice_reg_date          ");
			query.append(" from tb_notice          ");
			query.append(" order by notice_reg_date desc    ");			
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		
			ResultSet rs = pstmt.executeQuery();
			
			List<Notice> noticeList = new ArrayList<>();
			
			while(rs.next()) {
				Notice notice = new Notice();
				notice.setNotice_seq_no(rs.getInt("notice_seq_no"));
				notice.setNotice_title(rs.getString("notice_title"));
				notice.setNotice_content(rs.getString("notice_content"));
				notice.setNotice_user_name(rs.getString("notice_user_name"));
				notice.setNotice_reg_date(rs.getString("notice_reg_date"));
				
				noticeList.add(notice);
			}
		
		return noticeList;
	}
	
	public Notice selectNotice(Connection conn, int notice_seq_no) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
			query.append(" select ");
			query.append(" notice_seq_no,			");
			query.append(" notice_title,            ");
			query.append(" notice_content,          ");
			query.append(" notice_user_name,        ");
			query.append(" notice_reg_date          ");
			query.append(" from tb_notice          ");
			query.append(" where notice_seq_no= ?          ");
			
			PreparedStatement pstmt = conn.prepareStatement(query.toString());
			
			pstmt.setInt(1, notice_seq_no);
			
			ResultSet rs = pstmt.executeQuery();
			
			Notice notice = null;
			if(rs.next()) {
				notice = new Notice();
				notice.setNotice_seq_no(rs.getInt("notice_seq_no"));
				notice.setNotice_title(rs.getString("notice_title"));
				notice.setNotice_content(rs.getString("notice_content"));
				notice.setNotice_user_name(rs.getString("notice_user_name"));
				notice.setNotice_reg_date(rs.getString("notice_reg_date"));
				
			}
		
		
		return notice;
		
		
	}
	
	public int insertNotice(Connection conn, Notice notice) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append("  INSERT INTO tb_notice (      ");
		query.append("     notice_seq_no,          ");
		query.append("     notice_title,           ");
		query.append("     notice_content,         ");
		query.append("     notice_user_name,       ");
		query.append("     notice_reg_date         ");
		query.append("   VALUES (                  ");
		query.append("     seq_notice.nextval,                    ");
		query.append("     ?,                    ");
		query.append("     ?,                    ");
		query.append("     ?,                    ");
		query.append("     sysdate                     ");
		query.append("  )                          ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int i = 1;
		pstmt.setString(i++, notice.getNotice_title());
		pstmt.setString(i++, notice.getNotice_content());
		pstmt.setString(i++, notice.getNotice_user_name());
		
		int updCnt = pstmt.executeUpdate();
		return updCnt;
		
	}
	
	public int updateNotice(Connection conn, Notice notice) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
	    query.append("  UPDATE tb_notice           ");
	    query.append("  SET                        ");
	    query.append("    ,  notice_title = ?      ");
	    query.append("    ,  notice_content = ?    ");
	    query.append("    ,  notice_user_name = ?  ");
	    query.append("    ,  notice_reg_date = sysdate  ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int i = 1;
		pstmt.setString(i++, notice.getNotice_title());
		pstmt.setString(i++, notice.getNotice_content());
		pstmt.setString(i++, notice.getNotice_user_name());
		
		int updCnt = pstmt.executeUpdate();
		return updCnt;
		
	}
	
	public int deleteNotice(Connection conn, int notice_seq_no) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" delete from tb_notice where notice_seq_no = ? ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		pstmt.setInt(1, notice_seq_no);
		
		int updCnt = pstmt.executeUpdate();
		
		return updCnt;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
