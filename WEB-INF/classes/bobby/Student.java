package bobby;
public class Student
{
	private int rollNumber;
	private String name;
	private String dateOfBirth;
	public void setRollNumber(int rollNumber)
	{
		this.rollNumber = rollNumber;
	}
	public int getRollNumber()
	{
		return this.rollNumber;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public void setDateOfBirth(String dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfBirth()
	{
		return this.dateOfBirth;
	}
}