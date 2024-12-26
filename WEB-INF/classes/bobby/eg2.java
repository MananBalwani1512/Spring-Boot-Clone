package bobby;
import com.springboot.clone.annotations.*;
import com.springboot.clone.pojo.*;
@Path(value = "/teacher")
@InjectRequestScope
@InjectSessionScope
@InjectApplicationScope
public class eg2
{
	private RequestScope requestScope;
	private SessionScope sessionScope;
	private ApplicationScope applicationScope;
	@Get
	@Path("/getName")
	public void getName()
	{
		System.out.println("My Name is Gogo");
		sessionScope.setAttribute("Name","Manan Balwani");
	}
	@Post
	@Path(value = "/add")
	public int add()
	{
		System.out.println("REQUEST SCOPE : "+requestScope.getAttribute("name"));
		requestScope.setAttribute("RESULT","MANAN");
		System.out.println("RESULT : "+requestScope.getAttribute("RESULT"));
		String naam = applicationScope.getAttribute("Naam").toString();
		System.out.println("ApplicationScope : "+naam);
		return 5+4;
	}
	@Forward(value = "/teacher/edit")
	@Get
	@Path(value = "/delete")
	public int delete()
	{
		System.out.println("deleting teacher");
		return 420;
	}
	@Get
	@Path(value = "/edit")
	public void edit(int code)
	{
		System.out.println("Chachi : "+code);
	}
	@Get
	@Path(value = "/getAll")
	public String getAll(@RequestParameter(name = "x") int x, @RequestParameter(name = "y") String y, @RequestParameter(name = "z") boolean z)
	{
		return y+" "+x+" "+z;
	}
	@Post
	@Path(value = "/getByEmail")
	public String getByEmail(@RequestParameter() Student1 student1, @InjectRequestScope RequestScope requestScope1, @InjectSessionScope SessionScope sessionScope1, @InjectApplicationScope ApplicationScope applicationScope1)
	{
		System.out.println(student1.getAge()+" "+student1.getName());
		System.out.println(requestScope1+"   "+sessionScope1+"   "+applicationScope1);
		return student1.getName()+" "+student1.getAge();
	}
	@SecuredAccess(checkPost = "bobby.eg1", guard = "deleteStudent")
	@Get
	@Path("/naam")
	public String getNaam(@InjectApplicationScope ApplicationScope applicationScope)
	{
		String naam = (String)applicationScope.getAttribute("Naam");
		String result = "Naam "+naam;
		return result;
	}
}
class Student1
{
	private int age;
	private String name;
	public void setName(String name)
	{
		this.name = name;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public String getName()
	{
		return this.name;
	}
	public int getAge()
	{
		return this.age;
	}
}