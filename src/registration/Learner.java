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
	private int skimTotal,downPayment;
	private Vector<Installment> installments;
	private Icon picture;
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
	}
	public Learner(Icon givenIcon,String givenName,String givenCell,String givenEmail,String givenAddress,String givenCourseName,int skimAmount,int downPaymentAmount)
	{
		name=givenName;
		cell=givenCell;
		email=givenEmail;
		address=givenAddress;
		courseName=givenCourseName;
		installments=new Vector<Installment>();
		skimTotal=skimAmount;
		downPayment=downPaymentAmount;
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
}
