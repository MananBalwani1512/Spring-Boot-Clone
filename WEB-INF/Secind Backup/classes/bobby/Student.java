package bobby;
import com.thinking.machine.webRock.annotations.*;
import com.thinking.machine.webRock.pojo.*;
@Path(Path="")
public class Student
{
    @Path(Path="")
    public void getDate(@AutoWire ApplicationScope application)
    {
        System.out.println("GET DATE METHOD CALLED");
        application.setAttribute("Date","15th December 2003");
    }    
}