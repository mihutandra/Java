package domain;

import java.util.ArrayList;

public class Fitness {
    String date;
    int nrSteps;
    int sleepHours;
    String activitiesMin;

    public Fitness(int nrSteps, int sleepHours, String activitiesMin,String date) {
        this.nrSteps = nrSteps;
        this.sleepHours = sleepHours;
        this.activitiesMin = activitiesMin;
        this.date = date;
    }
    public int getMinutesOfAnActivity() {
        String[] result = activitiesMin.split(",");
        if (result.length >= 2) {
            try {
                return Integer.parseInt(result[1].trim());
            } catch (NumberFormatException e) {
                // Handle parsing error, e.g., log it or return a default value
                System.err.println("Error parsing move minutes: " + e.getMessage());
            }
        }
        // Return a default value if parsing fails or if activitiesMin does not contain a valid format
        return 0;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNrSteps() {
        return nrSteps;
    }

    public void setNrSteps(int nrSteps) {
        this.nrSteps = nrSteps;
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public String getActivitiesMin() {
        return activitiesMin;
    }

    public void setActivitiesMin(String activitiesMin) {
        this.activitiesMin = activitiesMin;
    }

    @Override
    public String toString() {
        return "Fitness{" +
                "day =" + date +
                ", nrSteps =" + nrSteps +
                ", sleepHours =" + sleepHours +
                ", activities and minutes ='" + activitiesMin + '\'' +
                '}';
    }
}
