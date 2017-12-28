/*package kr.or.nextit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.or.nextit.member.model.Member;

public class MemberDao {
	// dao는 특별한 경우를 제외하고 나서는 하나의 쿼리문만 실행
	
	private static final String NAMESPACE = "kr.or.nextit.member.mapper.MemberMapper.";
	
	// 회원목록 조회
	public List<Member> selectMemberList(SqlSession session, Map<String, Object> paramMap) throws Exception{
		
		List<Member> memberList = session.selectList(NAMESPACE + "selectMemberList", paramMap);
		
		return memberList;
	};
	
	// 회원 정보 조회
	public Member selectMember(SqlSession session, String mem_id) throws Exception {
		
		return session.selectOne(NAMESPACE + "selectMember", mem_id);
		
	};
	
	// 회원 등록
	public int insertMember(SqlSession session, Member member) throws Exception {
		
		return session.insert(NAMESPACE + "insertMember", member);
		
	}
	// 회원 수정
	public int updateMember(SqlSession session, Member member) throws Exception {
		return session.update(NAMESPACE + "updateMember", member);
		
	}
	// 회원 삭제
	public int deleteMember(SqlSession session, String mem_id) throws Exception {
		
		return session.delete(NAMESPACE + "deleteMember", mem_id);
	}	
	
	

}
*/