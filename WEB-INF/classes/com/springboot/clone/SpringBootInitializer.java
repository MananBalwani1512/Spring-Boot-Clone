package com.springboot.clone;
import javax.servlet.*;
import javax.servlet.http.*;
import com.springboot.clone.pojo.*;
import java.util.*;
import java.io.*;
import java.io.File.*;
import com.springboot.clone.model.*;
import java.lang.reflect.*;
import com.springboot.clone.annotations.*;
public class SpringBootInitializer extends HttpServlet
{
	public void compileServiceFiles(File directory, ServletContext servletContext)
	{
		try
		{
			String directoryPath = servletContext.getRealPath("WEB-INF/classes");
			List<String> command = new ArrayList<>();
			command.add("javac");
			command.add("-classpath");
			command.add(directoryPath+";.");
			command.add("-parameters");
			for(File file : directory.listFiles())
			{
				if(file.getName().endsWith(".java"))
				{
					command.add(file.getName());
				}
			}
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			processBuilder.directory(directory);
			Process process = processBuilder.start();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while(true)
			{
				line = bufferedReader.readLine();
				if(line == null)
					break;
				System.out.println(line);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	public void createJavascriptFiles(List<Class> classes, ServletContext servletContext,String baseURLPrefix)
	{
		String path = servletContext.getRealPath("WEB-INF")+"/js";
		File directory = new File(path);
		if(directory.exists() == true)
		{
			for(File file : directory.listFiles())
			{
				file.delete();
			}
		}
		for(Class cls : classes)
		{
			createJavascriptFile(cls,path,baseURLPrefix);
		}
	}
	public void createJavascriptFile(Class cls, String path, String baseURLPrefix)
	{
		try
		{
			File directory = new File(path);
			if(directory.exists() == false)
			{
				directory.mkdir();
			}
			Path classPath = (Path) cls.getAnnotation(Path.class);
			String classURL = classPath.value();
			String className = cls.getName();
			String[] splittedClassName = className.split("\\.");
			String fileName = splittedClassName[splittedClassName.length-1];
			File file = new File(path+"/"+fileName+".js");
			BufferedWriter bufferedWriter = null;
			List<Field> fields = new ArrayList<>();
			List<Parameter> parameters = new ArrayList<>();
			String contentType = "";
			if(file.exists() == true)
			{
				file.delete();
				file.createNewFile();
			}
			bufferedWriter = new BufferedWriter(new FileWriter(file,true));
			
			
			//Creating class for service in the file
			bufferedWriter.write("class "+fileName+"Service\n{\n");
			for(Method method : cls.getDeclaredMethods())
			{
				contentType = "";
				Parameter params[] = method.getParameters();
				List<Parameter> methodParameters = new ArrayList<>();
				for(Parameter parameter : params)
				{
					if(parameter.isAnnotationPresent(RequestParameter.class) == true)
					{
						parameters.add(parameter);
						methodParameters.add(parameter);
						Class parameterType = parameter.getType();
						if(parameterType.equals(int.class) || parameterType.equals(char.class)|| parameterType.equals(byte.class) || parameterType.equals(short.class) || parameterType.equals(double.class) || parameterType.equals(float.class) || parameterType.equals(long.class) || parameterType.equals(boolean.class) || parameterType.equals(String.class))
						{
							contentType = "application/x-www-form-urlencoded";
						}
						else
						{
							contentType = "application/json";
						}
					}
				}
				if(method.isAnnotationPresent(Path.class))
				{
					Path methodPath = (Path) method.getAnnotation(Path.class);
					String url = classURL+methodPath.value();
					String objectName = fileName.substring(0,1).toLowerCase()+fileName.substring(1);
					String queryString = "";
					bufferedWriter.write("\t");
					if(methodParameters.size() > 0)
					{
						bufferedWriter.write(method.getName()+"("+objectName+")\n\t{\n");
						bufferedWriter.write("\t\tvar data = {\n");
						for(int i = 0; i < methodParameters.size(); i++)
						{
							Parameter parameter = methodParameters.get(i);
							String parameterName = parameter.getName();
							String methodName = "get"+parameterName.substring(0,1).toUpperCase()+parameterName.substring(1);
							if(i == 0)
								queryString = queryString+"?"+parameterName+"='+data."+parameterName+"+'";
							else	
								queryString = queryString+"&"+parameterName+"='+data."+parameterName+"+'";
							bufferedWriter.write("\t\t\t'"+parameterName+"' : "+objectName+"."+methodName+"()");
							if(i < parameters.size()-1)
								bufferedWriter.write(",");
							bufferedWriter.write("\n");
						}
						bufferedWriter.write("\t\t};\n");
					}
					else
						bufferedWriter.write(method.getName()+"()\n\t{\n");
					bufferedWriter.write("\t\tvar promise = new Promise((resolve,reject)=>\n\t\t{\n");
					bufferedWriter.write("\t\t\tvar xmlHttpRequest = new XMLHttpRequest();\n");
					bufferedWriter.write("\t\t\txmlHttpRequest.onreadystatechange = ()=>\n");
					bufferedWriter.write("\t\t\t{\n");
					bufferedWriter.write("\t\t\t\tif(xmlHttpRequest.readyState == 4)\n\t\t\t\t{\n");
					bufferedWriter.write("\t\t\t\t\tif(xmlHttpRequest.status == 200)\n\t\t\t\t\t{\n");
					bufferedWriter.write("\t\t\t\t\t\tvar response = xmlHttpRequest.responseText;\n");
					bufferedWriter.write("\t\t\t\t\t\tresolve(response);\n");
					
					bufferedWriter.write("\t\t\t\t\t}\n");
					bufferedWriter.write("\t\t\t\t\telse\n\t\t\t\t\t{\n");
					bufferedWriter.write("\t\t\t\t\t\treject();\n");
					
					bufferedWriter.write("\t\t\t\t\t}\n");
					bufferedWriter.write("\t\t\t\t}\n");
					bufferedWriter.write("\t\t\t}\n");
					if(cls.isAnnotationPresent(Get.class) || method.isAnnotationPresent(Get.class))
					{
						bufferedWriter.write("\t\t\txmlHttpRequest.open('GET', '"+baseURLPrefix+url+queryString+"',true);\n");
						bufferedWriter.write("\t\t\txmlHttpRequest.send();\n");
					}
					if(cls.isAnnotationPresent(Post.class) || method.isAnnotationPresent(Post.class))
					{
						bufferedWriter.write("\t\t\txmlHttpRequest.open('POST', '"+baseURLPrefix+url+"',true);\n");
						if(contentType.equals("") == false)
						{
							bufferedWriter.write("\t\t\txmlHttpRequest.setRequestHeader('content-type', '"+contentType+"');\n");
							if(contentType.equals("application/json"))
								bufferedWriter.write("\t\t\txmlHttpRequest.send(JSON.stringify(data."+methodParameters.get(0).getName()+"));\n");
							else
								bufferedWriter.write("\t\t\txmlHttpRequest.send('"+queryString.substring(1)+"');\n");
						}
						else
							bufferedWriter.write("\t\t\txmlHttpRequest.send();\n");
					}
					bufferedWriter.write("\t\t});\n");
					bufferedWriter.write("\t\treturn promise;\n");
					bufferedWriter.write("\t}\n");
				}
			}
			bufferedWriter.write("}\n");
			
			//Creating class for POJO's
			if(parameters.size() > 0)
			{
				bufferedWriter.write("class "+fileName+"\n{\n");
				for(Parameter parameter : parameters)
				{
					String parameterName = parameter.getName();
					String functionName = parameterName.substring(0,1).toUpperCase()+parameterName.substring(1);
					bufferedWriter.write("\tset"+functionName+"("+parameterName+")\n\t{\n");
					bufferedWriter.write("\t\tthis."+parameterName+" = "+parameterName+";\n");
					bufferedWriter.write("\t}\n");
					bufferedWriter.write("\tget"+functionName+"()\n\t{\n");
					bufferedWriter.write("\t\treturn this."+parameterName+";\n");
					bufferedWriter.write("\t}\n");
				}
				bufferedWriter.write("}\n");
			}
			bufferedWriter.close();
		
		
			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	public void init()throws ServletException
	{
		System.out.println("\n\n\n\n\n\n\nON STARTUP OF SPRING-BOOT-CLONE\n\n\n\n\n\n\n");
		try
		{
			System.out.println("-----------------------------------------------------");
			SpringBootModel springBootModel = new SpringBootModel();
			Map<String,Service> services = new HashMap<>();
			List<Service> startupServices = new ArrayList<>();
			//Scanning all necessary classes and putting it in the hashmap
			ServletConfig servletConfig = getServletConfig();
			ServletContext servletContext = getServletContext();
			String servicePackagePrefix = servletConfig.getInitParameter("SERVICE_PACKAGE_PREFIX");
			String baseURLPrefix = servletConfig.getInitParameter("BASE_URL_PREFIX");
			String directoryPath = servicePackagePrefix.replace('.', '/');
			String path = servletContext.getRealPath("/WEB-INF/classes");
			File directory= new File(path+"/"+directoryPath);
			compileServiceFiles(directory, servletContext);
			List<Class> classes = new ArrayList<>();
			if(directory.exists() && directory.isDirectory())
			{
				for(File file : directory.listFiles())
				{
					if(file.getName().endsWith(".class"))
					{
						String fileName = file.getName();
						String className = fileName.substring(0,fileName.length()-6);
						Class cls = Class.forName(servicePackagePrefix+"."+className);
						//Check if Path annotation is present on the class.
						//If yes scan for methods else leave it.
						boolean isPathPresent = scanClassForPath(cls,services);
						if(isPathPresent == true)
						{
							classes.add(cls);
						}
						//Checking for startup methods in the class
						scanForStartupMethods(cls,startupServices);
					}
				}
				createJavascriptFiles(classes,servletContext,baseURLPrefix);
				springBootModel.paths = services;
				servletContext.setAttribute("springBootModel",springBootModel);
			}
			else
			{
				System.out.println("Package : "+servicePackagePrefix+" does not exist");
			}
			for(Service service : startupServices)
			{
				Method serviceMethod = service.getServiceMethod();
				Class serviceClass = service.getServiceClass();
				Object object = serviceClass.newInstance();
				Field fields[] = serviceClass.getDeclaredFields();
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
				serviceMethod.invoke(object);
			}
			System.out.println("--------------------------------------------------\n\n\n\n\n\n\n\n\n\n\n\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean scanClassForPath(Class cls, Map<String,Service> services)
	{
		boolean isPathPresent = false;
		try
		{
			if(cls.isAnnotationPresent(Path.class))
			{
				isPathPresent = true;
				Path path = (Path)cls.getAnnotation(Path.class);
				String classPath = path.value();
				for(Method method : cls.getDeclaredMethods())
				{
					if(method.isAnnotationPresent(Path.class))
					{
						path = (Path)method.getAnnotation(Path.class);
						String url = classPath+path.value();
						Service service = new Service();
						service.setServiceClass(cls);
						service.setServiceMethod(method);
						service.setURL(url);
						if(cls.isAnnotationPresent(Get.class) || method.isAnnotationPresent(Get.class))
						{
							service.setIsPostAllowed(false);
						}
						if(cls.isAnnotationPresent(Post.class) || method.isAnnotationPresent(Post.class))
						{
							service.setIsGetAllowed(false);
						}
						if(cls.isAnnotationPresent(InjectRequestScope.class))
						{
							service.setInjectRequestScope(true);
						}
						if(cls.isAnnotationPresent(InjectSessionScope.class))
						{
							service.setInjectSessionScope(true);
						}
						if(cls.isAnnotationPresent(InjectApplicationScope.class))
						{
							service.setInjectApplicationScope(true);
						}
						if(cls.isAnnotationPresent(InjectApplicationDirectory.class))
						{
							service.setInjectApplicationDirectory(true);
						}
						if(method.isAnnotationPresent(Forward.class))
						{
							Forward forward = (Forward) method.getAnnotation(Forward.class);
							String forwardPath = forward.value();
							service.setForward(forwardPath);
						}
						else
						{
							service.setForward(null);
						}
						if(method.isAnnotationPresent(SecuredAccess.class))
						{
							SecuredAccess securedAccess = (SecuredAccess) method.getAnnotation(SecuredAccess.class);
							String checkPost = securedAccess.checkPost();
							String guard = securedAccess.guard();
							service.setIsSecuredAccess(true);
							service.setSecuredAccessCheckPost(checkPost);
							service.setSecuredAccessGuard(guard);
						}
						//Checking for AutowiredFields
						List<AutowiredField> autowiredFields = scanForAutowiredFields(cls);
						service.setParameters(method.getParameters());
						service.setAutowiredFields(autowiredFields);
						services.put(url,service);
					}
				}
			}
			
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return isPathPresent;
	}
	public void scanForStartupMethods(Class cls, List<Service> startupServices)
	{
		for(Method method : cls.getDeclaredMethods())
		{
			Class returnType = method.getReturnType();
			if(method.isAnnotationPresent(OnStartup.class) && returnType.getName().equals("void"))
			{
				Service service = new Service();
				service.setServiceMethod(method);
				service.setServiceClass(cls);
				service.setRunOnStartup(true);
				if(cls.isAnnotationPresent(InjectApplicationScope.class))
				{
					service.setInjectApplicationScope(true);
				}
				if(cls.isAnnotationPresent(InjectApplicationDirectory.class))
				{
					service.setInjectApplicationDirectory(true);
				}
				OnStartup onStartup = (OnStartup) method.getAnnotation(OnStartup.class);
				int priority = onStartup.priority();
				service.setPriority(priority);
				startupServices.add(service);
			}
		}
		startupServices.sort((a,b) -> 
		{
			return Integer.compare(a.getPriority(), b.getPriority());
		});
	}
	public List<AutowiredField> scanForAutowiredFields(Class cls)
	{
		List<AutowiredField> autowiredFields = new ArrayList<>();
		Field fields[] = cls.getDeclaredFields();
		for(Field field : fields)
		{
			if(field.isAnnotationPresent(Autowired.class))
			{
				Autowired autowired = (Autowired)field.getAnnotation(Autowired.class);
				AutowiredField autowiredField = new AutowiredField();
				autowiredField.setFieldName(autowired.name());
				autowiredField.setField(field);
				autowiredFields.add(autowiredField);
			}
		}
		return autowiredFields;
	}
}