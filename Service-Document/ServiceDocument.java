import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;
import com.springboot.clone.annotations.*;
public class ServiceDocument
{
	public static void main(String[] args)
	{
		try
		{
			if(args.length > 2 || args.length == 0)
			{
				System.out.println("Invalid Number of Inputs");
			}
			String destination = "";
			if(args.length == 1)
				destination = "./";
			else
				destination = args[1];
			String servicePackage = args[0];
			File classesDirectory = new File("./"+servicePackage.replace(".","/"));
			System.out.println(classesDirectory.getPath());
			File pdfFile = new File(destination);
			PdfDocument pdfDocument = new PdfDocument(new PdfWriter(pdfFile));
			Document document = new Document(pdfDocument);
			java.util.List<Class> classes = new java.util.ArrayList<>();
			for(File file : classesDirectory.listFiles())
			{
				if(file.getName().endsWith(".class"))
				{
					String fileName = file.getName();
					classes.add(Class.forName(servicePackage+"."+fileName.substring(0,fileName.length()-6)));
				}
			}
			for(Class cls : classes)
			{
				scanClass(cls,document);
			}
			document.close();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	public static void scanClass(Class cls, Document document)
	{
		Paragraph paragraph = new Paragraph("Class "+cls.getName());
		document.add(paragraph);
		Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
		table.setMarginTop(5);
		table.addCell(new Cell().add(new Paragraph("Request Scope Injectable")));
		if(cls.isAnnotationPresent(InjectRequestScope.class))
			table.addCell(new Cell().add(new Paragraph("Yes")));
		else
			table.addCell(new Cell().add(new Paragraph("No")));
		table.addCell(new Cell().add(new Paragraph("Session Scope Injectable")));
		if(cls.isAnnotationPresent(InjectSessionScope.class))
			table.addCell(new Cell().add(new Paragraph("Yes")));
		else
			table.addCell(new Cell().add(new Paragraph("No")));
		table.addCell(new Cell().add(new Paragraph("Application Scope Injectable")));
		if(cls.isAnnotationPresent(InjectApplicationScope.class))
			table.addCell(new Cell().add(new Paragraph("Yes")));
		else
			table.addCell(new Cell().add(new Paragraph("No")));
		table.addCell(new Cell().add(new Paragraph("Application Directory Injectable")));
		if(cls.isAnnotationPresent(InjectApplicationDirectory.class))
			table.addCell(new Cell().add(new Paragraph("Yes")));
		else
			table.addCell(new Cell().add(new Paragraph("No")));
		for(Field field : cls.getDeclaredFields())
		{
			table.addCell(new Cell(1,2).add(new Paragraph("Field "+field.getName())));
			table.addCell(new Cell().add(new Paragraph("Autowired")));
			if(field.isAnnotationPresent(Autowired.class))
			{
				Autowired autowired = (Autowired)field.getAnnotation(Autowired.class);
				table.addCell(new Cell().add(new Paragraph("Yes")));
				table.addCell(new Cell().add(new Paragraph("Autowired Field Name")));
				table.addCell(new Cell().add(new Paragraph(autowired.name())));
			}
			else
				table.addCell(new Cell().add(new Paragraph("No")));
		}
		for(Method method : cls.getDeclaredMethods())
		{
			table.addCell(new Cell(1,2).add(new Paragraph("Method "+method.getName())));
			table.addCell(new Cell().add(new Paragraph("Return Type")));
			table.addCell(new Cell().add(new Paragraph(method.getReturnType().getName())));
			if(cls.isAnnotationPresent(Path.class) && method.isAnnotationPresent(Path.class))
			{
				Path classPath = (Path)cls.getAnnotation(Path.class);
				Path methodPath = (Path)method.getAnnotation(Path.class);
				table.addCell(new Cell().add(new Paragraph("URI")));
				table.addCell(new Cell().add(new Paragraph(classPath.value()+methodPath.value())));
				if(method.isAnnotationPresent(Post.class) || method.isAnnotationPresent(Get.class) || cls.isAnnotationPresent(Get.class) || cls.isAnnotationPresent(Post.class))
				{
					table.addCell(new Cell().add(new Paragraph("Request Type Allowed")));	
					if(method.isAnnotationPresent(Post.class))
					{
						table.addCell(new Cell().add(new Paragraph("POST")));
					}
					if(method.isAnnotationPresent(Get.class))
					{
						table.addCell(new Cell().add(new Paragraph("GET")));
					}
					if(cls.isAnnotationPresent(Post.class))
					{
						table.addCell(new Cell().add(new Paragraph("POST")));
					}
					if(cls.isAnnotationPresent(Get.class))
					{
						table.addCell(new Cell().add(new Paragraph("GET")));
					}
				}
			}
			if(method.isAnnotationPresent(OnStartup.class))
			{
				table.addCell(new Cell().add(new Paragraph("Run on server start")));
				table.addCell(new Cell().add(new Paragraph("Yes")));
			}
			if(method.isAnnotationPresent(Forward.class))
			{
				Forward forward = (Forward)method.getAnnotation(Forward.class);
				table.addCell(new Cell().add(new Paragraph("Forward to")));
				table.addCell(new Cell().add(new Paragraph(forward.value())));
			}
			for(Parameter parameter : method.getParameters())
			{
				table.addCell(new Cell(1,2).add(new Paragraph("Method Parameter "+parameter.getName())));
				table.addCell(new Cell().add(new Paragraph("Type")));
				table.addCell(new Cell().add(new Paragraph(parameter.getType().getName())));
				table.addCell(new Cell().add(new Paragraph("Request Scope Injectable")));
				if(parameter.isAnnotationPresent(InjectRequestScope.class))
					table.addCell(new Cell().add(new Paragraph("Yes")));
				else
					table.addCell(new Cell().add(new Paragraph("No")));
				table.addCell(new Cell().add(new Paragraph("Session Scope Injectable")));
				if(parameter.isAnnotationPresent(InjectSessionScope.class))
					table.addCell(new Cell().add(new Paragraph("Yes")));
				else
					table.addCell(new Cell().add(new Paragraph("No")));
				table.addCell(new Cell().add(new Paragraph("Application Scope Injectable")));
				if(parameter.isAnnotationPresent(InjectApplicationScope.class))
					table.addCell(new Cell().add(new Paragraph("Yes")));
				else
					table.addCell(new Cell().add(new Paragraph("No")));
				table.addCell(new Cell().add(new Paragraph("Application Directory Injectable")));
				if(parameter.isAnnotationPresent(InjectApplicationDirectory.class))
					table.addCell(new Cell().add(new Paragraph("Yes")));
				else
					table.addCell(new Cell().add(new Paragraph("No")));
				table.addCell(new Cell().add(new Paragraph("Request Parameter")));
				if(parameter.isAnnotationPresent(RequestParameter.class))
				{
					RequestParameter requestParameter = (RequestParameter) parameter.getAnnotation(RequestParameter.class);
					table.addCell(new Cell().add(new Paragraph("Yes")));
					table.addCell(new Cell().add(new Paragraph("Name of Request Parameter")));
					table.addCell(new Cell().add(new Paragraph(requestParameter.name())));
				}
				else
					table.addCell(new Cell().add(new Paragraph("No")));
			}
		}
		document.add(table);
	}
}