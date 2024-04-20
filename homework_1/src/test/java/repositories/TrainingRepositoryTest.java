package repositories;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import out.entites.Training;
import out.service.TrainingService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainingRepositoryTest {

    static {
        PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("test_db")
                .withUsername("test_user")
                .withPassword("test_password");
        postgresContainer.start();
        try (Connection connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(), "test_user", "test_password")) {
            connection.setAutoCommit(false);
          /*  String createTableSQL = "CREATE TABLE trainings (" +
                    "id SERIAL PRIMARY KEY," +
                    "login VARCHAR(255)," +
                    "password VARCHAR(255)," +
                    "role VARCHAR(255))";
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);
            connection.commit();*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
