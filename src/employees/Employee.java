package employees;

import java.util.Vector;

public class Employee {
	private String name;
	private String cellNumber,email;
	private String NID,passportNo;
	private String designation;
	private String fatherName,motherName,spouseName;
	private String presentAddress,permanentAddress;
	private int salaryPerMonth;
	private Vector<Salary> payments;
	
	public Employee()
	{
		name=null;
		cellNumber=null;
		email=null;
		NID=null;
		passportNo=null;
		designation=null;
		fatherName=null;
		motherName=null;
		spouseName=null;
		presentAddress=null;
		permanentAddress=null;
		salaryPerMonth=0;
		payments=new Vector<Salary>();
	}
	
	public Employee(String givenName,String givenCell,String givenEmail,String givenNID,String givenPassportNo,String givenDesignation,String givenFatherName,String givenMotherName,String givenSpouseName,String givenPresentAddress,String givenPermanentAddress,int givenSalaryPerMonth )
	{
		name=givenName;
		cellNumber=givenCell;
		email=givenEmail;
		NID=givenNID;
		passportNo=givenPassportNo;
		designation=givenDesignation;
		fatherName=givenFatherName;
		motherName=givenMotherName;
		spouseName=givenSpouseName;
		presentAddress=givenPresentAddress;
		permanentAddress=givenPermanentAddress;
		salaryPerMonth=givenSalaryPerMonth;
		payments=new Vector<Salary>();
	}
	public String get_name()
	{
		return name;
	}
	public void set_name(String newName)
	{
		name=newName;
	}
	public String get_cellNumber()
	{
		return cellNumber;
	}
	public String get_email()
	{
		return email;
	}
	public String get_NID()
	{
		return NID;
	}
	public String get_passportNo()
	{
		return passportNo;
	}
	public String get_designation()
	{
		return designation;
	}
	public String get_fatherName()
	{
		return fatherName;
	}
	public String get_motherName()
	{
		return motherName;
	}
	public String get_spuseName()
	{
		return spouseName;
	}
	public String get_presentAddress()
	{
		return presentAddress;
	}
	public String get_permanentAddress()
	{
		return permanentAddress;
	}
	public int get_salaryPerMonth()
	{
		return salaryPerMonth;
	}
	public void add_payment(Salary newPayment)
	{
		payments.add(newPayment);
	}
	
	
}
