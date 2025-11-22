package skillForge;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class UserManager extends Validations {
	private ArrayList<User> users;
	private Database db;

	public UserManager(Database db) {
		this.db = db;
		this.users = db.loadUsers();
		if(this.users==null) {
			this.users=new ArrayList<>();
			}
	}
	public boolean signup(String userName,String password,int age,String role,String email) {
		if(!validateEmail(email)) {
			 JOptionPane.showMessageDialog(null,"Invalid Email format");
		return false;}	
		else if(!validateUserName(userName)) {
			JOptionPane.showMessageDialog(null,"Invalid Username format[Please Enter at least 5 characters]");
		return false;}
		else if(!validatePassword(password)) {
			JOptionPane.showMessageDialog(null,"Invalid Password format");
		return false;}
	else if(!validateAge(age)) {
			JOptionPane.showMessageDialog(null,"Invalid age");
			return false;}
		for(User e:users) {
			if(e.getUserName().equalsIgnoreCase(userName) && e.getEmail().equalsIgnoreCase(email)) {
				JOptionPane.showMessageDialog(null,"User already exists");
				return false;
			}
		}
		User user = new User(userName.trim(),hashPassword(password),email.trim(),generateNewId(role),role,age);
		users.add(user);
		db.saveToUsersFile(users);
		
		JOptionPane.showMessageDialog(null,"Account Created Successfully");
return true;
	}
	public User login(String userName,String password,String role)  {
		String hashed = hashPassword(password);
		for(User user : users) {
				if(user.getUserName().equalsIgnoreCase(userName) && user.getRole().equalsIgnoreCase(role)) {

					if(hashed.equals(user.getPassword()))
						return user;
				}
			}
		
		return null;

}
	public void logout(User user) {
		JOptionPane.showMessageDialog(null,user.getUserName()+" has logged out.");
	}
	public String hashPassword(String password) {
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
			
	}}
	public ArrayList<User> getAllUsers(){
		return users;
	}
	public String generateNewId(String role) {
		String id;
	
		if(role.equals("Student"))
			id = String.format("S%05d",System.currentTimeMillis()%100000);
		else if(role.equals("Instructor"))
			id = String.format("I%05d",System.currentTimeMillis()%100000);
		else
			id = String.format("A%05d",System.currentTimeMillis()%100000);
		for(int i=0;i<users.size();i++) {
			if(id.equals(users.get(i).getUserId()))
				return generateNewId(role);
		}
		 JOptionPane.showMessageDialog(null,"ID --> " + id);
		 return id;
		
	}
	}
