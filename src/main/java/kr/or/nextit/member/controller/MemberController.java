package kr.or.nextit.member.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.or.nextit.member.model.Member;
import kr.or.nextit.member.service.MemberService;


@Controller
@RequestMapping("/member")// 경로를 나눠서 가능
//@SessionAttributes(value="member") // >> 잡코리아, 사람인과 같은 사이트에서 다음 단계로 넘어갔을 때 첫 단계의 정보를 화면에 보여줄 때 사용 request는 다음단계로 넘어갔을 때 사라지기 때문에 session에 넣어준다(임시로) >> 그렇기 때문에 해당 클래스 내의 메서드에서만 유효하다 >> 처리가 끝나면 없애주어야 하기 때문에 insert가 되면 없애주는 작업을 해줘야한다.

public class MemberController {
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	// arraylist나 map으로 @ModelAttribute를 통해 model에 세팅하여 화면에 뿌릴 메소드를 호출 가능하게 함.(화면에서 사용할 수 있다. select)
	@ModelAttribute("searchTypeMap")
	public Map<String, String> getSearchType(){
		Map<String, String> searchTypeMap = new LinkedHashMap(); // 들어간 순서대로 LinkedHashMap
		searchTypeMap.put("id", "아이디");
		searchTypeMap.put("name", "이름");
		return searchTypeMap;
	}
	

	
	
	@RequestMapping("/memberList")
	public String memberList(HttpServletRequest request,
//			@RequestParam(value="searchType", required=true, defaultValue="")String search, 반드시 파라미터가 들어와야하는 경우 required 속성을 true를 준다. 
			String searchType,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage, // String을 int로 자동 형변환
			@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			Model model,
			String searchWord) throws Exception{

		request.setCharacterEncoding("utf-8");
		
//		String searchType = request.getParameter("searchType");
//		String searchWord = request.getParameter("searchWord");
		
		Map<String, Object> paramMap = new HashMap<>();
		
		if(StringUtils.isNoneEmpty(searchWord)) {
			paramMap.put("searchType", searchType);
			paramMap.put("searchWord", searchWord);
		};
		
		List<Member> memberList = memberService.getMemberList(paramMap);
		
		model.addAttribute("memberList", memberList);
		
//		request.setAttribute("memberList", memberList);
		
		//리턴값이 String이면 뷰페이지로 생각한다.
		
		return "member/memberList";
	}
	
	
	
	{
	}
	
	
	
	
	
	
	@RequestMapping("/memberView")
	public String memberView(@RequestParam(value="mem_id", required=true) String mem_id
			, Model model
			) throws Exception {
		
		Member member = memberService.getMember(mem_id);
		
		model.addAttribute("member", member);
		
		return "member/memberView";
	}
	

	
	
	
	@RequestMapping("/memberForm")
	public String memberForm(
			@RequestParam(value = "mem_id", required = false) String mem_id,
			HttpSession session,
			Model model
			) throws Exception {
		
		Member member = new Member(); 
		if(StringUtils.isNotBlank(mem_id)) {
		member = memberService.getMember(mem_id);
		}
		
		if(session.getAttribute("member") != null) {
			member = (Member)session.getAttribute("member");
			System.out.println("memberForm : " + member.getMem_id());
		}
		model.addAttribute("member", member);
		
		return "member/memberForm";
	}
	
	
	
	
	
	
	@RequestMapping(value = "/memberInsert", method=RequestMethod.POST)
	public String memberInsert(@ModelAttribute("member") Member member, // 자동으로 member의 데이터를 model에 넣어놓는다
			Model model,
			SessionStatus sessionStatus
			) {
		
		String viewPage = "common/message";
		
		
		boolean isError = false;
		String message ="정상적으로 회원가입 되었습니다.";
		
		// 유효성 검증.(whitespace 공백도 검증해줘야 한다.)
		if(member.getMem_id() == null || member.getMem_id().isEmpty()) {
			isError = true;
			message = "아이디를 입력하세요.";
		}
		if(StringUtils.isAllBlank(member.getMem_name())) {
			isError = true;
			message = "이름을 입력하세요.";
			
			model.addAttribute("isError", isError);
			model.addAttribute("message", message);
			// 회원가입시 오류시 history 백이 아니고 작성한 데이터를 바인딩 해주기 위해 
//			model.addAttribute("member", member); >> @ModelAttribute
			return "member/memberForm";
			// redirect해버리면 데이터가 사라짐
//			return "redirect:/member/memberForm?type=I"; // >> model데이터가 request에 들어가있기 때문에. >> redirect시에도 해당 데이터를 유지하기 위해선 session에 속성 등록
		}
		
		try{
			if(!isError) {
				
			int updCnt = memberService.insertMember(member);
			if(updCnt == 0){
				// 에러
				message = "회원등록에 실패하였습니다.";
				isError = true;
			}
			// 정상 완료시 세션 애트리뷰트 제거.
//			sessionStatus.setComplete();
			}
		}catch(Exception e){
			// 에러
			e.printStackTrace();
			message = "회원등록에 실패하였습니다.";
			isError = true;
		} 
		
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		model.addAttribute("locationURL", "/member/memberList.do");
		
		return viewPage;
		
	}
	
