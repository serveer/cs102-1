import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable{
	
	public String username, password, firstname, lastname;//both Admin and Student has username and password
	public Boolean login(String u,String p) {//both student and Admin have to login, so create login in parent class
		if (u.equals(this.username)&&p.equals(this.password))
			return true;
		else return false;
	}
	public void withdraw(Course c) {
		c.stulist.remove(this);
		c.current-=1;
	}//withdraw student
	
    public static void printall(ArrayList <Course> courselist) {
    	for (Course i :courselist) {
    		i.printinfo();
    		System.out.println("");
    	}		
    }//for veiw all course
}
