package com.thinking.machine.webRock.pojo;
import javax.servlet.*;
import javax.servlet.http.*;
public class RequestScope
{
    private HttpServletRequest request;
    public RequestScope(HttpServletRequest request)
    {
        this.request = request;
    }
    public void setAttribute(String name, Object obj)
    {
        request.setAttribute(name,obj);
    }
    public Object getAttribute(String name)
    {
        return request.getAttribute(name);
    }
}