	// 단축키 ctrl + shift + r == openresource
	
	
	
	
	@RequestMapping("/memberUpdate")
	public String memberUpdate(
			Member member,
			Model model
			) throws Exception{
		
		boolean isError = false;
		String message = "정상 수정되었습니다.";
		
		
		try {
		int updCnt = memberService.updatetMember(member);
		if(updCnt == 0) {
			isError = true;
			message = "회원정보 수정 실패하였습니다.";
		}
		
		
		}catch(Exception e) {
			isError = true;
			message = "회원정보 수정 실패하였습니다.";
			throw e;
		}
		
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
		model.addAttribute("locationURL", "/member/memberView?mem_id=" + member.getMem_id());
		
		return "common/message";
	}
	
	
	
	@RequestMapping("/memberDelete")
	public String memberDelete(
			@RequestParam(value="mem_id", required=true) String mem_id,
			Model model
			)throws Exception {
		boolean isError = false;
		String message = "정상 삭제되었습니다.";
		try {
			int updCnt = memberService.deleteMember(mem_id);
			if(updCnt == 0) {
				isError = true;
				message = "삭제 실패하였습니다.";
			}
		} catch (Exception e) {
			isError = true;
			message = "삭제 실패하였습니다.";
			throw e;
		
		}
		
		model.addAttribute("message", message);
		model.addAttribute("isError", isError);
		model.addAttribute("locationURL", "/member/memberList");
		
		return "common/message";
	}
	
	
	
	
	@RequestMapping("/memberExists")
	@ResponseBody // 이 어노테이션이 들어가게 되면 return의 값이 뷰의 이름이 아니고 응답데이터의 바디영역에 넣어주라는 뜻.(ajax 사용시 주의사항)
	// json으로 데이터를 주고 받기 때문에 (key/value의 쌍으로) >> map으로 표현하면 좋다. map 또는 빈 >> 객체를 json으로 바꿔주기 위해 Jackson2 라이브러리 추가
	public Map<String, Object> memberExists(@RequestParam(value="mem_id", required=true) String mem_id
			) throws Exception {
		
		
		Member member = memberService.getMember(mem_id);
		// 리턴 타입을 map으로 사용하면 다음과 같이 여러가지의 리턴 값을 갖는것이 가능하기 때문에 편리하다
//		map.put("msg", "성공");
//		map.put("member", "member");
		
		Map<String, Object> map = new HashMap<>();
		
		
		if(member != null) {
			map.put("result", "true");
		}else {
			map.put("result", "false");
		}
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
