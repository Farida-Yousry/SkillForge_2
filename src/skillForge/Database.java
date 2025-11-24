package skillForge;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Database {
	private final static File file1 =new File( "course.json");
	private final static File file2 =new File( "user.json");
	
	public  static ArrayList<Course> loadCourses() {
		try {
			BufferedReader read = new BufferedReader(new FileReader(file1));
			Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy, hh:mm:ss a").create();
			return gson.fromJson(read,new TypeToken<ArrayList<Course>>() {}.getType());

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return new ArrayList();
	}
	
	public  static ArrayList<User> loadUsers() {
		ArrayList<User> users=new ArrayList<>();
		try {
			BufferedReader read = new BufferedReader(new FileReader(file2));
			Gson gson = new Gson();
			JsonObject   jSon=gson.fromJson(new FileReader(file2), JsonObject.class);
			JsonArray arr= jSon.getAsJsonArray("users");
			for(JsonElement e:arr) {
				JsonObject ob=e.getAsJsonObject();
				String role=ob.get("role").getAsString();
				if(role.equals("Student")) {
					users.add(gson.fromJson(ob, Student.class));
				}
            if(role.equals("Instructor")) {
            	users.add(gson.fromJson(ob, Instructor.class));
				}
            if(role.equals("Admin")) {
            	users.add(gson.fromJson(ob, Admin.class));
			}
			}
			
			
			return users;

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return users;
	}
	public static void saveToCourseFile(ArrayList<Course> courses) {
		try(FileWriter write = new FileWriter(file1)){
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(courses,write);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void saveToUsersFile(ArrayList<User> users) {
		try(FileWriter write = new FileWriter(file2)){
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonObject   jSon=new JsonObject();
			JsonArray arr=new JsonArray();
			for(User u:users) {
				arr.add(gson.toJsonTree(u,u.getClass()));
			}
		jSon.add("users", arr);
			gson.toJson(jSon,write);
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