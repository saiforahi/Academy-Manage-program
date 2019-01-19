package accounts;

import java.io.Serializable;

public class Collection implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String collectorName;
	private String collectionDate;
	private String collectionTime;
	private String collectionPurpose;
	private int collectionAmount;
	private String collectionNo;
	public Collection()
	{
		collectorName=new String();
		collectionDate=new String();
		collectionTime=new String();
		collectionPurpose=new String();
		collectionAmount=0;
		collectionNo=null;
	}
	
	public Collection(String givenNo,String givenUserName,String givenDate,String givenTime,String givenPurpose,int amount)
	{
		collectorName=givenUserName;
		collectionDate=givenDate;
		collectionTime=givenTime;
		collectionPurpose=givenPurpose;
		collectionAmount=amount;
		collectionNo=givenNo;
	}
	
	public String get_collectorName()
	{
		return collectorName;
	}
	public String get_collectionDate()
	{
		return collectionDate;
	}
	public String get_collectionTime()
	{
		return collectionTime;
	}
	public String get_collectionSource()
	{
		return collectionPurpose;
	}
	public int get_collectionAmount()
	{
		return collectionAmount;
	}
	public String get_collectionNo()
	{
		return collectionNo;
	}
}
