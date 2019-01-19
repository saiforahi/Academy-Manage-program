package accounts;

import java.io.Serializable;

public class Payment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paidBy;
	private String date;
	private String time;
	private int amount;
	private String paidTo;
	private String purpose;
	
	public Payment()
	{
		paidBy=null;
		date=null;
		amount=0;
		time=null;
		paidTo=null;
		purpose=null;
	}
	
	public Payment(String givenPaidByName,String givenDate,String givenTime,int givenAmount,String givenPaidToName,String givenPurpose)
	{
		paidBy=givenPaidByName;
		date=givenDate;
		time=givenTime;
		amount=givenAmount;
		paidTo=givenPaidToName;
		purpose=givenPurpose;
	}
	
	public String get_paidBy_name()
	{
		return paidBy;
	}
	public String get_payment_date()
	{
		return date;
	}
	public String get_paidTo_name()
	{
		return paidTo;
	}
	public String get_purpose()
	{
		return purpose;
	}
	public int get_paid_amount()
	{
		return amount;
	}
	public String get_payment_time()
	{
		return time;
	}
}
