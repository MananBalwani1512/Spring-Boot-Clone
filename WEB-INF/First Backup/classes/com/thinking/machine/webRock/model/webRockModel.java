package com.thinking.machine.webRock.model;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.thinking.machine.webRock.annotations.*;
import com.thinking.machine.webRock.pojo
.*;
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
			Field[] feilds = c.getDeclaredFields();
			Service serviceClass = new Service();
			System.out.println(feilds.length);
			obj = c.newInstance();
			serviceClass.setURL(className);
			if(c.isAnnotationPresent(InjectRequestScope.class))
			{
				serviceClass.setIsRequestScope(true);
				for(Field field : feilds)
				{
					if(field.getType().equals(RequestScope.class))
					{
						serviceClass.setRequestScope(field);
						break;
					}
				}
			}
			if(c.isAnnotationPresent(InjectSessionScope.class))
			{
				serviceClass.setIsSessionScope(true);
				for(Field field : feilds)
				{
					if(field.getType().equals(SessionScope.class))
					{
						serviceClass.setSessionScope(field);
						break;
					}
				}
			}
			if(c.isAnnotationPresent(InjectApplicationScope.class))
			{
				serviceClass.setIsApplicationScope(true);
				System.out.println("settings feild");
				for(Field field : feilds)
				{
					System.out.println(field.getType());
					if(field.getType().equals(ApplicationScope.class))
					{
						serviceClass.setApplicationScope(field);
						System.out.println("Feild Setted");
						break;
					}
				}
			}
			
			Method[] methods = c.getDeclaredMethods();
			ArrayList<Startup>s = new ArrayList();
			Path path = (Path)c.getAnnotation(Path.class);
			String classPath = path.Path();
			map.put(classPath,serviceClass);
			System.out.println(methods.length);
			for(Method m : methods)
			{
				System.out.println("For Loop Working");
				Service ser = new Service();
				Path p = (Path)m.getAnnotation(Path.class);
				String URL = classPath+p.Path();
				ser.setURL(URL);
				ser.setMethod(m);
				System.out.println("Before If Comdition");
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
			System.out.println(map);
			sc.setAttribute("map",map);
			sc.setAttribute("object",obj);
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
}