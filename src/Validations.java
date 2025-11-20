import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Validations {

	public boolean validateFullName(String fullName) {
		if(fullName == null)
			return false;
		else{
			String[] name = fullName.trim().split(" ");
			if(name.length < 3)
				return false;
			}
		return true;
	}
	public boolean validateAge(int age) {
		return (age > 0 );
	}
	public boolean validateEmail(String email) {
		if(email == null)
			return false;
		else if(!email.matches("^[^@]+@[^@]+$"))
			return false;
		else if(email.matches(".*@gmail\\.com") || email.matches(".*@yahoo\\.com") || email.matches("es.*@alexu\\.edu\\.eg"))
			 return true;
		 else 
			 return false;
	}
	public boolean validateUserName(String userName) {
		if(userName == null)
			return false;
		return (userName.length() >= 5);

	}
	public boolean validatePassword(String password) {
		if(password == null|| password.length()<5) return false;
			
		boolean hasDigit=false;
		for(int i=0;i<password.length();i++) {
			char x=password.charAt(i);
			if(x>='0'&& x<='9') {
				hasDigit=true;
				break;
			}
		}
		return hasDigit;
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
				
		}
		
	}
}
