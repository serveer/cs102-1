
import java.io.*; 
import java.util.*; 
public class Admin extends User implements Serializable,Admin_int{
	public ArrayList<Course> courselist=new ArrayList<Course>();
	public ArrayList<Student> Allstulist=new ArrayList<Student>();
	//Because serialize cannot deal with static, create list in Admin
	
	Admin(){}
	Admin(String username,String password){//takes in username and password parameter
		this.username=username;
		this.password=password;//both inherited from user
	}	
	public static void add_c(String name, String id,int max,int current, String instructor,
					int selection_num,String location,ArrayList<Course> courselist) {
		Course new_course=new Course(name,id,max,current, 
				instructor,selection_num,location);
		courselist.add(new_course);
	}//adding course

	public static void editnum(Course c,int option, int new_num) {
		if (option==1)
			c.max=new_num;
		else {
			if (option==4)
				c.section_num=new_num;
		}	
	}//edit course information that are of type int
	
	public static void editText(Course c,int option, String t) {
		if (option==3)
			c.instructor=t;
		else {
			if (option==5)
				c.location=t;
		}			
	}//edit course information that are of type string
	
	public Boolean login(String u,String p) {
		if (u.equals("Admin")&&p.equals("Admin001"))
			return true;
		else return false;
	}//login method inherited and override from user
    
	//admin's view all course, override user method because admin sees registered student info as well
	public static void printall(ArrayList <Course> courselist) {
    	for (Course i :courselist) {
    		i.printinfo();
    		System.out.println("Registered student:");
    		for (Student s:i.stulist) {//print individual student info in course student list
    			s.printstuinfo();
    		}
    		System.out.println("");
    	}		
    }
	
	//converting full course list to txt
	public static void fulltoFile(ArrayList <Course> courselist) {
		String fileName = "full.txt";
		
		try{
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (Course c:courselist) {
				bufferedWriter.write(c.getname()+""+c.getid());
				bufferedWriter.newLine();
			}					
//close writer
			bufferedWriter.close();
		}

		//Always close files

		catch (IOException exk) {
			System.out.println( "Error writing file '" + fileName + "'");
			exk.printStackTrace();
		}
	}
}
