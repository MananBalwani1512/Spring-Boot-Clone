package Student.service;
import java.util.*;
import java.sql.*;
import Student.dl.*;
import com.thinking.machine.webRock.annotations.*;
@Path(Path="/student")
public class Student
{
    @Path(Path="/getAll")
    public List<StudentDTO>getAll()
    {
        List<StudentDTO>students = new ArrayList();
        try
        {
            Connection connection = new connection().getConnection();
            PreparedStatement pstatement = connection.prepareStatement("select * from Student");
            ResultSet rs = pstatement.executeQuery();
            StudentDTO student = null;
            while(!rs.next())
            {
                student = new StudentDTO();
                student.setRoll(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setGender(rs.getString(3));
                students.add(student);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return students;
    }
    @Path(Path="/addStudent")
    public String addStudent(StudentDTO student)
    {
        String ans = "";
        try
        {
            Connection connection = new connection().getConnection();
            PreparedStatement pstatement = connection.prepareStatement("insert into Student values(?,?,?)");
            pstatement.setInt(1,student.getRoll());
            pstatement.setString(2,student.getName());
            pstatement.setString(3,student.getGender());
            pstatement.executeUpdate();
            ans = "Student Added Successfully";
        }
        catch(Exception e)
        {
            ans = "Cannot Add Student";
            e.printStackTrace();
        }
        return ans;
    }
    @Path(Path="/deleteStudent")
    public String deleteStudent(@RequestParameter(Name="roll")int roll)
    {
        String ans = "";
        try
        {
            Connection connection = new connection().getConnection();
            PreparedStatement pstatement = connection.prepareStatement("delete from Student where Roll=?");
            pstatement.setInt(1,roll);
            pstatement.executeUpdate();
            ans = "Student Deleted Successfully";
        }
        catch(Exception e)
        {
            ans = "Cannot Delete Student";
            e.printStackTrace();
        }
        return ans;
    }
}