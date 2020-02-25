import java.io.*; 
import java.util.*; 
public class Student extends User implements Student_int,Serializable{ 
	private static Scanner input = new Scanner(System.in);
	private String id;
	Student(){}//constructor to use student method
	Student(String firstname,String lastname,String username,String password,String id){//constructor of student to create student obj
		this.firstname=firstname;
		this.lastname=lastname;
		this.username=username;
		this.password=password;
		this.id=id;
	}
	public String getfname() {return firstname;}//getters
	public String getlname() {return lastname;}
	public String getusername() {return username;}
	public String getpassword() {return password;}
	public String getid() {return id;}
	
	public void printstuinfo() {
		System.out.println(this.firstname+" "+this.lastname);
		System.out.println(this.id);
		System.out.println("");
	}//print general student info for Admin
	
	public static Student findstudent(ArrayList<Student> studentlist,String fname,String lname,String id) {
		for (Student i:studentlist){
			if (i.firstname.contentEquals(fname)&&i.lastname.contentEquals(lname)&&i.id.contentEquals(id))
				return i;
		}
		return null;
	} //returns the student obj by id and name
	
	//method overload
	public static Student login(ArrayList<Student> studentlist,String username,String password) {
		for (Student i:studentlist){
			if (i.username.contentEquals(username)&&i.password.contentEquals(password))
				return i;
		}
		return null;
	} //to identify student after logging in
	
	public ArrayList<Course> findstucourses(ArrayList<Course>courselist) {
		ArrayList<Course> stucourses=new ArrayList<Course>();
		for (Course i:courselist) {//go through all course in courselist
			for(Student j:i.stulist) {//for every course search through course stulist
				if (j.equals(this))
					stucourses.add(i);
			}			
		}
		return stucourses;
	}//retrieve all courses the student is in
	public void registerclass(Course c) {
		if (c.current==c.max) {
			System.out.println("Course full");
			System.out.println("");
		}
		else {
			c.stulist.add(this);
			c.current+=1;
		}	
	}//register a student into class 
	
	public void studentpage(ArrayList<Course>courselist,ArrayList<Student>Allstulist) {//student menu
		System.out.println("1.register course");
		System.out.println("2.withdraw from course");
		System.out.println("3.view all course");
		System.out.println("4.view available course");
		System.out.println("5.view student course");
		System.out.println("6.Exit");
		int admin_c=Integer.parseInt(input.next());
		switch(admin_c) {
		case 1://register in a course
			System.out.println("Course id:");
			String id=input.next();
			System.out.println("Section_num");
			int section_num=Integer.parseInt(input.next());
			Course c=Course.findcourse(courselist, id, section_num);//course of interest
			System.out.println("First name:");
			input.next();
			System.out.println("Last name:");
			input.next();
			this.registerclass(c);
			studentpage(courselist,Allstulist);//back
			break;
		case 2://withdraw from course
			System.out.println("Course id:");
			String id1=input.next();
			System.out.println("Section_num");
			int section_num1=Integer.parseInt(input.next());
			Course C=Course.findcourse(courselist, id1, section_num1);
			System.out.println("First name:");
			input.next();
			System.out.println("Last name:");
			input.next();
			this.withdraw(C);
			studentpage(courselist,Allstulist);
			break;
		case 3:	//view all course in courselist		
			User.printall(courselist);
			System.out.println("Exit?");
			input.next();
			studentpage(courselist,Allstulist);
			break;
		case 4://view courses that are not full 
			ArrayList<Course> notfull=Course.notfullList(courselist);//notfull is a separate ArrayList with not full course
			for (Course i:notfull) {
				i.printinfo();;
			}	
			System.out.println("Exit?");
			input.next();
			studentpage(courselist,Allstulist);
			break;
		case 5://view all course the student is registered in
			ArrayList<Course> stucourses=this.findstucourses(courselist);
	    	for (Course i:stucourses) {
	    		i.printinfo();
	    	}
	    	System.out.println("Exit?");
			input.next();
			studentpage(courselist,Allstulist);
			break;
		case 6://store info and end program
			Main.serialize(courselist, Allstulist);
			break;
		}			
	}
	
}
