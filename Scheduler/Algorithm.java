import java.util.ArrayList;
import java.util.HashMap;
public class Algorithm {

    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.04;
    private static final int tournamentSize = 5;
    private static boolean elitism = true;
    
    public static void setElitism(boolean val){
        elitism = val;
    }
    
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.getSize(), false);
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        
        // Keep the best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover
        for (int i = elitismOffset; i < pop.getSize(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getSize(); i++) {
            mutate(newPopulation.getIndividual(i));
        }
        return newPopulation;
    }
        
    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        newSol.generateIndividual();
        HashMap<Integer,ArrayList<Gene>> groups = newSol.getGroups();
        HashMap<Integer,ArrayList<Gene>> groups1 = indiv1.getGroups();
        HashMap<Integer,ArrayList<Gene>> groups2 = indiv2.getGroups();
        
        // Loop through genes
        for (int i = 0; i < indiv1.getSize(); i++) {
            Gene gene=Individual.genePool[i];
            if(!gene.getCertainty()){
            int oldGroup = findGroup(groups,gene);
            groups.get(oldGroup).remove(gene);
            // Crossover
            if (Math.random() <= uniformRate) {                
                int group = findGroup(groups1,gene);
                groups.get(group).add(gene);
            } else {
                int group = findGroup(groups2,gene);
                groups.get(group).add(gene);
            }
            }
        }
        return newSol;
    }

    // Mutate an individual
    private static void mutate(Individual indiv) {
        // Loop through genes
        HashMap<Integer,ArrayList<Gene>> groups = indiv.getGroups();
        for (int i = 0; i < indiv.getSize(); i++) {
            if (Math.random() <= mutationRate) {
                Gene gene=Individual.genePool[i];
                if(gene.getMaxCN()!=1){
                    int oldGroup = findGroup(groups,gene);
                    groups.get(oldGroup).remove(gene);
                    int group = (int)Math.floor(5*Math.random())+1;
                    groups.get(group).add(Individual.genePool[i]);
                    indiv.setGene(i, gene);
                }
            }
        }
    }
    
    // Select individuals for crossover
    private static Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getSize());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }
    
    private static int findGroup(HashMap<Integer,ArrayList<Gene>> groups,Gene target){
        int groupN=0;
        for(int i=1;i<=6;i++){
            //traverse hashmap: a group
            ArrayList<Gene> group = groups.get(i);
            for(int j=0;j<group.size();j++){
                //traverse arraylist: a gene
                Gene gene = group.get(j);
                if(gene==target){
                    groupN=i;
                    break;
                }
            }    
        }
        return groupN;
    }
}