package employees;

import java.io.Serializable;

public class Salary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private int amount;
	private String paidBy;
	public Salary()
	{
		date=new String();
		amount=0;
		paidBy=null;
	}
	public Salary(String givenDate,int givenAmount,String namePaidBy)
	{
		date=givenDate;
		amount=givenAmount;
		paidBy=namePaidBy;
	}
	
	public String get_date()
	{
		return date;
	}
	
	public int get_amount()
	{
		return amount;
	}
	
	public String get_paidBy()
	{
		return paidBy;
	}
}
