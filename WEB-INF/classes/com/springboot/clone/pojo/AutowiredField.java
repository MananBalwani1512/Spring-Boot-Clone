package com.springboot.clone.pojo;
import java.util.*;
import java.lang.reflect.*;
public class AutowiredField implements java.io.Serializable
{
	private String fieldName;
	private Field field;
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public String getFieldName()
	{
		return this.fieldName;
	}
	public void setField(Field field)
	{
		this.field = field;
	}
	public Field getField()
	{
		return this.field;
	}
}