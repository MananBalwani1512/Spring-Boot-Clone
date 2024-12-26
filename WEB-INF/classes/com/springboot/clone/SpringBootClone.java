package com.springboot.clone;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import com.springboot.clone.model.*;
import com.springboot.clone.pojo.*;
import com.springboot.clone.annotations.*;
import com.google.gson.*;
public class SpringBootClone extends HttpServlet
{
	public void injectScopes(Service service, Object object, HttpServletRequest request, HttpSession session, ServletContext servletContext)
	{
		Class serviceClass = service.getServiceClass();
		Field fields[] = serviceClass.getDeclaredFields();
		try
		{
			if(service.getInjectRequestScope() == true)
			{
				for(Field field : fields)
				{
					if(RequestScope.class.equals(field.getType()))
					{
						field.setAccessible(true);
						field.set(object,new RequestScope(request));
						break;
					}
				}
			}
			if(service.getInjectSessionScope() == true)
			{
				for(Field field : fields)
				{
					if(SessionScope.class.equals(field.getType()))
					{
						field.setAccessible(true);
						field.set(object,new SessionScope(session));
						break;
					}
				}
			}
			if(service.getInjectApplicationScope() == true)
			{
				for(Field field : fields)
				{
					if(ApplicationScope.class.equals(field.getType()))
					{
						field.setAccessible(true);
						field.set(object,new ApplicationScope(servletContext));
						break;
					}
				}
			}
			if(service.getInjectApplicationDirectory() == true)
			{
				for(Field field : fields)
				{
					if(ApplicationDirectory.class.equals(field.getType()))
					{
						field.setAccessible(true);
						field.set(object,new ApplicationDirectory(new File(servletContext.getRealPath(""))));
						break;
					}
				}
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	public void setAutowiredParameters(Service service, Object object, HttpServletRequest request, HttpSession session, ServletContext servletContext)
	{
		List<AutowiredField> autowiredFields = service.getAutowiredFields();
		try
		{
			for(AutowiredField autowiredField : autowiredFields)
			{
				String fieldName = autowiredField.getFieldName();
				Field field = autowiredField.getField();
				Class fieldClass = field.getType();
				boolean foundField = false;
				if(request.getAttribute(fieldName) != null)
				{
					Object fieldValue = request.getAttribute(fieldName);
					if(fieldClass.isInstance(fieldValue))
					{
						foundField = true;
						field.setAccessible(true);
						field.set(object,fieldValue);
					}
				}
				if(foundField == false && session.getAttribute(fieldName) != null)
				{
					Object fieldValue = session.getAttribute(fieldName);
					if(fieldClass.isInstance(fieldValue))
					{
						foundField = true;
						field.setAccessible(true);
						field.set(object,fieldValue);
					}
				}
				if(foundField == false && servletContext.getAttribute(fieldName) != null)
				{
					Object fieldValue = servletContext.getAttribute(fieldName);
					if(fieldClass.isInstance(fieldValue))
					{
						foundField = true;
						field.setAccessible(true);
						field.set(object,fieldValue);
					}
				}
				if(foundField == false)
				{
					field.setAccessible(true);
					field.set(object,null);
				}
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	public Object[] setMethodParameters(Service service, Object object, HttpServletRequest request, HttpSession session, ServletContext servletContext)
	{
		Parameter[] parameters = service.getParameters();
		Object[] arguments = new Object[parameters.length];	
		String contentType = request.getContentType();
		try
		{
			int index = 0;
			if(contentType != null && contentType.startsWith("application/json"))
			{
				boolean JSONSetted = false;
				for(Parameter parameter : parameters)
				{
					
					if(parameter.isAnnotationPresent(RequestParameter.class))
					{
						if(JSONSetted == true)
						{
							System.out.println("Exception : Multiple JSON cannot be parsed");
						}
						JSONSetted = true;
						StringBuffer stringBuffer = new StringBuffer();
						BufferedReader bufferedReader = request.getReader();
						while(true)
						{
							String d = bufferedReader.readLine();
							if(d == null)
								break;
							stringBuffer.append(d);
						}
						String rawData = stringBuffer.toString();
						Class parameterType = parameter.getType();
						Gson gson = new Gson();
						arguments[index++] = gson.fromJson(rawData,parameterType);
					}
					if(parameter.isAnnotationPresent(InjectRequestScope.class) && parameter.getType().equals(RequestScope.class))
					{
						arguments[index++] = new RequestScope(request);
					}
					if(parameter.isAnnotationPresent(InjectSessionScope.class) && parameter.getType().equals(SessionScope.class))
					{
						arguments[index++] = new SessionScope(session);
					}
					if(parameter.isAnnotationPresent(InjectApplicationScope.class) && parameter.getType().equals(ApplicationScope.class))
					{
						arguments[index++] = new ApplicationScope(servletContext);
					}
					if(parameter.isAnnotationPresent(InjectApplicationDirectory.class) && parameter.getType().equals(ApplicationDirectory.class))
					{
						arguments[index++] = new ApplicationDirectory(new File(servletContext.getRealPath("")));
					}
				}
			}
			else
			{
				for(Parameter parameter : parameters)
				{
					if(parameter.isAnnotationPresent(RequestParameter.class))
					{
						RequestParameter requestParameter = (RequestParameter)parameter.getAnnotation(RequestParameter.class);
						String parameterValue = request.getParameter(requestParameter.name());
						if(parameter.getType().equals(int.class))
						{
							arguments[index++] = Integer.parseInt(parameterValue);
						}
						else if(parameter.getType().equals(short.class))
						{
							arguments[index++] = Short.parseShort(parameterValue);
						}
						else if(parameter.getType().equals(byte.class))
						{
							arguments[index++] = Byte.parseByte(parameterValue);
						}
						else if(parameter.getType().equals(long.class))
						{
							arguments[index++] = Long.parseLong(parameterValue);
						}
						else if(parameter.getType().equals(double.class))
						{
							arguments[index++] = Double.parseDouble(parameterValue);
						}
						else if(parameter.getType().equals(boolean.class))
						{
							arguments[index++] = Boolean.parseBoolean(parameterValue);
						}
						else if(parameter.getType().equals(float.class))
						{
							arguments[index++] = Float.parseFloat(parameterValue);
						}
						else if(parameter.getType().equals(char.class))
						{
							arguments[index++] = parameterValue.charAt(0);
						}
						else
						{
							arguments[index++] = parameterValue;
						}
					}
					if(parameter.isAnnotationPresent(InjectRequestScope.class) && parameter.getType().equals(RequestScope.class))
					{
						arguments[index++] = new RequestScope(request);
					}
					if(parameter.isAnnotationPresent(InjectSessionScope.class) && parameter.getType().equals(SessionScope.class))
					{
						arguments[index++] = new SessionScope(session);
					}
					if(parameter.isAnnotationPresent(InjectApplicationScope.class) && parameter.getType().equals(ApplicationScope.class))
					{
						arguments[index++] = new ApplicationScope(servletContext);
					}
					if(parameter.isAnnotationPresent(InjectApplicationDirectory.class) && parameter.getType().equals(ApplicationDirectory.class))
					{
						arguments[index++] = new ApplicationDirectory(new File(servletContext.getRealPath("")));
					}
				}
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return arguments;
	}
	public void checkSecuredAccess(Service service, HttpServletRequest request, HttpSession session, ServletContext servletContext)
	{
		try
		{
			if(service.getIsSecuredAccess() == true)
			{
				Class cls = Class.forName(service.getSecuredAccessCheckPost());
				Method method = cls.getDeclaredMethod(service.getSecuredAccessGuard());
				Object object = cls.newInstance();
				Parameter[] parameters = method.getParameters();
				Object arguments[] = new Object[parameters.length];
				int index = 0;
				Field fields[] = cls.getDeclaredFields();
				//Scan for Inject Scopes in the Parameters
				for(Parameter parameter : parameters)
				{
					if(parameter.isAnnotationPresent(InjectRequestScope.class) && parameter.getType().equals(RequestScope.class))
					{
						arguments[index++] = new RequestScope(request);
					}
					if(parameter.isAnnotationPresent(InjectSessionScope.class) && parameter.getType().equals(SessionScope.class))
					{
						arguments[index++] = new SessionScope(session);
					}
					if(parameter.isAnnotationPresent(InjectApplicationScope.class) && parameter.getType().equals(ApplicationScope.class))
					{
						arguments[index++] = new ApplicationScope(servletContext);
					}
					if(parameter.isAnnotationPresent(InjectApplicationDirectory.class) && parameter.getType().equals(ApplicationDirectory.class))
					{
						arguments[index++] = new ApplicationDirectory(new File(servletContext.getRealPath("")));
					}
				}
				//Injecting scopes in fields of the class
				if(cls.isAnnotationPresent(InjectRequestScope.class) == true)
				{
					for(Field field : fields)
					{
						if(RequestScope.class.equals(field.getType()))
						{
							field.setAccessible(true);
							field.set(object,new RequestScope(request));
							break;
						}
					}
				}
				if(cls.isAnnotationPresent(InjectSessionScope.class) == true)
				{
					for(Field field : fields)
					{
						if(SessionScope.class.equals(field.getType()))
						{
							field.setAccessible(true);
							field.set(object,new SessionScope(session));
							break;
						}
					}
				}
				if(cls.isAnnotationPresent(InjectApplicationScope.class) == true)
				{
					for(Field field : fields)
					{
						if(ApplicationScope.class.equals(field.getType()))
						{
							field.setAccessible(true);
							field.set(object,new ApplicationScope(servletContext));
							break;
						}
					}
				}
				if(cls.isAnnotationPresent(InjectApplicationDirectory.class) == true)
				{
					for(Field field : fields)
					{
						if(ApplicationDirectory.class.equals(field.getType()))
						{
							field.setAccessible(true);
							field.set(object,new ApplicationDirectory(new File(servletContext.getRealPath(""))));
							break;
						}
					}
				}
				//Setting Autowired Fields
				for(Field field : fields)
				{
					if(field.isAnnotationPresent(Autowired.class))
					{
						Autowired autowired = (Autowired) field.getAnnotation(Autowired.class);
						String fieldName = autowired.name();
						Class fieldClass = field.getType();
						boolean foundField = false;
						if(request.getAttribute(fieldName) != null)
						{
							Object fieldValue = request.getAttribute(fieldName);
							if(fieldClass.isInstance(fieldValue))
							{
								foundField = true;
								field.setAccessible(true);
								field.set(object,fieldValue);
							}
						}
						if(foundField == false && session.getAttribute(fieldName) != null)
						{
							Object fieldValue = session.getAttribute(fieldName);
							if(fieldClass.isInstance(fieldValue))
							{
								foundField = true;
								field.setAccessible(true);
								field.set(object,fieldValue);
							}
						}
						if(foundField == false && servletContext.getAttribute(fieldName) != null)
						{
							Object fieldValue = servletContext.getAttribute(fieldName);
							if(fieldClass.isInstance(fieldValue))
							{
								foundField = true;
								field.setAccessible(true);
								field.set(object,fieldValue);
							}
						}
						if(foundField == false)
						{
							field.setAccessible(true);
							field.set(object,null);
						}
					}
				}
				System.out.println("Invoking Secured Method");
				method.invoke(object,arguments);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		SpringBootModel springBootModel = (SpringBootModel)servletContext.getAttribute("springBootModel");
		Map<String, Service> services = springBootModel.paths;
		String url = request.getPathInfo();
		try
		{
			if(services.containsKey(url) == false)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			Service service = services.get(url);
			Method serviceMethod = service.getServiceMethod();
			Class returnType = serviceMethod.getReturnType();
			Class serviceClass = service.getServiceClass();
			if(service.getIsGetAllowed() == false)
			{
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return;
			}
			Object object = serviceClass.newInstance();
			//Check for sercured AccessControlContext
			checkSecuredAccess(service,request,session,servletContext);
			//Injecting all scopes present in class
			injectScopes(service,object,request,session,servletContext);
			//Setting Autowired parameters
			setAutowiredParameters(service,object,request,session,servletContext);
			Object arguments[] = setMethodParameters(service,object,request,session,servletContext);
			Object returnValue = serviceMethod.invoke(object,arguments);
			if(returnType.getName().equals("void") == false)
			{
				PrintWriter out = response.getWriter();
				out.println(returnValue);
			}
			String forward = service.getForward();
			if(forward != null)
			{
				if(services.containsKey(forward) == true)
				{
					Service forwardService = services.get(forward);
					Method forwardServiceMethod = forwardService.getServiceMethod();					
					Class forwardServiceClass = forwardService.getServiceClass();
					Class forwardReturnType = forwardServiceMethod.getReturnType();
					Object forwardObject = forwardServiceClass.newInstance();
					//Injecting all scopes present in class
					injectScopes(forwardService,forwardObject,request,session,servletContext);
					//Setting Autowired parameters
					setAutowiredParameters(service,object,request,session,servletContext);
					Class parameterTypes[] = forwardServiceMethod.getParameterTypes();
					arguments = new Object[parameterTypes.length];
					Object forwardReturnValue = null;
					if(returnType.getName().equals("void") == false && parameterTypes.length == 1)
					{
						if(parameterTypes[0].equals(returnType))
						{
							arguments[0] = returnValue;
						}
						forwardReturnValue = forwardServiceMethod.invoke(forwardObject,arguments);
					}
					else
					{
						forwardReturnValue = forwardServiceMethod.invoke(forwardObject);
					}
					if(forwardReturnType.getName().equals("void") == false)
					{
						PrintWriter out = response.getWriter();
						out.println(forwardReturnValue);
					}
				}
				else
				{
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
					requestDispatcher.forward(request,response);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		ServletContext servletContext = getServletContext();
		HttpSession session = request.getSession();
		SpringBootModel springBootModel = (SpringBootModel) servletContext.getAttribute("springBootModel");
		Map<String, Service> services = springBootModel.paths;
		String url = request.getPathInfo();
		try
		{
			if(services.containsKey(url) == false)
			{
				System.out.println(url+" not found");
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			Service service = services.get(url);
			Class serviceClass = service.getServiceClass();
			if(service.getIsPostAllowed() == false)
			{
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				return;
			}
			Method serviceMethod = service.getServiceMethod();
			Object object = serviceClass.newInstance();
			//Check for sercured AccessControlContext
			checkSecuredAccess(service,request,session,servletContext);
			//Injecting all scopes present in class
			injectScopes(service,object,request,session,servletContext);
			//Setting all autowired parameters
			setAutowiredParameters(service,object,request,session,servletContext);
			Object arguments[] = setMethodParameters(service,object,request,session,servletContext);
			Class returnType = serviceMethod.getReturnType();
			Object returnValue = serviceMethod.invoke(object,arguments);
			if(returnType.getName().equals("void") == false)
			{
				PrintWriter out = response.getWriter();
				out.println(returnValue);
			}
			String forward = service.getForward();
			if(forward != null)
			{
				if(services.containsKey(forward) == true)
				{
					Service forwardService = services.get(forward);
					Method forwardServiceMethod = forwardService.getServiceMethod();
					Class forwardServiceClass = forwardService.getServiceClass();
					Class forwardReturnType = forwardServiceMethod.getReturnType();
					Object forwardObject = forwardServiceClass.newInstance();
					//Injecting all scopes present in class
					injectScopes(forwardService,forwardObject,request,session,servletContext);
					//Setting Autowired parameters
					setAutowiredParameters(forwardService,forwardObject,request,session,servletContext);
					Class parameterTypes[] = forwardServiceMethod.getParameterTypes();
					arguments = new Object[parameterTypes.length];
					Object forwardReturnValue = null;
					if(returnType.getName().equals("void") == false && parameterTypes.length == 1)
					{
						if(parameterTypes[0].equals(returnType))
						{
							arguments[0] = returnValue;
						}
						forwardReturnValue = forwardServiceMethod.invoke(forwardObject,arguments);
					}
					else
					{
						forwardReturnValue = forwardServiceMethod.invoke(forwardObject);
					}
					if(forwardReturnType.getName().equals("void") == false)
					{
						PrintWriter out = response.getWriter();
						out.println(forwardReturnValue);
					}
				}
				else
				{
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
					requestDispatcher.forward(request,response);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}