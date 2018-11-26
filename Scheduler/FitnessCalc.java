import java.util.ArrayList;
import java.util.HashMap;
public class FitnessCalc
{
    static int geneNumber;
    static int fitness1=0;
    static int fitness2=0;
    static int fitness3=0;
    
    public static int getFitness(Individual individual) {
        int fitness=0;
        
        fitness1=0;
        fitness2=0;
        fitness3=0;
        
        HashMap<Integer,ArrayList<Gene>> groups = individual.getGroups();
        geneNumber = Individual.genePool.length;
        
        for(int i=1;i<=6;i++){
            //traverse hashmap: a group
            ArrayList<Gene> group = groups.get(i);
            for(int j=0;j<group.size();j++){
                //traverse arraylist: a genen
                Gene gene = group.get(j);                
                int valid = 1;
                
                String name = gene.getName();
                boolean _1class1Group=true;
                
                String sub = gene.getSub();
                String lev = gene.getLev();
                boolean _1subject1Group=true;
                
                int courseSize = gene.getCS();
                int _classSize=0;
                //int test=group.size();
                
                /*
                int _classNum=0;
                int maxCN = gene.getMaxCN();
                boolean _goodClassSize=true;
                */
               
                for(int k=0;k<group.size();k++){
                    if(k!=j){
                        if(_1class1Group){
                            if(group.get(k).getName().equals(name)){
                                _1class1Group=false;
                            }
                        }                                            
                        if(_1subject1Group){
                            if(group.get(k).getSub().equals(sub)&&
                                    !group.get(k).getLev().equals(lev)){
                                _1subject1Group=false;
                            }
                        }                        
                    }                    
                    if(group.get(k).getSub().equals(sub)&&
                            group.get(k).getLev().equals(lev)){
                            _classSize++;
                    }
                }
                                
                /* STUDENT:the student does not have 
                 * other classes in the group;
                 */  
                if(_1class1Group){fitness1+=1;}
                else{valid*=2;}
                              
                /* TEACHER:if course has only one teacher, 
                 * the student should not have a classmate with the same 
                 * subject but different level in the same group;
                 */
                int teacherN=gene.getTN();                
                if (teacherN==1){
                   if(_1subject1Group){fitness2+=1;}
                   else{valid*=3;}
                }
                else{fitness2+=1;}
                                 
                /*CLASS SIZE
                 * Student has more than 5, less than 15 classmates
                 * If Course has less than 5 students, 
                 * student has all the classmates in one group;
                */                  
                if(courseSize<=6){
                    if(_classSize==courseSize){
                        fitness3+=1;}
                    else{valid*=4;}
                }
                else if(courseSize>6&&courseSize<=20){
                    if(_classSize>3){
                        fitness3+=1;}
                    else{valid*=4;}
                }
                else{
                    if(_classSize> 6){fitness3+=1;}
                    else{valid*=4;}
                }
                
                gene.setValidity(valid);                
            }
        }
        fitness = fitness1+fitness2+fitness3;
        individual.setFitArr(fitness1,fitness2,fitness3);        //test
        return fitness;
    }
    
    static int getMaxFitness(){
        return geneNumber*3;
    }
}
