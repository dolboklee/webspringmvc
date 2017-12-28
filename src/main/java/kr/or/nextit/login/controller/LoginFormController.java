package kr.or.nextit.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.nextit.web.servlet.Controller;

public class LoginFormController implements Controller{ // 구현해야 dispatcher가 인식

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// referer => 클라이언트가 준 정보이기 때문에 100% 믿기 힘들다.
		/*String referer = request.getHeader("referer");
		System.out.println("referer : " + referer);
		
		HttpSession session = request.getSession();
		session.setAttribute("previewPage", referer);*/
		
		
		
		return "login/loginForm";
	}

}
