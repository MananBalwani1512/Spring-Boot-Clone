package bobby;
import com.thinking.machine.webRock.annotations.*;
import com.thinking.machine.webRock.pojo.*;
@Path(Path="/school/service")
@InjectApplicationScope
public class school
{
	public ApplicationScope applicationScope;
	@Get
	@Path(Path="/getStudent")
	@ForwardTo(forward="/index.html")
	public void getStudent()
	{
		System.out.println("Get Student Method Called");
		applicationScope.setAttribute("Name","Manan Balwani");
	}
	@Post
	@ForwardTo(forward="/getStudent")
	@Path(Path="/addStudent")
	public void addStudent()
	{
		System.out.println("Add Student Method Called");
		System.out.println(applicationScope.getAttribute("Name"));
	}
	@Path(Path="/onstart")
	@OnStartup(Priority=1)
	public void onstart()
	{
		System.out.println("This Method is Going to work on startup");
	}
}