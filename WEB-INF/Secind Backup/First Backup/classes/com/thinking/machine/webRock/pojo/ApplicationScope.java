package com.thinking.machine.webRock.pojo;
import javax.servlet.*;
import javax.servlet.http.*;
public class ApplicationScope
{
    private ServletContext sc;
    public ApplicationScope(ServletContext sc)
    {
        this.sc = sc;
    }
    public void setAttribute(String name, Object obj)
    {
        sc.setAttribute(name,obj);
    }
    public Object getAttribute(String name)
    {
        return sc.getAttribute(name);
    }
}
