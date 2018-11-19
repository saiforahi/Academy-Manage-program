package classes;

import java.io.Serializable;
import java.util.Date;

public class License implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String cell;
	String code;
	String adminPassword;
	Date expiryDate;
	public License(){
		name="LabSymbiotic";
		cell="01737552558";
		code="1133";
		adminPassword="cill2018";
	}
}
