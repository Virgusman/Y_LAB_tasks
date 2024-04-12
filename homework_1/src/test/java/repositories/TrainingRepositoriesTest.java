package repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import out.entites.Training;
import out.repositories.TrainingRepositories;
import out.service.TrainingService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainingRepositoriesTest {

    /** Добавление тестовых тренировк */
    @BeforeAll
    static void addTestTrainings(){

        TrainingService.addNewTraining("LOGIN1",
                new Training(LocalDate.parse("2024-01-01"), "Кардио", 20, 20, "Информация1"));
        TrainingService.addNewTraining("LOGIN1",
                new Training(LocalDate.parse("2024-01-02"), "Силовая тренировка", 30, 20, "Информация2"));
        TrainingService.addNewTraining("LOGIN1",
                new Training(LocalDate.parse("2024-01-03"), "Йога", 100, 30, "Информация3"));

        TrainingService.addNewTraining("LOGIN2",
                new Training(LocalDate.parse("2022-01-01"), "Кардио", 20, 20, "Информация1"));
        TrainingService.addNewTraining("LOGIN2",
                new Training(LocalDate.parse("2022-01-02"), "Силовая тренировка", 30, 20, "Информация2"));
        TrainingService.addNewTraining("LOGIN2",
                new Training(LocalDate.parse("2022-01-03"), "Йога", 100, 30, "Информация3"));
    }

    /** Проверка на добавление существующей тренировки */
    @Test
    void testAddNewTraining(){
        Training training = new Training(LocalDate.parse("2024-01-01"), "Кардио", 20, 20, "Информация1");
        assertFalse(TrainingRepositories.addNewTraining("LOGIN1", training));
    }

    /** Проверка на добавление новой тренировки */
    @Test
    void testAddNewTraining2(){
        Training training = new Training(LocalDate.parse("2020-01-01"), "Силовая тренировка", 20, 20, "Информация1");
        assertTrue(TrainingRepositories.addNewTraining("LOGIN1", training));
    }
}
