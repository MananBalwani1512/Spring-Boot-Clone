package com.springboot.clone.pojo;
import javax.servlet.*;
import javax.servlet.http.*;
public class RequestScope
{
	private HttpServletRequest request;
	public RequestScope(HttpServletRequest request)
	{
		this.request = request;
	}
	public void setAttribute(String name, Object object)
	{
		request.setAttribute(name,object);
	}
	public Object getAttribute(String name)
	{
		return request.getAttribute(name);
	}
}