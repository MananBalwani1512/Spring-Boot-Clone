package bobby;
import com.springboot.clone.annotations.*;
import com.springboot.clone.pojo.*;
@Path(value = "/student")
@Get
@InjectSessionScope
@InjectApplicationScope
@InjectApplicationDirectory
public class eg1
{
	private SessionScope sessionScope;
	private ApplicationScope applicationScope;
	private ApplicationDirectory applicationDirectory;
	@Autowired(name = "MANAN")
	private String manan;
	@Autowired(name = "TEST1")
	private String test1;
	
	@Forward(value = "/teacher")
	@Path(value = "/getStudent")
	public void getStudent()
	{
		System.out.println("This is a get student method");
		System.out.println("This Session Scope Name : "+sessionScope.getAttribute("Name"));
		System.out.println("Path : "+applicationDirectory.getDirectory().getPath());
		System.out.println("AUTOWIRED TESTING : "+test1+"     "+manan);
	}
	@Path(value = "/delete")
	public void deleteStudent()
	{
		System.out.println("Setting Naam Shabana");
		applicationScope.setAttribute("Naam", "Shabana");
	}
	@Path(value = "/edit")
	public Object editStudent()
	{
		return applicationScope.getAttribute("Naam");
	}
	@Forward(value = "/teacher/add")
	@Path(value = "/addStudent")
	public int addStudent()
	{
		return 1;
	}
	@OnStartup(priority = 2)
	public int get()
	{
		return 2;
	}
}