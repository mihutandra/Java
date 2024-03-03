package service;

import domain.Quiz;
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

    public ArrayList<Quiz> getData(){
        ArrayList<Quiz> quizzes =  repo.getData();
        quizzes.sort(Comparator.comparing(Quiz::getScore)); // show all sorted by score
        return quizzes;
    }

    public List<Quiz> searchQuestion(String searchText, int minScore) { return repo.searchQuestion(searchText,minScore);}
    public void updateQuiz(Quiz updateQuiz) { repo.updateQuiz(updateQuiz);}

    public int calculateAndDisplayTotalScore() {
        int totalScore = 0;
        for (Quiz quiz : repo.getData()) {
            if (!quiz.getUserAnswer().isBlank()) {
                totalScore += quiz.getScore();
            }
        }
        return totalScore;
    }

}
