import java.io.*; 
import java.util.*; 

public class Main {
	private static Scanner input = new Scanner(System.in);
	public static void main(String [] args) {
		Admin a=new Admin("Admin","Admin001");//Create single admin obj and all courselist and stulist refers to it		
		String filename="MyUniversityCourses.csv";
		String line=null;		
		try {//try to locate ser file and deserialize
			FileInputStream fis = new FileInputStream("course+stu.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Data_field lists= (Data_field)ois.readObject();
			a.courselist=lists.getcourselist();
			a.Allstulist=lists.getstudentlist();
		    ois.close();
		    fis.close();
		}
		catch(ClassNotFoundException cnfe) {
		       cnfe.printStackTrace();
		       return;
		}catch(Exception e) {//if cannot deserialize read in csv
			try{
				FileReader fileReader=new FileReader(filename);
				BufferedReader bufferedReader=new BufferedReader(fileReader);
				bufferedReader.readLine();
				while ((line=bufferedReader.readLine())!=null) {
					a.courselist.add(loadCourse(line));
				}
				bufferedReader.close();
			}
			catch(IOException ex) {
				System.out.println("Error");
			}
		}
		
		if (loginpage()==1) {//Admin page
			if (adminlogin().equals(true))
				adminpage(a.courselist,a.Allstulist);
			else {
				System.out.println("Wrong Info");
				}
			}
			else {//student page
				System.out.println("Log in");
		    	System.out.println("username: ");
		    	String username = input.next();
		    	System.out.println("password: ");
		    	String password = input.next();
				Student s=Student.login(a.Allstulist, username, password);//find specific student obj
				studentpage(s,a.courselist,a.Allstulist);
			}		
		}
	private static Course loadCourse(String line) {
		String[] arr =line.split(",");
		String name=arr[0];
		String id=arr[1];
		int max=Integer.parseInt(arr[2]);
		int current=Integer.parseInt(arr[3]);
		String instructor=arr[5];
		int section_num=Integer.parseInt(arr[6]);
		String location=arr[7];
		Course c=new Course(name,id,max,current,instructor,section_num,location);
	    return c;
	}//converting individual line in csv to course obj
	
	public static int loginpage() {//Admin or student
		System.out.println("Choose number?");
		System.out.println("1.Admin");
		System.out.println("2.Student");
		return Integer.parseInt(input.next());
	}
	public static Boolean adminlogin() {//admin login
		System.out.println("Log in");
    	System.out.println("username: ");
    	String username = input.next();
    	System.out.println("password: ");
    	String password = input.next();
    	Admin ad=new Admin();
    	Boolean Login=ad.login(username,password);
    	return Login;
	}
	public static void adminpage(ArrayList<Course>courselist,ArrayList<Student>Allstulist) {
		System.out.println("1.add course");
		System.out.println("2.delete course");
		System.out.println("3.view all course");
		System.out.println("4.view full course");
		System.out.println("5.display/edit specific course(add student)");
		System.out.println("6.register new student");
		System.out.println("7.view student course");
		System.out.println("8.sort course");
		System.out.println("9.Exit");
		int admin_c=Integer.parseInt(input.next());//take in admin choice
		input.nextLine();
		switch(admin_c) {
		case 1://adding course, input all required number other than student list, automatic null list
			System.out.println("name: ");	
	    	String name = input.nextLine();
	    	System.out.println("id: ");
	    	String id = input.nextLine();
	    	System.out.println("max: ");
	    	String max_temp=input.nextLine();
	    	int max = Integer.parseInt(max_temp);
	    	System.out.println("instructor: ");
	    	String instructor = input.nextLine();
	    	System.out.println("section number: ");
	    	String section_temp=input.nextLine();	    	    	
	    	int section_num = Integer.parseInt(section_temp);
	    	System.out.println("location: ");
	    	String location = input.nextLine();
			Admin.add_c(name, id, max, 0, instructor, section_num, location,courselist);
			adminpage(courselist,Allstulist);
			break;
		case 2://delete course from course list
			System.out.println("id of course deleting:");
			String id1=input.next();
			System.out.println("section number of course deleting:");
			int section_num1=Integer.parseInt(input.next());			
			Course.findcourse(courselist, id1, section_num1).delete(courselist);
			adminpage(courselist,Allstulist);
			break;
		case 3:	//view all course in courselist		
			Admin.printall(courselist);
			System.out.println("Exit?");
			input.next();
			adminpage(courselist,Allstulist);
			break;
		case 4://print full course
			ArrayList<Course> fullcourse=Course.fullList(courselist);//fullcourse is a seperate ArrayList with full course
			for (Course i:fullcourse) {
				i.printinfo();;
			}			
			System.out.println("To file?(Y/N)");//ask if Admin wants to convert full list to txt file to view
			if(input.next().contentEquals("Y"));
				Admin.fulltoFile(fullcourse);
				System.out.println("complete!Exiting");
				System.out.println("");
			adminpage(courselist,Allstulist);
			break;
		case 5://Find specific course
			System.out.println("Course id:");
			String id2=input.next();
			System.out.println("Section_num");
			int section_num2=Integer.parseInt(input.next());
			Course selected=Course.findcourse(courselist, id2, section_num2);
			selected.printinfo();
    		System.out.println("Registered student:");
    		for (Student s:selected.stulist) {//print individual student info in course student list
    			s.printstuinfo();
    		}
    		System.out.println("");
			System.out.println("1 for Edit");//edit the specific course or exit
			System.out.println("any key for Exit");
			String edit=input.next();
			if (edit.contentEquals("1"))
				edit(selected,courselist,Allstulist);
			else {
				adminpage(courselist,Allstulist);
			}				
			break;
		case 6://add student obj/register student
			System.out.println("Student info");
			System.out.println("first name: ");	    			
	    	String fname = input.next();
	    	System.out.println("last name: ");
	    	String lname = input.next();
	    	System.out.println("id: ");
	    	String s_id = input.next();
	    	System.out.println("username: ");
	    	String s_username = input.next();
	    	System.out.println("password: ");
	    	String s_password = input.next();
			Student new_s=new Student(fname,lname,s_username,s_password,s_id);
			Allstulist.add(new_s);
			adminpage(courselist,Allstulist);
			break;
		case 7://find all course a student is registered in
			System.out.println("Student info");
			System.out.println("first name: ");	    			
	    	String fname1=input.next();
	    	System.out.println("last name: ");
	    	String lname1=input.next();
	    	System.out.println("id: ");
	    	String s_id1=input.next();
	    	ArrayList<Course> stucourses=Student.findstudent(Allstulist, fname1, lname1, s_id1).findstucourses(courselist);
	    	for (Course i:stucourses) {
	    		i.printinfo();
	    	}
	    	System.out.println("Exit?");
			input.next();
			adminpage(courselist,Allstulist);
			break;
		case 8://sort course by current student
			Collections.sort(courselist);
			System.out.println("Sorted!");
			adminpage(courselist,Allstulist);
			break;
		case 9://end program and store info
			serialize(courselist,Allstulist);
			break;
		}		
	}
	
	//edit method
	public static void edit(Course c,ArrayList<Course>courselist,ArrayList<Student>Allstulist) {
		System.out.println("Which one do you want to change");
		System.out.println("1.max");
    	System.out.println("2.add or remove student");
    	System.out.println("3.instructor");
    	System.out.println("4.section number");    	    	
    	System.out.println("5.location");
    	int edit_choice = Integer.parseInt(input.next());
    	if (edit_choice==1||edit_choice==4) {//edit int member variable
    		System.out.println("enter new number");
    		int new_num = Integer.parseInt(input.next());
    		Admin.editnum(c,edit_choice,new_num);
    	}else if(edit_choice==3||edit_choice==5){//edit string memeber variable
    		System.out.println("enter new string");
    		String new_text = input.next();
    		Admin.editText(c,edit_choice,new_text);
    	}
    	else {//add or remove student
    		System.out.println("Student info");
			System.out.println("first name: ");	    			
	    	String fname=input.next();
	    	System.out.println("last name: ");
	    	String lname=input.next();
	    	System.out.println("id: ");
	    	String s_id=input.next();
	    	Student stu=Student.findstudent(Allstulist, fname, lname, s_id);	    	
    		System.out.println("1.add student to class");
        	System.out.println("2.remove student from class");
        	int add_choice = Integer.parseInt(input.next());
        	if (add_choice==1) {
        		stu.registerclass(c);
        	}else {
        		stu.withdraw(c);
        	}
    	}	
    	adminpage(courselist,Allstulist);
	}

	public static void studentpage(Student s,ArrayList<Course>courselist,ArrayList<Student>Allstulist) {
		s.studentpage(courselist, Allstulist);//calling studentpage method in student class
	}
	
	public static void serialize(ArrayList<Course>courselist,ArrayList<Student>Allstulist) {
		try {
			//FileOutput Stream writes data to a file
			FileOutputStream fos = new FileOutputStream("course+stu.ser");
			
			//create data field
			Data_field lists=new Data_field(courselist,Allstulist);
			
			//ObjectOutputStream writes objects to a stream (A sequence of data)
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			//Writes the specific object to the OOS
			oos.writeObject(lists);
			
			//Close both streams
			oos.close();
			fos.close();
			System.out.println("Serialization complete");
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
