package service;

import domain.Fitness;
import repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private final Repository repo;

    public Service(Repository repo) {

        this.repo = repo;

    }

    public void addData(){
        repo.addData();
    }

    public ArrayList<Fitness> getData(){
        ArrayList<Fitness> fitnesses =  repo.getData();
        fitnesses.sort(Comparator.comparing(Fitness::getDate)); // show all sorted by score
        return fitnesses;
    }

    public List<Fitness> search(int value1) {
       return repo.search(value1);
    }

    public void addFitnessActivity(Fitness fitness){repo.addFitnessActivity(fitness);}

    public int getTotalStepsForMonth(String month){
        return repo.getTotalStepsForMonth(month);
    }

}
