package classes;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import connections.sqlConnection;
import login.Animation;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
