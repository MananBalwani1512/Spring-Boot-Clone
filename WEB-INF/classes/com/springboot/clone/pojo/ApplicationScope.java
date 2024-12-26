package com.springboot.clone.pojo;
import javax.servlet.*;
import javax.servlet.http.*;
public class ApplicationScope
{
	private ServletContext servletContext;
	public ApplicationScope(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}
	public void setAttribute(String name, Object object)
	{
		servletContext.setAttribute(name,object);
	}
	public Object getAttribute(String name)
	{
		return servletContext.getAttribute(name);
	}
}