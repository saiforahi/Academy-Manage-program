package registration;

import java.util.Vector;

import javax.swing.Icon;

import com.sun.prism.Image;

public class Learner {
	
	private String name,cell,email,address;
	private String courseName;
	private Vector<Installment> installments;
	private Image image;
	public Learner()
	{
		
	}
	public Learner(Icon givenIcon,String givenName,String givenCell,String givenEmail,String givenAddress,String givenCourseName)
	{
		name=givenName;
		cell=givenCell;
		email=givenEmail;
		address=givenAddress;
		courseName=givenCourseName;
		installments=new Vector<Installment>();
		image=(Image) givenIcon;
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
	public Image get_picture()
	{
		return image;
	}
	
}
