package com.solo.LoLStrategy.user.security;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestValidationFilter implements Filter{
	
	
	

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
		
		HttpServletRequest HttpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		/*
		 * String requestId = HttpRequest.getHeader("Request-Id");
		 * 
		 * if(requestId == null || requestId.isBlank()) {
		 * httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST); return; }
		 */
		
		
		// 요청을 필ㅊ터 체인의 다음 필터에 전달
		filterchain.doFilter(request, response);
	}

}
