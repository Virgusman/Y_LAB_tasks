package repositories;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import out.entites.Training;
import out.service.TrainingService;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainingRepositoryTest {

    @Rule
    public static TrainingService trainingService = new TrainingService();

    @BeforeAll
    @DisplayName("Добавление тестовых тренировок")
    static void addTestTrainings(){
        trainingService.addNewTraining("LOGIN1",
                new Training(LocalDate.parse("2024-01-01"), "Кардио", 20, 20, "Информация1"));
        trainingService.addNewTraining("LOGIN1",
                new Training(LocalDate.parse("2024-01-02"), "Силовая тренировка", 30, 20, "Информация2"));
        trainingService.addNewTraining("LOGIN1",
                new Training(LocalDate.parse("2024-01-03"), "Йога", 100, 30, "Информация3"));

        trainingService.addNewTraining("LOGIN2",
                new Training(LocalDate.parse("2022-01-01"), "Кардио", 20, 20, "Информация1"));
        trainingService.addNewTraining("LOGIN2",
                new Training(LocalDate.parse("2022-01-02"), "Силовая тренировка", 30, 20, "Информация2"));
        trainingService.addNewTraining("LOGIN2",
                new Training(LocalDate.parse("2022-01-03"), "Йога", 100, 30, "Информация3"));
    }

    @Test
    @DisplayName("Проверка на добавление существующей тренировки")
    void testAddNewTraining(){
        Training training = new Training(LocalDate.parse("2024-01-01"), "Кардио", 20, 20, "Информация1");
        assertFalse(trainingService.addNewTraining("LOGIN1", training));
    }

    @Test
    @DisplayName("Проверка на добавление новой тренировки")
    void testAddNewTraining2(){
        Training training = new Training(LocalDate.parse("2020-01-01"), "Силовая тренировка", 20, 20, "Информация1");
        assertTrue(trainingService.addNewTraining("LOGIN1", training));
    }
}
