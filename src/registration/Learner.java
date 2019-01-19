package registration;

import java.io.Serializable;
import java.util.Vector;
import javax.swing.Icon;

public class Learner implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name,cell,email,address;
	private String courseName;
	private int skimTotal,downPayment,dueAmount;
	private Vector<Installment> installments;
	private Icon picture;
	private String report;
	public Learner()
	{
		name=new String();
		cell=new String();
		email=new String();
		address=new String();
		courseName=new String();
		installments=new Vector<Installment>();
		skimTotal=0;
		downPayment=0;
		dueAmount=0;
		picture=null;
		report=new String();
	}
	public Learner(Icon givenIcon,String givenName,String givenCell,String givenEmail,String givenAddress,String givenCourseName,int skimAmount,int downPaymentAmount,String givenReport)
	{
		name=givenName;
		cell=givenCell;
		email=givenEmail;
		address=givenAddress;
		courseName=givenCourseName;
		installments=new Vector<Installment>();
		skimTotal=skimAmount;
		downPayment=downPaymentAmount;
		dueAmount=skimTotal-downPayment;
		picture=givenIcon;
		report = givenReport;
	}
	public void add_installment(Installment newInstallment)
	{
		installments.add(newInstallment);
	}
	public String get_email()
	{
		return email;
	}
	public String get_name()
	{
		return name;
	}
	public String get_cell()
	{
		return cell;
	}
	public String get_address()
	{
		return address;
	}
	public String get_courseName()
	{
		return courseName;
	}
	
	public int get_skimTotal()
	{
		return skimTotal;
	}
	public int get_downPayment()
	{
		return downPayment;
	}
	public Vector<Installment> get_Installments()
	{
		return installments;
	}
	public Icon get_picture()
	{
		return picture;
	}
	public int get_due()
	{
		return dueAmount;
	}
	public void set_due(int newValue)
	{
		dueAmount=newValue;
	}
	public String get_report()
	{
		return report;
	}
	public void set_report(String newReport)
	{
		report=newReport;
	}
	
	public void set_name(String newName)
	{
		name=newName;
	}
	public void set_cell(String newCell)
	{
		cell=newCell;
	}
	public void set_address(String newAddress)
	{
		address=newAddress;
	}
	public void set_courseName(String newcourseName)
	{
		courseName=newcourseName;
	}
	public void set_skimTotal(int newSkim)
	{
		skimTotal=newSkim;
	}
	public void set_downPayment(int newdownPayment)
	{
		downPayment=newdownPayment;
	}
	public void set_picture(Icon newPicture)
	{
		picture=newPicture;
	}
	public void erase_installments()
	{
		installments.clear();
	}
}
