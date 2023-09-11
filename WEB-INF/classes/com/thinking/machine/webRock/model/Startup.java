package com.thinking.machine.webRock.model;
import java.lang.reflect.*;
public class Startup implements java.util.Comparator
{
    public int priority;
    public Method method;
    public int compare(Object s1, Object s2)
    {
        int p1 = ((Startup)s1).priority;
        int p2 = ((Startup)s2).priority;
        return p1-p2;
    }
}