/*package kr.or.nextit.board.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.nextit.board.model.Board;
import kr.or.nextit.board.service.BoardService;
import kr.or.nextit.board.service.impl.BoardServiceImpl;
import kr.or.nextit.member.model.Member;
import kr.or.nextit.web.servlet.Controller;

public class BoardDeleteController implements Controller {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		request.setCharacterEncoding("utf-8");
		
		// 로그인 여부 확인(url을 치고 들어올 수 있기 때문에 체크)
		HttpSession session = request.getSession();
		
		Member member = (Member)session.getAttribute("login_user");
		
//		if(member == null) {
//			return "redirect:/login/loginForm.do";
//		}
		
		Board board = new Board(); // 시퀀스 번호 하나 받아오기 위해서 객체 생성을 하는건 비효율
		int boSeqNo = Integer.parseInt(request.getParameter("bo_seq_no"));
		
		BeanUtils.populate(board, request.getParameterMap());
		
//		board.setUpd_user("san");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bo_seq_no", boSeqNo); // 현 방법은 비효율
		paramMap.put("upd_user", member.getMem_name());
		
		boolean isError = false;
		String message = "정상 삭제되었습니다.";
		String locationURL = "/board/boardList.do";
		String viewPage="common/message";

		try {
		BoardService boardService = BoardServiceImpl.getInstance();
		
		int updCnt = boardService.deleteBoard(paramMap);
		
		if(updCnt == 0) {
			isError = true;
			message = "삭제 실패하였습니다.";
		}
		}catch(Exception e) {
			isError = true;
			message = "삭제 실패하였습니다.";
		}
		
		request.setAttribute("isError", isError);
		request.setAttribute("message", message);
		request.setAttribute("locationURL", locationURL);
		
		return viewPage;
	}
}
*/