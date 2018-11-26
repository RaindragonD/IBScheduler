import java.util.ArrayList;

public class Course
{
    private String subject;
    private String level;
    private int maxClassNumber = 0;
    private int classNumber = 0;
    private ArrayList<IBClass> classList = new ArrayList<IBClass>();
    private ArrayList<Student> studentList=new ArrayList<Student>();
    private ArrayList<Teacher> teacherList=new ArrayList<Teacher>();    
    
    public Course (String subject, String level, int CN){
        this.subject = subject;
        this.level=level;
        maxClassNumber=CN;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public String getLevel(){
        return level;
    }
    
    public int getCN(){
        return maxClassNumber;
    }
    
    public ArrayList<IBClass> getClassList(){
        return classList;
    }
    
    public ArrayList<Teacher> getTeacherList(){
        return teacherList;
    }
    
    public ArrayList<Student> getStuList(){
        return studentList;
    }
    
    public void addClass(IBClass aClass){
        classNumber++;
        classList.add(aClass);
        aClass.setID(classNumber);
    }
            
    public void addStudent(Student aStudent){
        studentList.add(aStudent);
    }    
    
    public void addTeacher(Teacher aTeacher){
        teacherList.add(aTeacher);
    }
    
    public String toString(){
        String info = 
            "*************************\n"+
            subject+" "+ level + 
            "\n\tClass Number "+classNumber+
            "\n*************************";
        for (int i=0;i<classList.size();i++){
            info += "\n"+classList.get(i).toString();
        }
        return info;
    }
}
