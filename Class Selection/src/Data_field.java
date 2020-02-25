
import java.io.*; 
import java.util.*; 
public class Data_field implements Serializable{//for serializing both courselist and studentlist
	private ArrayList<Course> courselist;
	private ArrayList<Student> Allstulist;
	Data_field(ArrayList<Course>courselist,ArrayList<Student>Allstulist){
		this.courselist=courselist;
		this.Allstulist=Allstulist;
	}
	public ArrayList<Course> getcourselist(){//getters
		return courselist;
	}
	public ArrayList<Student> getstudentlist(){
		return Allstulist;
	}
}
