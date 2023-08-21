package com.thinking.machine.webRock.pojo;
import java.lang.reflect.*;
public class Service
{
	private String url;
	private Method method;
	private String methodType;
	private String forwardTo;
	private boolean onStartup;
	private int priority;
	private boolean isRequestScope;
	private Field requestScope;
	private boolean isSessionScope;
	private Field sessionScope;
	private boolean isApplicationScope;
	private Field applicationScope;
	public void setURL(String url)
	{
		this.url = url;
	}
	public String getURL()
	{
		return this.url;
	}
	public void setMethod(Method method)
	{
		this.method = method;
	}
	public Method getMethod()
	{
		return this.method;
	}
	public void setMethodType(String methodType)
	{
		this.methodType = methodType;
	}
	public String getMethodType()
	{
		return this.methodType;
	}
	public void setForwardTo(String forwardTo)
	{
		this.forwardTo = forwardTo;
	}
	public String getForwardTo()
	{
		return this.forwardTo;
	}
	public void setOnStartup(boolean onStartup)
	{
		this.onStartup = onStartup;
	}
	public boolean getOnStartup()
	{
		return this.onStartup;
	}
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	public int getPriority()
	{
		return this.priority;
	}
	public void setIsSessionScope(boolean isSessionScope)
	{
		this.isSessionScope = isSessionScope;
	}
	public boolean getIsSessionScope()
	{
		return this.isSessionScope;
	}
	public void setIsRequestScope(boolean isRequestScope)
	{
		this.isRequestScope = isRequestScope;
	}
	public boolean getIsRequestScope()
	{
		return this.isRequestScope;
	}
	public void setIsApplicationScope(boolean isApplicationScope)
	{
		this.isApplicationScope = isApplicationScope;
	}
	public boolean getIsApplicationScope()
	{
		return this.isApplicationScope;
	}
	public void setRequestScope(Field requestScope)
	{
		this.requestScope = requestScope;
	}
	public Field getRequestScope()
	{
		return this.requestScope;
	}
	public void setSessionScope(Field sessionScope)
	{
		this.sessionScope = sessionScope;
	}
	public Field getSessionScope()
	{
		return this.sessionScope;
	}
	public void setApplicationScope(Field applicationScope)
	{
		this.applicationScope = applicationScope;
	}
	public Field getApplicationScope()
	{
		return this.applicationScope;
	}
}