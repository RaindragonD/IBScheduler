import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;
import javax.swing.*;

public class Individual
{
    private static Student[] students;
    private static Teacher[] teachers;
    private static Course[] courses;
    protected static Gene[] genePool;
    private HashMap<Integer,ArrayList<Gene>> groups = new HashMap<>(6);
    private static HashMap<String,ArrayList<Gene>> oneClassCourse = new HashMap<>();
    private int fitness=0;
    protected int[] fitArr = new int[3];
    
    public void setFitArr(int i, int j, int k){
        fitArr[0]=i;
        fitArr[1]=j;
        fitArr[2]=k;
    }
    
    public Course[] getCourseList(){
        return courses;
    }
    
    public Teacher[] getTeacherList(){
        return teachers;
    }
    
    public Student[] getStuList(){
        return students;
    }
    
    public HashMap<Integer,ArrayList<Gene>> getGroups(){
        return groups;
    }
    
    public void generateIndividual(){
        for(int i=0; i<genePool.length;i++){
           int group = (int)Math.floor(5*Math.random())+1;
           groups.get(group).add(genePool[i]);                      
        }
    }
    
    public Gene getGene(int index){
        return genePool[index];
    }
    public void setGene(int index, Gene value){
        genePool[index]=value;
    }

    public int getSize(){
        return genePool.length;
    }

    public int getFitness(){
        if(fitness==0){
            fitness=FitnessCalc.getFitness(this);
        }
        return fitness;
    }
    
    public void evolve(){
        for(int i=1;i<=5;i++){
            //traverse hashmap: a group
            ArrayList<Gene> group = groups.get(i);            
            for(int j=0;j<group.size();j++){
                //traverse arraylist: a gene
                Gene gene = group.get(j);                               
                String name = gene.getName();                
                String sub = gene.getSub();
                String lev = gene.getLev();                
                //int courseSize = gene.getCS();
                int _classSize=0;

                for(int k=0;k<group.size();k++){                                     
                    if(group.get(k).getSub().equals(sub)&&
                            group.get(k).getLev().equals(lev)){
                            _classSize++;
                    }
                }
                if(_classSize==1){
                outerloop:
                for(int p=1;p<=5;p++){                                     
                    if(p!=i){
                    ArrayList<Gene> _group = groups.get(p);
                    for(int q=0;q<_group.size();q++){
                        Gene _gene = _group.get(q);
                        if(_gene.getName().equals(name)){
                            group.remove(gene);
                            _group.remove(_gene);
                            group.add(_gene);
                            _group.add(gene);
                            int newFit = FitnessCalc.getFitness(this);
                            if(newFit>fitness){
                                fitness=newFit;
                                break outerloop;                                
                            }
                            else{
                                group.remove(_gene);
                                _group.remove(gene);
                                group.add(gene);
                                _group.add(_gene);
                            }
                            break;
                        }
                    }}
                }}
            }
        }
    }
    
    public void preProcess(){
        //Find courses that can only have one class
        for(int k=0;k<courses.length;k++){
            Course cos = courses[k];
            int maxCN = cos.getCN();
            if(maxCN==1){
                String tag = cos.getSubject()+" "+cos.getLevel();
                oneClassCourse.put(tag,new ArrayList<Gene>());
            }
        }
        
        //initiate six arraylist for each group
        for (int j=1;j<7;j++){
            groups.put(j,new ArrayList<Gene>());}
            
        //see if the course of the gene can only have one class
        for(int i=0;i<genePool.length;i++){            
            Gene ge = genePool[i];            
            int maxCN = ge.getMaxCN();
            String sub = ge.getSub();
            if(sub.equals("Eng A") || sub.equals("Eng B") ){
                ge.setCertain();
                groups.get(6).add(ge);
            }
            else if(maxCN==1){
               String lev = ge.getLev();
               oneClassCourse.get(sub+" "+lev).add(ge);
            }
        }
                        
        //put genes of one-class courses into groups first    
        for(int k=0;k<courses.length;k++){
            Course cos = courses[k];
            int maxCN = cos.getCN();
            if(maxCN==1){
                String tag = cos.getSubject()+" "+cos.getLevel();
                ArrayList<Gene> ges1 = oneClassCourse.get(tag);
                //Check each group
                for(int q=1;q<=5;q++){
                    ArrayList<Gene> ges2 = groups.get(q);
                    if(ges2.size()<genePool.length*0.7){
                    boolean groupFound = true;                    
                    innerloop:
                    //each gene in one-class course list
                    for(int p=0;p<ges1.size();p++){
                        Gene ge1 = ges1.get(p);
                        String name1 = ge1.getName();
                        String sub1 = ge1.getSub();
                        //each gene in group list
                        for(int f=0;f<ges2.size();f++){
                            Gene ge2 = ges2.get(f);
                            String name2 = ge2.getName();
                            String sub2 = ge2.getSub();
                            if((name1.equals(name2))||
                                (sub1.equals(sub2)&&ge1.getTN()==1)){                                
                                groupFound=false;
                                break innerloop;
                            }                            
                        }
                    }
                    if(groupFound){
                        for(int p=0;p<ges1.size();p++){                           
                            Gene ge1 = ges1.get(p);
                            ge1.setCertain();
                            ges2.add(ge1);
                        }
                        break;
                    }
                    }
                }
            }
        }
        /*
        for (int i = 0;i<5;i++){
            certain[i]=groups.get(i+2).size();
        }
        */
    }
    
