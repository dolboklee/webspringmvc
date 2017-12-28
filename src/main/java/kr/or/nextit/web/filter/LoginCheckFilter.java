package kr.or.nextit.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter{

	List<String> excludeURI = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig config) throws ServletException {

		// 로그인 없이 들어가야하는 회피대상 : /login/loginForm.do, /board/boardList.do, /board/boardView.do ...
		excludeURI.add("/login/loginForm.do");
		excludeURI.add("/login/login.do");
		excludeURI.add("/login/logout.do");
		excludeURI.add("/board/boardList.do");
		excludeURI.add("/board/boardView.do");
		excludeURI.add("/member/memberList.do");
		excludeURI.add("/member/memberView.do");
		excludeURI.add("/member/memberForm.do");
		excludeURI.add("/member/memberInsert.do");
		excludeURI.add("/member/memberUpdate.do");
		excludeURI.add("/member/memberDelete.do");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		HttpSession session = httpRequest.getSession();
		String uri = httpRequest.getRequestURI(); // contextPath가 딸려 들어옴
		
		if(uri.indexOf(httpRequest.getContextPath()) == 0) {
			uri = uri.substring(httpRequest.getContextPath().length());
		}
		
		// 로그인 정보 체크
		if(!excludeURI.contains(uri) && session.getAttribute("login_user") == null) { // contains 포함되어 있는지 물어보는 메서드
			
			session.setAttribute("previewPage", uri); // 리다이렉트 전에 정확한 url정보를 알수 있다 ( 이전 페이지 (요청들어온)) >> 예를 들어 referer을 사용했을 시에는 boardList, filter를 사용 시 boardForm으로 로그인 후 이동
			
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login/loginForm.do");
		}else {
			// 정상 진행
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {

		
		
	}

}
