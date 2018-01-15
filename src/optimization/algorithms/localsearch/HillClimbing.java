package optimization.algorithms.localsearch;

import optimization.SearchAlgorithm;
import optimization.Configuration;

import java.util.ArrayList;
import java.util.Arrays;


public class HillClimbing extends SearchAlgorithm {

    protected double k;

    @Override
    public void search() {
        initSearch();

        applyHillClimbing(genRandomConfiguration());

        stopSearch();

    }

    public Configuration applyHillClimbing(Configuration initialSolution){

        boolean improves;
        Configuration currentSol;

        currentSol = initialSolution.clone();
        evaluate(currentSol);
        improves = true;

        while(improves){

            improves = false;

            for(Configuration neighbor : generateNeighbors(currentSol)){

                double score = evaluate(neighbor);

                if(score < currentSol.score()) {
                    currentSol = neighbor.clone();
                    improves = true;
                }
            }
        }
        return currentSol;
    }

    public ArrayList<Configuration> generateNeighbors(Configuration configuration){

        double min, max;
        double step;
        double params[];
        ArrayList<Configuration> neighbors;

        neighbors = new ArrayList<Configuration>();

        for(int i = 0; i < problem.size(); i++){

            min = problem.getRepresentation()[0][i];
            max = problem.getRepresentation()[1][i];

            step = k * (max - min);

            params = Arrays.copyOf(configuration.getValues(), problem.size());

            params[i] = Math.min(configuration.getValues()[i] + generator.nextDouble() * step, max);
            neighbors.add(new Configuration(params));
            params[i] = Math.max(configuration.getValues()[i] - generator.nextDouble() * step, min);
            neighbors.add(new Configuration(params));
        }

        return neighbors;
    }

    @Override
    public void showSearchStats() {

    }

    @Override
    public void setParams(String args[]){

        try {

            k = Double.parseDouble(args[0]);
            System.out.println("Using specified configuration: k = " + k);
        } catch(Exception ex) {

            k = 0.1;
            System.out.println("Using default configuration: k = " + k);
        }

    }
}
