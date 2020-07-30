package com.in28minutes.microservices;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter{
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	// execute filter for every request
	@Override
	public boolean shouldFilter() {
		return true;
	}

	// real logic of intercept
	@Override
	public Object run() throws ZuulException {
		//how can i get the current request
		HttpServletRequest request=RequestContext.getCurrentContext().getRequest();
		logger.info("request -> {} request uri -> {}",
				request,request.getRequestURI());
		return null;
	}

	// all the request intercept before execution
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
