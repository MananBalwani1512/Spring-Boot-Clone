package Student.dl;
public class StudentDTO
{
    private int roll;
    private String name;
    private String gender;
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public int getRoll()
    {
        return this.roll;
    }
    public void setRoll(int roll)
    {
        this.roll = roll;
    }
    public String getGender()
    {
        return this.gender;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }
}
