package com.springboot.clone.pojo;
import java.io.File;
public class ApplicationDirectory
{
	private File directory;
	public ApplicationDirectory(File directory)
	{
		this.directory = directory;
	}
	public File getDirectory()
	{
		return directory;
	}
}