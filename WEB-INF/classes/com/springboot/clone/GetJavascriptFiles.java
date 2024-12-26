package com.springboot.clone;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class GetJavascriptFiles extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			ServletContext servletContext = request.getServletContext();
			String fileName = request.getParameter("fileName");
			File file = new File(servletContext.getRealPath("WEB-INF")+"/js/"+fileName+".js");
			if(file.exists() == false)
			{
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			PrintWriter out = response.getWriter();
			while(true)
			{
				String line = bufferedReader.readLine();
				if(line == null)
					break;
				out.println(line);
			}
			bufferedReader.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
}