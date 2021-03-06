package kr.or.nextit.member.service;

import java.util.List;
import java.util.Map;

import kr.or.nextit.member.model.Member;

public interface MemberService {

	
	// 회원 목록 조회
	public List<Member> getMemberList(Map<String, Object> paramMap) throws Exception; 
	
	// 회원 정보 조회
	public Member getMember(String mem_id) throws Exception;
	
	// 회원 등록 >> 등록, 수정 삭제의 리턴 타입은 수정 개수(==executeUpdate) int.
	public int insertMember(Member member) throws Exception;
	
	// 회원 수정
	public int updatetMember(Member member) throws Exception;
	
	// 회원 삭제
	public int deleteMember(String mem_id) throws Exception;
	
	
}
