package com.s7soft.gae.news.filter;

import java.io.IOException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DefaultFilter implements Filter {

	public DefaultFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		URL url = new URL(((HttpServletRequest)req).getRequestURL().toString());
	    String host  = url.getHost();

	    if(!host.contains("localhost") && !host.contains("news.s7soft.com")){
	    	((HttpServletResponse)res).sendRedirect("http://news.s7soft.com"+url.getFile());
	    	return;
	    }


		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
