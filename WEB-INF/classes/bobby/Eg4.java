package bobby;
import com.springboot.clone.annotations.*;
import com.springboot.clone.pojo.*;
@Path(value = "/eg4")
public class Eg4
{
	@Path(value = "/test")
	@Get
	public String test()
	{
		System.out.println("TESTING SUCCESSFULL");
		return "TESTING SUCCESSFULL";
	}
	@Path(value = "/test1")
	@Get
	public void test1(@RequestParameter(name = "x") int x, @RequestParameter(name = "y") char y)
	{
		System.out.println("RESULT TEST 1 : "+x+" "+y);
	}
	@Path(value = "/test2")
	@Post
	public void test2(@RequestParameter(name = "student") Student student)
	{
		System.out.println("RESULT TEST 2 : "+student.getRollNumber()+" "+student.getName()+" "+student.getDateOfBirth());
	}
	@Path(value = "/test3")
	@Post
	public void test3(@RequestParameter(name = "x") int x, @RequestParameter(name = "y") char y)
	{
		System.out.println("RESULT TEST 3 : "+x+" "+y);
	}
	@Path(value = "/test4")
	@Post
	public void test4(@RequestParameter(name = "student") Student student)
	{
		System.out.println("RESULT TEST 4 : "+student.getRollNumber()+" "+student.getName()+" "+student.getDateOfBirth());
	}
}