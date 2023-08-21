package com.thinking.machine.webRock.model;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.thinking.machine.webRock.annotations.*;
import com.thinking.machine.webRock.pojo.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machine.webRock.pojo.*;
public class webRockModel extends HttpServlet
{
	public void init() throws ServletException
	{
		try {
		HashMap<String,Service>map = new HashMap();
		String className = getServletConfig().getInitParameter("SERVICE_PACKAGE_PREFIX");
		String folderName = className.replace('.','/');
		ServletContext sc = getServletContext();
		File file = new File("C:/Tomcat9/webapps/tmWebRock/WEB-INF/classes/"+folderName);
		ArrayList<String>list = new ArrayList(Arrays.asList(file.list()));
		Class c = null;
		Object obj = null;
		for(String f : list)
		{
			if(!f.contains(".class"))
				continue;
			try
			{
				f = f.substring(0,f.indexOf('.'));
				System.out.println(f);
				c = Class.forName(className+"."+f);	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			Field[] fields = c.getDeclaredFields();	
			System.out.println(fields.length);
			obj = c.newInstance();
			List<Service>autoWire = new ArrayList();
			Service serviceClass = new Service();
			List<Service>extract = new ArrayList();
			for(Field field : fields)
			{
				serviceClass = new Service();
				if(field.isAnnotationPresent(InjectRequestParameter.class))
				{
					serviceClass.setIsInjectRequestParameter(true);
					serviceClass.setInjectRequestParameter(field);
					InjectRequestParameter irp = (InjectRequestParameter)field.getAnnotation(InjectRequestParameter.class);
					serviceClass.setRequestName(irp.Name());
					autoWire.add(serviceClass);
				}
				if(field.isAnnotationPresent(AutoWire.class))
				{
					AutoWire annotation = (AutoWire)field.getAnnotation(AutoWire.class);
					System.out.println(annotation.name());
					if(annotation.name().equals(""))
					{
						serviceClass.setIsAutoWire(false);
						System.out.println("Going to set feild in webRockModel");
						if(field.getType().equals(RequestScope.class))
						{
							serviceClass.setIsRequestScope(true);
							serviceClass.setRequestScope(field);
						}
						else if(field.getType().equals(SessionScope.class))
						{
							serviceClass.setIsSessionScope(true);
							serviceClass.setSessionScope(field);
						}
						else if(field.getType().equals(ApplicationScope.class))
						{
							System.out.println("setting applicationScope in webRockModel");
							serviceClass.setIsApplicationScope(true);
							serviceClass.setApplicationScope(field);
						}
						autoWire.add(serviceClass);
					}
					else
					{
						serviceClass.setIsAutoWire(true);
						serviceClass.setAutoWire(field);
						serviceClass.setName(annotation.name());
						extract.add(serviceClass);
					}
				}
			}
			Method[] methods = c.getDeclaredMethods();
			ArrayList<Startup>s = new ArrayList();
			Path path = (Path)c.getAnnotation(Path.class);
			String classPath = path.Path();
			
			sc.setAttribute(classPath,obj);
			System.out.println(methods.length);
			for(Method m : methods)
			{
				createJs(m, classPath);
				List<Parameter>par = new ArrayList();
				System.out.println("For Loop Working");
				Service ser = new Service();
				getSecuredAccess(m,ser);
				Path p = (Path)m.getAnnotation(Path.class);
				String URL = classPath+p.Path();
				ser.setURL(URL);
				ser.setMethod(m);
				System.out.println(m.getName());
				System.out.println("Before If Comdition");
				Parameter[] parameters = m.getParameters();
				for(Parameter parameter : parameters)
				{
					if(parameter.isAnnotationPresent(RequestParameter.class))
					{
						ser.setIsRequestParameter(true);
						par.add(parameter);
					}
					else if(parameter.isAnnotationPresent(AutoWire.class))
					{
						ser.setIsAutoWire(true);
						par.add(parameter);
					}
					else
						par.add(parameter);
				}
				ser.setRequestParameters(par);
				if(m.isAnnotationPresent(OnStartup.class))
				{
					try {
					System.out.println("Inside If Condition of annotationa");
					OnStartup on = (OnStartup)m.getAnnotation(OnStartup.class);
					System.out.println("Going outside");
					Startup st = new Startup();
							System.out.println("Going outside BITCH");
					st.priority = on.Priority();
					st.method = m;
					System.out.println("Going outside");
					s.add(st);
					}catch(Exception e)
					{
						System.out.println("KUTTA");
						e.printStackTrace();
					}
					
				}	
				if(m.isAnnotationPresent(Get.class))
					ser.setMethodType("GET");
				else if(m.isAnnotationPresent(Post.class))
					ser.setMethodType("POST");
				else
					ser.setMethodType("ANY");
				if(m.isAnnotationPresent(ForwardTo.class))
				{
					System.out.println("Inside Forward");
					ForwardTo ft = (ForwardTo)m.getAnnotation(ForwardTo.class);
					ser.setForwardTo(ft.forward());
					System.out.println("Outside Forward");
				}
				map.put(URL,ser);
			}
			System.out.println(autoWire);
			sc.setAttribute(classPath+"map",map);
			sc.setAttribute(classPath+"autoWire",autoWire);
			sc.setAttribute(classPath+"extract",extract);
			Collections.sort(s, new Comparator(){
				public int compare(Object s1, Object s2)
				{
					int s1pri = ((Startup)s1).priority;
					int s2pri = ((Startup)s2).priority;
					return s1pri-s2pri;
				}
			});
			for(Startup start : s)
			{
				if(start.method == null)
				{
					break;
				}
				start.method.invoke(obj);
			}
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void getSecuredAccess(Method m,Service service)
	{
		System.out.println("SECURITY"+m.isAnnotationPresent(SecuredAccess.class));
		if(m.isAnnotationPresent(SecuredAccess.class))
		{
			SecuredAccess sa = (SecuredAccess)m.getAnnotation(SecuredAccess.class);
			try
			{
				Class c = Class.forName(sa.checkPost());
				Method methods[] = c.getDeclaredMethods();
				for(Method method : methods)
				{
					System.out.println(method.getName());
					System.out.println(sa.gaurd());
					if(method.getName().equals(sa.gaurd()))
					{
						System.out.println("Method Nm= "+method.getName());
						service.setIsSecuredAccess(true);
						service.setCheckPost(c);
						service.setGaurd(method);
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void createJs (Method method,String classPath)
	{
		try
		{
			File file = new File("C:/Tomcat9/webapps/tmWebRock/WEB-INF/js/"+method.getName()+".js");
			if(file.exists())
			{
				file.delete();
				file.createNewFile();	
			}
			FileWriter writer = new FileWriter(file);
			writer.write("$(()=>{\n");
			if(method.getParameters().length == 0)
			{
				writer.write("function "+method.getName()+"(success){\n");
			}
			else
			{
				writer.write("function "+method.getName()+"(data,success){\n");
			}
			Path path = (Path)method.getAnnotation(Path.class);
			String methodPath = path.Path();
			writer.write("var obj = {\n");
			writer.write("'url' : '"+classPath.substring(1)+methodPath+"',\n");
			if(method.isAnnotationPresent(Post.class))
			{
				writer.write("'type' : 'POST',\n");
			}
			else
			{
				writer.write("'type' : 'GET',\n");
			}
			if(method.getParameters().length != 0)
			{
				writer.write("'data' : data,\n");
			}
			writer.write("'success' : function(result){success(result);}\n");
			writer.write("};\n");
			writer.write("$.ajax(obj)\n");
			writer.write("}\n");
			writer.write("});");
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}