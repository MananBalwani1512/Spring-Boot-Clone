package com.thinking.machine.webRock.pojo;
import javax.servlet.*;
import javax.servlet.http.*;
public class SessionScope
{
    private HttpSession session;
    public SessionScope(HttpServletRequest request)
    {
        session = request.getSession();
    }
    public void setAttribute(String name, Object obj)
    {
        session.setAttribute(name,obj);
    }
    public Object getAttribute(String name)
    {
        return session.getAttribute(name);
    }
}
