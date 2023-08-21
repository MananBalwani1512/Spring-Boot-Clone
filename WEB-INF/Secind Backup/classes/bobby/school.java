package bobby;
import com.thinking.machine.webRock.annotations.*;
import com.thinking.machine.webRock.pojo.*;
@Path(Path="/school/service")
@InjectApplicationScope
public class school
{
	@AutoWire()
	public ApplicationScope applicationScope;
	@InjectRequestParameter(Name="Name")
	public String Name;
	@Get
	@Path(Path="/getStudent")
	@ForwardTo(forward="/index.html")
	public void getStudent()
	{
		System.out.println(applicationScope);
		System.out.println("Get Student Method Called");
		applicationScope.setAttribute("Name","Manan Balwani");
	}
	/*@AutoWire(name="Name")
	public String name;*/
	@Post
	@ForwardTo(forward="/getStudent")
	@Path(Path="/addStudent")
	public void addStudent()
	{
		System.out.println("Add Student Method Called");
		System.out.println("Name : "+Name);
	}
	@Path(Path="/onstart")
	@OnStartup(Priority=1)
	public void onstart()
	{
		System.out.println("This Method is Going to work on startup");
	}
	@Path(Path="/deleteStudent")
	public void deleteStudent(@RequestParameter(Name="rollNo")int rollNo, @RequestParameter(Name="name")String name)
	{
		System.out.println("Student will Be deleted ");
		System.out.println("Name : "+name);
		System.out.println("Roll No : "+rollNo);
	}
	@Path(Path="/editStudent")
	public void editStudent(@RequestParameter(Name="name") String name,@AutoWire() ApplicationScope applicationScope)
	{
		System.out.println("Initial Name of Student was given by applicationScope : "+applicationScope.getAttribute("Name"));
		System.out.println("Editted Name of student is given by Request scope : "+name);
	}
	@SecuredAccess(checkPost="bobby.Student",gaurd="getDate")
	@Path(Path="/getDate")
	public void getDateOfStudent(@AutoWire SessionScope session)
	{
		System.out.println("Student Ws Born On : "+session.getAttribute("Date"));
	}

}