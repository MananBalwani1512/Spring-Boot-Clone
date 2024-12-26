package com.springboot.clone.pojo;
import java.lang.reflect.*;
import java.util.*;
public class Service implements java.io.Serializable
{
	private Class serviceClass;
	private Method serviceMethod;
	private String url;
	private String forward;
	private boolean isGetAllowed = true;
	private boolean isPostAllowed = true;
	private boolean runOnStartup;
	private int priority;
	private boolean injectRequestScope;
	private boolean injectSessionScope;
	private boolean injectApplicationScope;
	private boolean injectApplicationDirectory;
	private List<AutowiredField> autowiredFields;
	private Parameter[] parameters;
	private boolean isSecuredAccess;
	private String securedAccessCheckPost;
	private String securedAccessGuard;
	public void setServiceClass(Class serviceClass)
	{
		this.serviceClass = serviceClass;
	}
	public Class getServiceClass()
	{
		return this.serviceClass;
	}
	public void setServiceMethod(Method serviceMethod)
	{
		this.serviceMethod = serviceMethod;
	}
	public Method getServiceMethod()
	{
		return this.serviceMethod;
	}
	public void setURL(String url)
	{
		this.url = url;
	}
	public String getURL()
	{
		return this.url;
	}
	public void setForward(String forward)
	{
		this.forward = forward;
	}
	public String getForward()
	{
		return this.forward;
	}
	public void setIsGetAllowed(boolean isGetAllowed)
	{
		this.isGetAllowed = isGetAllowed;
	}
	public boolean getIsGetAllowed()
	{
		return this.isGetAllowed;
	}
	public void setIsPostAllowed(boolean isPostAllowed)
	{
		this.isPostAllowed = isPostAllowed;
	}
	public boolean getIsPostAllowed()
	{
		return this.isPostAllowed;
	}
	public void setRunOnStartup(boolean runOnStartup)
	{
		this.runOnStartup = runOnStartup;
	}
	public boolean getRunOnStartup()
	{
		return this.runOnStartup;
	}
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	public int getPriority()
	{
		return this.priority;
	}
	public void setInjectRequestScope(boolean injectRequestScope)
	{
		this.injectRequestScope = injectRequestScope;
	}
	public boolean getInjectRequestScope()
	{
		return this.injectRequestScope;
	}
	public void setInjectSessionScope(boolean injectSessionScope)
	{
		this.injectSessionScope = injectSessionScope;
	}
	public boolean getInjectSessionScope()
	{
		return this.injectSessionScope;
	}
	public void setInjectApplicationScope(boolean injectApplicationScope)
	{
		this.injectApplicationScope = injectApplicationScope;
	}
	public boolean getInjectApplicationScope()
	{
		return this.injectApplicationScope;
	}
	public void setInjectApplicationDirectory(boolean injectApplicationDirectory)
	{
		this.injectApplicationDirectory = injectApplicationDirectory;
	}
	public boolean getInjectApplicationDirectory()
	{
		return this.injectApplicationDirectory;
	}
	public void setAutowiredFields(List<AutowiredField> autowiredFields)
	{
		this.autowiredFields = autowiredFields;
	}
	public List<AutowiredField> getAutowiredFields()
	{
		return this.autowiredFields;
	}
	public void setParameters(Parameter[] parameters)
	{
		this.parameters = parameters;
	}
	public Parameter[] getParameters()
	{
		return this.parameters;
	}
	public void setIsSecuredAccess(boolean isSecuredAccess)
	{
		this.isSecuredAccess = isSecuredAccess;
	}
	public boolean getIsSecuredAccess()
	{
		return this.isSecuredAccess;
	}
	public void setSecuredAccessCheckPost(String securedAccessCheckPost)
	{
		this.securedAccessCheckPost = securedAccessCheckPost;
	}
	public String getSecuredAccessCheckPost()
	{
		return this.securedAccessCheckPost;
	}
	public void setSecuredAccessGuard(String securedAccessGuard)
	{
		this.securedAccessGuard = securedAccessGuard;
	}
	public String getSecuredAccessGuard()
	{
		return this.securedAccessGuard;
	}
	public boolean equals(Object obj)
	{
		if((obj instanceof Service) == false)
		{
			return false;
		}
		Service service = (Service)obj;
		if(service.getURL().equals(this.url) == false)
			return false;
		if(service.getServiceClass().equals(this.serviceClass) == false)
		{
			return false;
		}
		if(service.getServiceMethod().equals(this.serviceMethod) == false)
		{
			return false;
		}
		return true;
	}
	public int hashCode()
	{
		int code = this.url.length();
		code = code+serviceClass.hashCode()+serviceMethod.hashCode();
		return code;
	}
}