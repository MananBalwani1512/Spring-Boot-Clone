package com.thinking.machine.webRock;
import javax.servlet.*;
import java.util.*;
import com.thinking.machine.webRock.pojo.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
public class tmWebRock extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String classPath = request.getServletPath();
			String method = request.getPathInfo();
			Object obj = getServletContext().getAttribute("object");
			HashMap<String,Service>map = (HashMap)getServletContext().getAttribute("map");
			System.out.println(map+classPath);
			Service serviceClass = map.get(classPath);
			System.out.println(serviceClass.getURL());
			if(serviceClass.getIsRequestScope())
			{
				Field requestScope = serviceClass.getRequestScope();
				requestScope.set(obj,new RequestScope(request));
			}
			if(serviceClass.getIsSessionScope())
			{
				Field sessionScope = serviceClass.getSessionScope();
				sessionScope.set(obj,new SessionScope(request));
			}
			if(serviceClass.getIsApplicationScope())
			{
				
				Field applicationScope = serviceClass.getApplicationScope();
				System.out.println(applicationScope);
				applicationScope.set(obj,new ApplicationScope(getServletContext()));
			}
			Service ser = map.get(classPath+method);
			if(ser == null)
			{
				response.sendError(404,"Sorry We Cannot Find URL : "+classPath+method); 
				return;
			}
			if(ser.getMethodType() != "GET" && ser.getMethodType() != "ANY")
			{
				response.sendError(500,"Wrong Method Call Try POST Method");
				return;
			}
			ser.getMethod().invoke(obj);
			String forward = ser.getForwardTo();
			if(forward == null)
				return;
			if(map.containsKey(classPath+forward))
			{
				Service s = map.get(classPath+forward);
				s.getMethod().invoke(obj);
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher(forward);
				rd.forward(request,response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String classPath = request.getServletPath();
			String method = request.getPathInfo();
			Object obj = getServletContext().getAttribute("object");
			HashMap<String,Service>map = (HashMap)getServletContext().getAttribute("map");
			Service ser = map.get(classPath+method);
			if(ser == null)
			{
				response.sendError(404,"Sorry We Cannot Find URL : "+classPath+method); 
				return;
			}
			if(ser.getMethodType() != "POST" && ser.getMethodType() != "ANY")
			{
				response.sendError(500,"Wrong Method Call Try GET Method");
				return;
			}
			String forward = ser.getForwardTo();
			ser.getMethod().invoke(obj);
			if(forward == null)
				return;
			if(map.containsKey(classPath+forward))
			{
				Service s = map.get(classPath+forward);
				s.getMethod().invoke(obj);
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher(forward);
				rd.forward(request,response);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}