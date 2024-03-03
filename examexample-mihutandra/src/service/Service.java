package service;

import domain.Route;
import repository.*;

import java.sql.Connection;
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

    public ArrayList<Route> getData(){
        return repo.getData();
    }

    public ArrayList<String> getAllSourceCities(){
        return repo.getSourceCities();
    }

    public ArrayList<String> getAllDestinationsFor(String sourceCity){
        return repo.getDestinationCitiesFor(sourceCity);
    }

    public ArrayList<Route> getAllRoutsFor(String sourceCity, String destination){
        return repo.getAllRoutesFor(sourceCity,destination);
    }

    public List<Route> sortByDeparture(){
        List<Route> routes = repo.getData();
        // Compare departure cities
        // If departure cities are the same, compare departure times
        return routes.stream()
                .sorted(Comparator.comparing(Route::getSourceCity).thenComparing(Route::getDepartureTime))
                .collect(Collectors.toList());
    }

}
