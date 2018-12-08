package employees;

public class Salary {
	private String monthName;
	private int amount;
	
	public Salary()
	{
		monthName=new String();
		amount=0;
	}
	public Salary(String givenMonth,int givenAmount)
	{
		monthName=givenMonth;
		amount=givenAmount;
	}
	
	public String get_monthName()
	{
		return monthName;
	}
	
	public int get_amount()
	{
		return amount;
	}
	
	public void set_month(String newMonthName)
	{
		monthName=newMonthName;
	}
	public void set_amount(int newAmount)
	{
		amount=newAmount;
	}
}
