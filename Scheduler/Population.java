public class Population{
    Individual[] individuals;

    public Population(int populationSize, boolean initialize) {
        individuals = new Individual[populationSize];
        if (initialize) {
            for (int i = 0; i < getSize(); i++) {
                Individual[] tournament = new Individual[populationSize];
                for (int j = 0; j < getSize(); j++) {
                    Individual ind = new Individual();
                    ind.generateIndividual();
                    tournament[j]=ind;
                }
                Individual fittest = tournament[0];
                for (int j = 0; j < getSize(); j++) {
                    if (fittest.getFitness() <= tournament[j].getFitness()) {
                        fittest = tournament[j];
                    }
                }
                saveIndividual(i, fittest);
            }
        }
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 0; i < getSize(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    public int getSize() {
        return individuals.length;
    }

    public void saveIndividual(int index, Individual ind) {
        individuals[index] = ind;
    }
}