package com.thinking.machine.webRock.pojo;
import java.lang.reflect.*;
import java.util.*;
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
	private boolean isAutoWire;
	private String name;
	private Field autoWire;
	private boolean isRequestParameter;
	private List<Parameter> requestParameters;
	private boolean isInjectRequestParameter;
	private Field injectRequestParameter;
	private String requestName;
	private boolean isSecuredAccess;
	private Class checkPost;
	private Method gaurd;
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
	public void setIsAutoWire(boolean isAutoWire)
	{
		this.isAutoWire = isAutoWire;
	}
	public boolean getIsAutoWire()
	{
		return this.isAutoWire;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setAutoWire(Field autoWire)
	{
		this.autoWire = autoWire;
	}
	public Field getAutoWire()
	{
		return this.autoWire;
	}
	public void setIsRequestParameter(boolean isRequestParameter)
	{
		this.isRequestParameter = isRequestParameter;
	}
	public boolean getIsRequestParameter()
	{
		return this.isRequestParameter;
	}
	public void setRequestParameters(List<Parameter>requestParameters)
	{
		this.requestParameters = requestParameters;
	}
	public List<Parameter> getRequestParameters()
	{
		return this.requestParameters;
	}
	public void setIsInjectRequestParameter(boolean isInjectRequestParameter)
	{
		this.isInjectRequestParameter = isInjectRequestParameter;
	}
	public boolean getIsInjectRequestParameter()
	{
		return this.isInjectRequestParameter;
	}
	public void setInjectRequestParameter(Field injectRequestParameter)
	{
		this.injectRequestParameter = injectRequestParameter;
	}
	public Field getInjectRequestParameter()
	{
		return this.injectRequestParameter;
	}
	public void setRequestName(String requestName)
	{
		this.requestName = requestName;
	}
	public String getRequestName()
	{
		return this.requestName;
	}
	public void setIsSecuredAccess(boolean isSecuredAccess)
	{
		this.isSecuredAccess = isSecuredAccess;
	}
	public boolean getIsSecuredAccess()
	{
		return this.isSecuredAccess;
	}
	public void setCheckPost(Class checkPost)
	{
		this.checkPost = checkPost;
	}
	public Class getCheckPost()
	{
		return this.checkPost;
	}
	public void setGaurd(Method gaurd)
	{
		this.gaurd = gaurd;
	}
	public Method getGaurd()
	{
		return this.gaurd;
	}
}