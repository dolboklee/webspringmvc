package kr.or.nextit.member.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import kr.or.nextit.member.model.Member;

@Mapper
public interface MemberMapper {

	public List<Member> selectMemberList(Map<String, Object> paramMap) throws Exception;
	
	public Member selectMember(String mem_id) throws Exception;
	
	public int insertMember(Member member) throws Exception;
	
	public int updateMember(Member member) throws Exception;
	
	public int deleteMember(String mem_id) throws Exception;
	
}
