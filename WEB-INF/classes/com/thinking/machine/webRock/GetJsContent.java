package com.thinking.machine.webRock;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class GetJsContent extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            System.out.println("Working get content js");
            String fileName = request.getParameter("name");
            System.out.println(fileName);
            File file = new File("C:/Tomcat9/webapps/tmWebRock/WEB-INF/js/"+fileName+".js");
            Scanner reader = new Scanner(file);
            String d = "";
            System.out.println(reader.hasNextLine());
            PrintWriter out = response.getWriter();
            while(reader.hasNextLine())
            {
                out.println(reader.nextLine());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
