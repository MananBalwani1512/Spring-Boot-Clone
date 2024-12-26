package bobby;
import com.springboot.clone.annotations.*;
import com.springboot.clone.pojo.*;
@InjectApplicationScope
public class eg3
{
	private ApplicationScope applicationScope;
	@OnStartup(priority = 1)
	public void getName()
	{
		applicationScope.setAttribute("TEST1","SUCCESSFULL");
		System.out.println("STARTUP METHOD 1");
	}
	@OnStartup(priority = 3)
	public void getClasses()
	{
		System.out.println("STARTUP METHOD 3");
	}
}