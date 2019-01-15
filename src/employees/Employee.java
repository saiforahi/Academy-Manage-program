package employees;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.Icon;

import accounts.Payment;

public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String cellNumber,email;
	private String NID,passportNo;
	private String designation;
	private String fatherName,motherName,spouseName;
	private String presentAddress,permanentAddress;
	private int salaryPerMonth;
	private String joiningDate;
	private Vector<Payment> payments;
	private String ID;
	private Icon picture;
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
		payments=new Vector<Payment>();
		ID=new String();
		joiningDate=null;
		picture=null;
	}
	
	public Employee(String givenID,Icon givenIcon,String givenName,String givenCell,String givenEmail,String givenNID,String givenPassportNo,String givenDesignation,String givenFatherName,String givenMotherName,String givenSpouseName,String givenPresentAddress,String givenPermanentAddress,int givenSalaryPerMonth,String givenJoiningDate )
	{
		ID=givenID;
		picture=givenIcon;
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
		payments=new Vector<Payment>();
		joiningDate=givenJoiningDate;
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
	public String get_spouseName()
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
	public void add_payment(Payment newPayment)
	{
		payments.add(newPayment);
	}
	
	public String get_ID()
	{
		return ID;
	}
	public String get_joiningDate()
	{
		return joiningDate;
	}
	public Icon get_picture()
	{
		return picture;
	}
	public Vector<Payment> get_payments()
	{
		return payments;
	}
}
