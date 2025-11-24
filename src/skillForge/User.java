package skillForge;
import java.util.ArrayList;
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

	

}