package out.repositories;

import out.entites.Training;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public interface TrainingRepository {
    boolean addNewTraining(String userLogin, Training training) throws SQLException;

    Set<Training> getAllTrainingForUser(String userLogin) throws SQLException;

    void replaseSet(int userLogin, Training training) throws SQLException;

    Map<String, Set<Training>> getTraining() throws SQLException;

    void deleteTrain(int indexTrain) throws SQLException;
}
