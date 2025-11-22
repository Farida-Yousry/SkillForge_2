package skillForge;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Database {
	private final File file1 =new File( "course.json");
	private final File file2 =new File( "user.json");
	
	public  ArrayList<Course> loadCourses() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(file1));
			Gson gson = new Gson();
			return gson.fromJson(read,new TypeToken<ArrayList<Course>>() {}.getType());

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ArrayList();
	}
	
	public  ArrayList<User> loadUsers() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(file2));
			Gson gson = new Gson();
			return gson.fromJson(read,new TypeToken<ArrayList<User>>() {}.getType());

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ArrayList();
	}
	public void saveToCourseFile(ArrayList<Course> courses) {
		try(FileWriter write = new FileWriter(file1)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(courses,write);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void saveToUsersFile(ArrayList<User> users) {
		try(FileWriter write = new FileWriter(file2)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(users,write);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public User getUserById(String id) {
		ArrayList<User> users=loadUsers();
		for(User u:users) {
			if(u.getUserId().equals(id)) {
				return u;
			}
		}
		return null;
	}
}