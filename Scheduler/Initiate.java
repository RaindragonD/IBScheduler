import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Initiate{

    private int fittest = 0;
    private int popSize; 
    protected static String stuPath,teaPath,corPath;
    protected static boolean flag = true;
    protected static boolean confirmed = false;
    
    public static void main(String args[]){
        Thread gui = new Thread(new mainGUI());
        try{
            gui.start();
            gui.join();
        }
        catch(Exception e){
        }
        Initiate ini = new Initiate(50);        
        ini.runGA();
    }
    
    public Initiate(int popSize){
        clearFile();
        this.popSize = popSize;
        importData();
    }
    
    public void runGA(){
        // Create an initial population
        Population myPop = new Population(popSize, true);
        Processing gui = new Processing();
        
        // Evolve our population until we reach an optimum solution
        int generationCount = 0;        
        int unchangedCount = 0;
        
        while (myPop.getFittest().getFitness() < FitnessCalc.getMaxFitness()
            &&unchangedCount<80000 && flag) {
            //for console  
            if((generationCount%1000)==0){
                System.out.println(generationCount/1000+"k");
                System.out.println(myPop.getFittest().getFitness());          
                gui.reload((double)myPop.getFittest().getFitness()/
                    (double)FitnessCalc.getMaxFitness());
            }           
            
            generationCount++;
            myPop = Algorithm.evolvePopulation(myPop);
            if(myPop.getFittest().getFitness()==fittest){unchangedCount++;}
            else{unchangedCount=0;}
            fittest = myPop.getFittest().getFitness();               
        }
                
        Individual best = myPop.getFittest();
        best.evolve();
        int finalVal = FitnessCalc.getFitness(best);
        System.out.println(finalVal); 
        gui.reload((double)finalVal/
            (double)FitnessCalc.getMaxFitness());
        gui.stopInfo();
        
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        
        //for console
        best = myPop.getFittest();
        System.out.println(best.getFitness()
        +"\n1 "+best.fitArr[0]+" 2 "+best.fitArr[1]+" 3 "+best.fitArr[2]);  
        
        output(best);
    }
    
    private static void clearFile(){
        try{
            PrintWriter gpWri = new PrintWriter(new FileWriter("Result/Group.csv",false)); 
            PrintWriter clWri = new PrintWriter(new FileWriter("Result/Class.txt",false));
            PrintWriter coWri = new PrintWriter(new FileWriter("Result/Course.txt",false));
            gpWri.print("");
            clWri.print("");
            coWri.print("");
            clWri.close();gpWri.close();coWri.close();
        }
        catch(Exception e){
            System.err.print("Error");            
        }
    }
    
    private void importData(){
        Individual.inputStudent(stuPath);
        Individual.inputTeacher(teaPath);
        Individual.inputCourse(corPath);
    }
    
    private void output(Individual indiv){
        HashMap<Integer,ArrayList<Gene>> groups = indiv.getGroups();
        int[] stuNumber = new int[6];
        for(int j=0;j<6;j++){
            stuNumber[j]=groups.get(j+1).size();
        }
        ArrayList<ArrayList<IBClass>> garr=intoGroups(groups,indiv);
        
         try{
              PrintWriter gpWri = new PrintWriter(new FileWriter("Result/Group.csv",false)); 
              PrintWriter clWri = new PrintWriter(new FileWriter("Result/Class.txt",false));
              PrintWriter coWri = new PrintWriter(new FileWriter("Result/Course.txt",false));    
              
              String userNote = 
                "*** The student have more than one class in a group\n"+
                "%%% The class size is not suitable\n" +
                "&&& Two classes of a subject are in the same group, "+
                "but only one teacher for the subject\n\n";
              clWri.append("The class information (class size, class ID, "+
                "and student names) classified according to groups.\n" +
                userNote);                
              coWri.append("The class information (class size, class ID, "+
                "and student names) classified according to courses.\n" +
                userNote);  
              for(int i=0;i<6;i++)
              {
                    //Table Head
                    gpWri.append("Group"+(i+1));
                    clWri.append("*************************\n");
                    clWri.append("GROUP"+(i+1)+"\n"); 
                    ArrayList<IBClass> clArr=garr.get(i);
                    clWri.append("TOTAL STUDENT NUMBER "+stuNumber[i]+"\n");
                    clWri.append("*************************\n");
                    //Content
                    for(int j=0;j<clArr.size();j++){
                        gpWri.append(",");
                        IBClass cl = clArr.get(j);
                        gpWri.append(cl.tostring()); 
                        
                        //Class.txt
                        clWri.append(cl.toString());
                        clWri.append("\n");
                    }
                    gpWri.append("\n");
                    clWri.append("\n\n");
              }                            
              gpWri.close();
              clWri.close();                           
                           
              //Course.txt
              Course[] coList= indiv.getCourseList();
              for(int i=0;i<coList.length;i++){
                  coWri.append(coList[i].toString());
                  coWri.append("\n\n");
              }  
              coWri.close();
              System.out.println("Group and class files exported.");  
        }
        catch(NoSuchElementException e){
            e.printStackTrace();
            System.out.println("No Such Element");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("File Not Found");
        }     
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }               
    }
    
    private ArrayList<ArrayList<IBClass>> 
        intoGroups(HashMap<Integer,ArrayList<Gene>> groups, Individual indiv){
        ArrayList<IBClass> g1 = new ArrayList<IBClass>();
        ArrayList<IBClass> g2 = new ArrayList<IBClass>();
        ArrayList<IBClass> g3 = new ArrayList<IBClass>();
        ArrayList<IBClass> g4 = new ArrayList<IBClass>();
        ArrayList<IBClass> g5 = new ArrayList<IBClass>();
        ArrayList<IBClass> g6 = new ArrayList<IBClass>();
        ArrayList<ArrayList<IBClass>> garr= new ArrayList<ArrayList<IBClass>>();
        garr.add(g1);garr.add(g2);garr.add(g3);
        garr.add(g4);garr.add(g5);garr.add(g6);
        
        Course[] courses = indiv.getCourseList();
        String validity = "";
        
        for(int i=1;i<7;i++){
            //traverse hashmap: a group
            ArrayList<Gene> group = groups.get(i);
            while(!group.isEmpty()){
                Gene gene = group.get(0);
                String sub = gene.getSub();
                String lev=gene.getLev();
                IBClass newClass = new IBClass(sub,lev,i);
                
                for(int j=0;j<group.size();j++){
                    Gene g = group.get(j);                    
                    String s= g.getSub();
                    String l= g.getLev();
                    if(s.equals(sub)&&l.equals(lev)){
                        int valid = g.getValidity();
                        switch(valid){
                            case 1: validity = ""; break;
                            case 2: validity = "***"; break;
                            case 3: validity = "&&&"; break;
                            case 4: validity = "%%%"; break;
                            case 6: validity = "***&&&"; break;
                            case 8: validity = "***%%%"; break;
                            case 12: validity = "&&&%%%"; break;
                            case 24: validity = "***&&&%%%"; break;
                            default: validity = "";
                        }
                        String n = g.getName();
                        newClass.addStudent(validity+n);
                        group.remove(g);
                        j--;
                    }
                }
                for(int k=0;k<courses.length;k++){
                    Course cos = courses[k];
                    if(sub.equals(cos.getSubject())&&lev.equals(cos.getLevel())){
                        cos.addClass(newClass);
                    }
                }
                garr.get(i-1).add(newClass);
            }    
        }
        return garr;
    }
}