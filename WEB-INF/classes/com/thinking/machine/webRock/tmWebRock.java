package com.thinking.machine.webRock;
import javax.servlet.*;
import java.util.*;
import com.thinking.machine.webRock.pojo.*;
import com.thinking.machine.webRock.annotations.*;
import javax.servlet.http.*;
import java.lang.reflect.*;
import com.google.gson.*;
import java.io.*;
public class tmWebRock extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String classPath = request.getServletPath();
			String method = request.getPathInfo();
			Object obj = getServletContext().getAttribute(classPath);
			HashMap<String,Service>map = (HashMap)getServletContext().getAttribute(classPath+"map");
			List<Service>autoWire = (List<Service>)getServletContext().getAttribute(classPath+"autoWire");
			for(Service service : autoWire)
			{
				getAutoWire(service, obj, request);
				getInjectRequestParameter(service, obj, request);
			}
			List<Service>extract = (List<Service>)getServletContext().getAttribute(classPath+"extract");
			for(Service service : extract)
			{
				extractAttribute(service, obj, request);
			}
			Service ser = map.get(classPath+method);
			validate("GET",ser,response);
			processSecuredAccess(ser,request);
			Object[]ob = setParameter(ser, request);
			if(void.class.equals(ser.getMethod().getReturnType()))
			{
				if(ob == null)
					ser.getMethod().invoke(obj);
				else
				{
					ser.getMethod().invoke(obj,ob);
				}
			}
			else
			{
				Object res;
				if(ob == null)
					res = ser.getMethod().invoke(obj);
				else
				{
					res = ser.getMethod().invoke(obj,ob);
				}
				sendResponse(res, response);
			}
			forwardTo(method, classPath, ser, obj, request, response, map);
			
			
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
			Object obj = getServletContext().getAttribute(classPath);
			HashMap<String,Service>map = (HashMap)getServletContext().getAttribute(classPath+"map");
			List<Service>autoWire = (List<Service>)getServletContext().getAttribute(classPath+"autoWire");
			for(Service service : autoWire)
			{
				getAutoWire(service, obj, request);
				getInjectRequestParameter(service, obj, request);
			}
			List<Service>extract = (List<Service>)getServletContext().getAttribute(classPath+"extract");
			for(Service service : extract)
			{
				extractAttribute(service, obj, request);
			}
			Service ser = map.get(classPath+method);
			validate("POST",ser,response);
			processSecuredAccess(ser,request);
			Object[]ob = setParameter(ser, request);
			if(void.class.equals(ser.getMethod().getReturnType()))
			{
				if(ob == null)
					ser.getMethod().invoke(obj);
				else
				{
					ser.getMethod().invoke(obj,ob);
				}
			}
			else
			{
				Object res;
				if(ob == null)
					res = ser.getMethod().invoke(obj);
				else
				{
					res = ser.getMethod().invoke(obj,ob);
				}
				sendResponse(res, response);
			}
			forwardTo(method, classPath, ser, obj, request, response, map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void processSecuredAccess(Service service,HttpServletRequest request)
	{
		if(service.getIsSecuredAccess())
		{
			try
			{
				Class c = service.getCheckPost();
				Method m = service.getGaurd();
				Parameter parameters[] = m.getParameters();
				if(parameters.length == 0)
					m.invoke(c.newInstance());
				else
				{
					Object obArray[] = new Object[parameters.length];
					int index = 0;
					for(Parameter p : parameters)
					{
						if(p.isAnnotationPresent(AutoWire.class))
						{
							Class cls = p.getType();
							if(cls.equals(RequestScope.class))
							{
								RequestScope rs = new RequestScope(request);
								obArray[index++] = rs;
							}
							else if(cls.equals(SessionScope.class))
							{
								obArray[index++] = new SessionScope(request);
							}
							else
							{
								obArray[index++] = new ApplicationScope(getServletContext());
							}
						}
					}
					m.invoke(c.newInstance(),obArray);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void getAutoWire(Service service, Object obj, HttpServletRequest request)
	{
		try
		{
			if(service.getIsRequestScope())
			{
				service.getRequestScope().set(obj,new RequestScope(request));
			}
			if(service.getIsSessionScope())
				service.getSessionScope().set(obj,new SessionScope(request));
			if(service.getIsApplicationScope())
			{
				service.getApplicationScope().set(obj,new ApplicationScope(getServletContext()));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void getInjectRequestParameter(Service service, Object obj,HttpServletRequest request)
	{
		try
		{
			if(service.getIsInjectRequestParameter())
			{
				String name = service.getRequestName();
				Field fi = service.getInjectRequestParameter();
				String val = request.getParameter(name);
				if(fi.getType().equals(int.class))
					fi.set(obj,Integer.parseInt(val));
				else if(fi.getType().equals(Character.class))
					fi.set(obj,val.charAt(0));
				else if(fi.getType().equals(String.class))
					fi.set(obj,val);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void extractAttribute(Service service,Object obj, HttpServletRequest request)
	{
		try
		{
			String name = service.getName();
			Object attr = request.getAttribute(name);
			HttpSession session = request.getSession();
			if(attr != null)
			{
				if(attr.getClass().equals(service.getAutoWire().getType()))
					service.getAutoWire().set(obj,attr);
				else
					attr = null;
			}
			if(attr == null)
			{
				attr = session.getAttribute(name);
				if(attr != null)
				{
					if(attr.getClass().equals(service.getAutoWire().getType()))
						service.getAutoWire().set(obj,attr);
					else
						attr = null;	
				}
			}
			if(attr == null)
			{
				attr = getServletContext().getAttribute(name);
				if(attr != null)
				{
					if(attr.getClass().equals(service.getAutoWire().getType()))
						service.getAutoWire().set(obj,attr);
					else
						attr = null;	
				}
			}
			else
				service.getAutoWire().set(obj,null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void validate(String methodType, Service service, HttpServletResponse response)
	{
		try
		{
			if(service == null)
			{
				response.sendError(404,"Sorry We Cannot Find Resource"); 
				return;
			}
			if(service.getMethodType() != methodType && service.getMethodType() != "ANY")
			{
				response.sendError(500,"Wrong Method Call Try "+methodType+" Method");
				return;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void forwardTo(String url,String classPath,Service ser,Object obj, HttpServletRequest request,HttpServletResponse response,HashMap map)
	{
		try
		{
			String forward = ser.getForwardTo();
			if(forward == null)
				return;
			if(map.containsKey(classPath+forward))
			{
				Service s = (Service)map.get(classPath+forward);
				s.getMethod().invoke(obj);
				return;
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
	public Object[] setParameter(Service ser, HttpServletRequest request)
	{
		Object ob[] = null;
		List<Parameter>parameters = new ArrayList();
		try
		{
			parameters = ser.getRequestParameters();
			if(parameters.size()>0)
			{
				ob = new Object[parameters.size()];
				int index = 0;
				for(Parameter parameter : parameters)
				{
					if(parameter.isAnnotationPresent(RequestParameter.class))
					{
						RequestParameter rp = (RequestParameter)parameter.getAnnotation(RequestParameter.class);
						String name = rp.Name();
						if(name != "")
						{
							String par = request.getParameter(name);
							Class type = parameter.getType();
							if(type.equals(int.class))
							{
								ob[index++] = Integer.parseInt(par);
							}
							else if(type.equals(Character.class))
								ob[index++] = par.charAt(0);
							else if(type.equals(Double.class))
								ob[index++] = Double.parseDouble(par);
							else
								ob[index++] = par;
						}
					}
					else if(parameter.isAnnotationPresent(AutoWire.class))
					{
						AutoWire auto = (AutoWire)parameter.getAnnotation(AutoWire.class);
						Class type = parameter.getType();
						if(type.equals(RequestScope.class))
						{
							ob[index++] = new RequestScope(request);
						}
						else if(type.equals(SessionScope.class))
						{
							ob[index++] = new SessionScope(request);
						}
						else
						{
							ob[index++] = new ApplicationScope(getServletContext());
						}
					}
					else
					{
						StringBuffer sb = new StringBuffer();
						BufferedReader br = request.getReader();
						String d;
						while(true)
						{
							d = br.readLine();
							if(d == null)
							{
								break;
							}
							sb.append(d);
						}
						String rawData = sb.toString();
						Gson gson = new Gson();
						ob[index++] = gson.fromJson(rawData,parameter.getType());
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ob;
	}
	public void sendResponse(Object res, HttpServletResponse response)
	{
		PrintWriter out = null;
		try
		{
			out = response.getWriter();
			if(String.class.equals(res))
			{
				out.println(res);				
			}
			else
			{
				Gson gson = new Gson();
				String jsonData = gson.toJson(res);
				out.println(jsonData);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}