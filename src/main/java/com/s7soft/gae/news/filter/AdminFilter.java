package com.s7soft.gae.news.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.s7soft.gae.news.admin.AdminUtil;

public class AdminFilter implements Filter {

	public AdminFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("start");
		if (AdminUtil.isAdminUser()) {
			System.out.println("is Admin Ok");
		}else{
			System.out.println("is Admin NG");
			((HttpServletResponse)res).sendRedirect("/");
			return;
		}
		chain.doFilter(req, res);
		System.out.println("end");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