    public static void initiateGenePool(){
        genePool=new Gene[students.length*6];
        for (int i=0; i<students.length;i++){
            Student stu = students[i];
            String name = stu.getName();
            String[][] courseList = stu.getCourseList();
            for(int j=0;j<6;j++){
                String sub = courseList[j][0];
                String lev = courseList[j][1];
                int maxCN = 0;
                try{
                       maxCN = Integer.parseInt(courseList[j][2]); 
                }
                catch(Exception e){
                        error("Make sure that all the information in Course file " +
                        "is inputted.");
                        System.exit(0);
                }
                int TN = Integer.parseInt(courseList[j][3]);
                int CS = Integer.parseInt(courseList[j][4]);
                Gene newGene = new Gene(name,sub,lev,maxCN, TN,CS);
                genePool[i*6+j]=newGene;                                
            }
        }
    }
    
    //exception handling
    private static void error(String message){
        JOptionPane.showMessageDialog(null, 
        "Error!\n"+message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    //input student information
    public static void inputStudent(String filePath){        
        try{
            Scanner scan = new Scanner(new File (filePath));
            scan.useDelimiter("\n");  
            String info, name;
            String[] infoSplit;
            int stuID=0;
            scan.nextLine(); scan.nextLine(); 
            scan.nextLine(); scan.nextLine();// Instruction line
            
            //Initiate students array
            info = scan.nextLine();
            infoSplit = info.split(",");           
            int number = 0;
            try{
                  number = Integer.parseInt(infoSplit[1]);;
            }
            catch(Exception e){
                  error("Make sure that the Number of Students is inputted "+ 
                  "correctly in the format of number.");
                  System.exit(0);
            }     
            
            students=new Student[number];
            
            scan.nextLine(); // Table Head
            //input sample
            //David,EngA,SL, Math,HL, Chi,SL, Phy,HL,CS,HL,Econ,SL
            //create Student objects 
            //Student has two dimentional array
             while(scan.hasNext()){                  
                  info = scan.nextLine();
                  infoSplit = info.split(",");
                  name = infoSplit[0];
                  Student stu = new Student(name);
                  int classID=0;
                  String sub="", lev="";                  
                  for(int i=1;i<13;i+=2){
                    try{
                        sub=infoSplit[i];
                        lev=infoSplit[i+1];
                    }
                    catch(Exception e){
                        error("Make sure that all the information in Student file " +
                        "is inputted.");
                        System.exit(0);
                    }
                    stu.addCourse(sub,lev,classID);
                    classID++;
                  }                  
                  students[stuID]=stu;
                  stuID++;
             }
             scan.close();
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
            System.out.println("No Such Element");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File Not Found");
        }     
    }
    
    public static void inputTeacher(String filePath){        
        try{
            Scanner scan = new Scanner(new File (filePath));
            scan.useDelimiter("\n");            
            String info="", name="", subject="";
            String[] infoSplit;
            int teacherID=0;
            scan.nextLine(); scan.nextLine();
            scan.nextLine(); // Instruction line
            
            //Initiate teachers array
            info = scan.nextLine();
            infoSplit = info.split(",");
            int number = 0;
            try{
                  number = Integer.parseInt(infoSplit[1]);;
            }
            catch(Exception e){
                  error("Make sure that the Number of Teachers is inputted "+ 
                  "correctly in the format of number.");
                  System.exit(0);
            }     
            teachers=new Teacher[number];
                        
            scan.nextLine(); // Table Head         
            //input sample
            //Mr. Pete, CS, 2
            //create Teacher objects 
             while(scan.hasNext()){
                  info = scan.nextLine();
                  infoSplit = info.split(",");
                  try{
                        name = infoSplit[0];
                        subject = infoSplit[1];
                  }
                  catch(Exception e){
                        error("Make sure that all the information in Teacher file " +
                        "is inputted.");
                        System.exit(0);
                  }
                  
                  //maxClass = Integer.parseInt(infoSplit[2]);
                  Teacher tea = new Teacher(name, subject); //maxClass);
                  
                  teachers[teacherID]=tea;
                  teacherID++;
             }
             scan.close();
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
            System.out.println("No Such Element");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File Not Found");
        }     
    }
    
    public static void inputCourse(String filePath){        
        try{
            Scanner scan = new Scanner(new File (filePath));
            scan.useDelimiter("\n");
            String info="", subject="", level="";
            String[] infoSplit;
            int CN=0,courseID=0;
            scan.nextLine(); scan.nextLine(); 
            scan.nextLine(); scan.nextLine();// Instruction line
            
            //Initiate courses array
            info = scan.nextLine();
            infoSplit = info.split(",");
            int number = 0;
            try{
                  number = Integer.parseInt(infoSplit[1]);;
            }
            catch(Exception e){
                  error("Make sure that the Number of Courses is inputted "+ 
                  "correctly in the format of number.");
                  System.exit(0);
            }     
            courses=new Course[number];
            
            scan.nextLine(); // Table Head
            //input sample
            //CS, HL, 1
            //create course objects 
             while(scan.hasNext()){
                  info = scan.nextLine();
                  infoSplit = info.split(",");
                  subject = infoSplit[0];
                  level = infoSplit[1];                  
                  try{
                      CN = Integer.parseInt(infoSplit[2]);
                  }
                  catch(Exception e){
                      error("Make sure that Maximum Class Numbers are "+ 
                      "inputted correctly in the format of number.");
                      System.exit(0);
                  }                   
                  Course cos = new Course(subject, level, CN);
                  
                  //Add students to course
                  for (Student stu: students){
                      if(stu==null){
                          error("Make sure that the Number of Students is correct.");
                          System.exit(0);
                      }
                      String[][] courseList= stu.getCourseList(); 
                      for (int i=0;i<courseList.length;i++){
                          String sub = courseList[i][0];
                          String lev = courseList[i][1];
                          if (sub.equals(subject)&&lev.equals(level)){
                              cos.addStudent(stu);                              
                              break;
                          }                          
                      }              
                  }
                  
                  //Add teachers to course
                  for (Teacher tea: teachers){
                      if(tea==null){
                          error("Make sure that the Number of Teachers is correct.");
                          System.exit(0);
                      }
                      if (tea.getSubject().equals(subject)){
                          cos.addTeacher(tea);
                      }
                  }
                  
                  try{
                      courses[courseID]=cos;
                  }
                  catch(Exception e){
                      error("Make sure that the total Class Numbers is "+ 
                      "inputted correctly and no course is ommited.");
                      System.exit(0);
                  }         
                  
                  courseID++;
             }
             
             for(int i=0;i<courses.length;i++){
                  Course cos = courses[i];
                  if(cos==null){
                      error("Make sure that the Number of Courses is correct.");
                      System.exit(0);
                  }
                  subject = cos.getSubject();
                  level = cos.getLevel();
                  for (Student stu: students){
                      String[][] courseList= stu.getCourseList(); 
                      for (int j=0;j<courseList.length;j++){
                          String sub = courseList[j][0];
                          String lev = courseList[j][1];
                          if (sub.equals(subject)&&lev.equals(level)){
                              stu.setCN(cos.getCN(),j);
                              stu.setTN(cos.getTeacherList().size(),j);
                              stu.setCS(cos.getStuList().size(),j);
                              break;
                          }                          
                      }              
                  }            
             }
             initiateGenePool();
             scan.close();
        }        
        catch(NoSuchElementException e){
            e.printStackTrace();
            System.out.println("No Such Element");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File Not Found");
        }     
    }
}

class Gene{
    String stuName;
    String[] course = new String[2];    
    int courseMaxCN;
    int teacherN;
    int courseSize;
    boolean certain = false;
    int valid;
    
    public Gene(String name, String sub, String level, int CN,int tcN,int CS){
        stuName = name;
        course[0]=sub;
        course[1]=level;
        courseMaxCN = CN;
        teacherN=tcN;
        courseSize=CS;
    }
    
    public String getName(){
        return stuName;
    }
    
    public String getSub(){
        return course[0];
    }
    
    public String getLev(){
        return course[1];
    }
    
    public int getMaxCN(){
        return courseMaxCN;
    }
    
    public int getTN(){
        return teacherN;
    }
    
    public int getCS(){
        return courseSize;
    }
    
    public boolean getCertainty(){
        return certain;
    }
    
     public int getValidity(){
        return valid;
    }
    
    public void setCertain(){
        certain = true;
    }
    
    public void setValidity(int validity){
        valid = validity;
    }
}
