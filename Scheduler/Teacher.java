
//import java.util.ArrayList;

public class Teacher
{
    private String name;
    private String subject;

    public Teacher(String name, String subject){ //int max){
        this.name=name;
        this.subject=subject;
    }
    
    public String getName(){
        return name;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public String toString(){
        String info = "Name: "+ name;
        return info;
    }
}
