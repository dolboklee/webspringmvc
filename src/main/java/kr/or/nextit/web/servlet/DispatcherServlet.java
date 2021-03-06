package kr.or.nextit.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.nextit.web.handler.UrlHandlerMapping;

public class DispatcherServlet extends HttpServlet{
	
	@Override
	public void init() throws ServletException {

		String contextConfigLocation = getInitParameter("contextConfigLocation");
		String configFilePath = getServletContext().getRealPath(contextConfigLocation); // getServletContext == jsp의 application
		
		try {
			UrlHandlerMapping.init(configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uri =  request.getRequestURI();
		
		if(uri.indexOf(request.getContextPath()) == 0) {
			uri = uri.substring(request.getContextPath().length());
		}
		
		String viewPage = "";
		
		System.out.println("요청 URI : " + uri);
		
		try {
			
			Controller controller = UrlHandlerMapping.getHandler(uri);
			
			if(controller != null) {
				viewPage = controller.process(request, response);
				
				if(viewPage != null) {
					
				if(viewPage.startsWith("redirect:")) {
					// "redirect:/member/memberList.do"
					viewPage =  viewPage.substring("redirect:".length());
					response.sendRedirect(request.getContextPath() + viewPage);
				}else {
//					RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/" + viewPage + ".jsp"); // view 페이지가 꼭 jsp이라는 보장이 없기 때문에 구분(표현)
					dispatcher.forward(request, response);
					
				}
				
				}
			}else {
				// 404 에러 출력
				response.setStatus(HttpServletResponse.SC_NOT_FOUND); 
			}
		
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		
	}

	
	
}
