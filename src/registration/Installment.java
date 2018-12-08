package registration;

public class Installment {
	String date;
	int amount;
	
	public Installment()
	{
		date=new String();
		amount=0;
	}
	
	public Installment(String givenDate,int givenAmount)
	{
		date=givenDate;
		amount=givenAmount;
	}
	
	public String get_date()
	{
		return date;
	}
	public int get_amount()
	{
		return amount;
	}
	
	public void set_date(String givenDate)
	{
		date=givenDate;
	}
	public void set_amount(int givenAmount)
	{
		amount=givenAmount;
	}
}
