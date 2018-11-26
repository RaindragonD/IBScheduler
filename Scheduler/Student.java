
public class Student
{
    private String name;
    private String[][] courseList = new String[6][5];
    
    public Student(String name){
        this.name=name;
    }
    
    public String getName(){
        return name;
    }
    
    public String[][] getCourseList(){
        return courseList;
    }
            
    public void addCourse(String newCourse, String level, int i){
        courseList[i][0]= newCourse;
        courseList[i][1]= level;
    }
    
    //Maximum Class Number
    public void setCN(int number, int i){
        courseList[i][2]=""+number;
    }
    
    //Teacher Number
    public void setTN(int number, int i){
        courseList[i][3]=""+number;
    }
    
    //Class Size
    public void setCS(int number, int i){
        courseList[i][4]=""+number;
    }        
    
    public String toString(){
        String info = "Name: "+ name + "\nCourse List:";
        for (int i=0;i<courseList.length;i++){
            info += " "+courseList[i][0]+" "+courseList[i][1];
        }
        return info;
    }
}
