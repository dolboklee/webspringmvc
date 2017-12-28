package kr.or.nextit.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharacterEncodingFilter implements Filter{

	private String encoding = "";
	
	@Override
	public void init(FilterConfig config) throws ServletException {

		encoding = config.getInitParameter("encoding");
		if(encoding == null) {
			encoding = "utf-8";
		}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 필터 작업.
		request.setCharacterEncoding(encoding);
		
		chain.doFilter(request, response); // 다음 단계로 전달.
	}

	@Override
	public void destroy() {
		// 필터에서 사용한 자원해제.
		
	}

}
