package service;


import domain.Weather;
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

    public ArrayList<Weather> getData(){
        ArrayList<Weather> weathers =  repo.getData();
        weathers.sort(Comparator.comparing(Weather::getStartHour)); // show all sorted by hour
        return weathers;
    }

    public ArrayList<Weather> getAllFor(String description){
        return repo.getAllFor(description);
    }
    public ArrayList<String> getAllDescriptions(){
        return repo.getAllDescriptions();
    }

    public int getTotalHoursForDescription(String description) {
        ArrayList<Weather> weathers = getData();

        // Filter the weathers based on the provided description
        ArrayList<Weather> filteredWeathers = weathers.stream()
                .filter(weather -> weather.getDescription().equals(description)) //contains->partial search ; equals->total search
                .collect(Collectors.toCollection(ArrayList::new));

        // Calculate the total number of hours
        int totalHours = 0;
        for (Weather weather : filteredWeathers) {
            totalHours += weather.getEndHour() - weather.getStartHour();
        }

        return totalHours;
    }
    public void updateWeather(Weather updatedWeather){
        repo.updateWeather(updatedWeather);
    }
}
