
import java.io.*; 
import java.util.*; 
public class Course implements Serializable,Comparable<Course>{
	public static ArrayList<Course> fullcourse=new ArrayList<Course>();//create new list to store all courses that are full
	public static ArrayList<Course> notfullcourse=new ArrayList<Course>();//create new list to store all courses that are not full
	private String name,id;//encapsulation to protect from messing up
	public String location,instructor;
	public int max,current,section_num;
	public int reg_stu;
	public ArrayList<Student> stulist;
	Course(){}
	Course(String name, String id,int max,int current,
			String instructor,int section_num,String location) {
		this.name=name;
		this.id=id;
		this.location=location;
		this.max=max;
		this.current=current;
		this.stulist=new ArrayList<Student>(); //initialize a new course student list for each new course obj created
		this.instructor=instructor;
		this.section_num=section_num;
	}//constructor with all course info
	
	public String getname() {return name;}//getters
	public String getid() {return id;}
	
	public static Course findcourse(ArrayList<Course> courselist,String id,int section_num) {
		for (Course i:courselist){
			if (i.getid().contentEquals(id)&&i.section_num==section_num)
				return i;
		}
		return null;
	} //returns the course obj by id and selection number			
	public void printinfo() {
		System.out.println("name:"+name);
		System.out.println("id:"+id);
		System.out.println("max number of student:"+max);
		System.out.println("current number of student:"+current);
		System.out.println("course instructor:"+instructor);
		System.out.println("section number:"+section_num);		
		System.out.println("location:"+location);
	}//print out relevant course info, able to use with multiple other methods such as printall and getinfo
	
	public static ArrayList<Course> fullList(ArrayList<Course> courselist) {
		for (Course i:courselist){
			if (i.current==i.max)//check if course is full
				fullcourse.add(i);
		}
	return fullcourse;
	}//create and return Arraylist of full course
	
	public static ArrayList<Course> notfullList(ArrayList<Course> courselist) {
		for (Course i:courselist){
			if (i.current<i.max)//check if course is not full
				notfullcourse.add(i);
		}
	return notfullcourse;
	}//create and return Arraylist of not full course
	
	public void delete(ArrayList<Course> courselist) {
		courselist.remove(this);
	}//remove the course obj from courselist
	
	//for sorting courses
	public int compareTo(Course compareto) {
		int comparecurrent=((Course)compareto).current;
		return comparecurrent-this.current;
	}
}
