

import java.io.Serializable;
import java.util.ArrayList;


public class Userz implements Serializable{
	
	String name;
	String username;
	String email;
	String pass;
        boolean isLogin;
	public ArrayList<String> interest ;
	
	public Userz(String name, String username, String email, String pass,ArrayList i) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.username = username;
		this.email = email;
		this.pass = pass;
		this.interest = i ;
	
	}
}
