package kr.or.nextit.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.nextit.web.servlet.Controller;

public class LogoutController implements Controller{ // 구현해야 dispatcher가 인식

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false); // true : 디폴트값, 없으면 생성 false는 null 리턴
		if(session != null) { // jsp를 들렸다 오지 않는 데이터는 session이 생성되지 않는다.
			session.invalidate();
			
		}
		
		return "redirect:/login/loginForm.do";
	}

}
