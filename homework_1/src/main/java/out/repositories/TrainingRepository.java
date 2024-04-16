package out.repositories;

import out.entites.Training;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface TrainingRepository {
    boolean addNewTraining(String userLogin, Training training);

    Set<Training> getAllTrainingForUser(String userLogin);

    void replaseSet(String userLogin, HashSet<Training> training);

    Map<String, Set<Training>> getTraining();
}
