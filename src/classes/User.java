package classes;

public class User {
	String name;
	String password;
	String type;
	
	public User()
	{
		name=null;
		password=null;
		type=null;
	}
	
	public User(String givenName,String givenPass, String choosenType)
	{
		name=givenName;
		password=givenPass;
		type=choosenType;
	}
	
	public String get_name()
	{
		return name;
	}
	
	public String get_password()
	{
		return password;
	}
	
	public String get_type()
	{
		return type;
	}

}
