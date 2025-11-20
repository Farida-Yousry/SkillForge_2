import java.util.Scanner;


import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User{

	protected String userName;
	protected String password;
	protected String email;
	protected String userId;
	//protected String fullName;
	protected String role;
	protected int age;

	
	


	
	public User(String userName, String password, String email, String userId, String role, int age) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.userId = userId;
		this.role = role;
		this.age = age;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	/*public String hashPassword(String password) {
		try {
			MessageDigest pass = MessageDigest.getInstance("SHA-256");
			byte[] temp = pass.digest(password.getBytes());
			StringBuffer s = new StringBuffer();
			for(byte b : temp)
				s.append(String.format("%02x",b & 0xff));
			return s.toString();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			return null;
			
	}
	
}

	 public void logout() {
		 System.exit(0);
	 }
	public String generateNewId(String role) {
		String id;
		if(role.equals("Student"))
			id = String.format("S%05d",System.currentTimeMillis()%100000);
		else 
			id = String.format("I%05d",System.currentTimeMillis()%100000);
		
		this.userId = id;
		 JOptionPane.showMessageDialog(null,"ID --> " + id);
		 return id;
		
	}*/

}