
import java.util.ArrayList;

public class IBClass
{
    private String subject;
    private String level;
    private int id;
    private int group;    
    private ArrayList<String> studentList= new ArrayList<String>();
    
    public IBClass(String sub, String lev,int group){
        this.group=group;
        subject = sub;
        level = lev;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public int getGroup(){
        return group;
    }
    
    public String getLevel(){
        return level;
    }
    
    public int getID(){
        return id;
    }
    
    public int getClassSize(){
        return studentList.size();  
    }
                
    public ArrayList<String> getStuList(){
        return studentList;
    }
    
    public void setID(int id){
        this.id=id;
    }
    
    public void addStudent(String aStudent){
        studentList.add(aStudent);
    }    
    
    public String tostring(){
        String info = subject+" "+ level + " "+id;
        return info;
    }
    
    public String toString(){
        String info = subject+" "+ level + " "+id
            +"\n\tClass Size "+studentList.size()
            +"\n\tGroup "+group
            +"\n\tStudent Info:";
        for (int i=0;i<studentList.size();i++){
            info += "\n\t\t"+studentList.get(i);
        }
        return info;
    }
}
