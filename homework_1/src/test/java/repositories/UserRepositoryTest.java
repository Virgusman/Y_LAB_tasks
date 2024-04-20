package repositories;


import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Testcontainers
public class UserRepositoryTest {

    static {
        PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
                .withDatabaseName("test_db")
                .withUsername("test_user")
                .withPassword("test_password");
        postgresContainer.start();
        try (Connection connection = DriverManager.getConnection(postgresContainer.getJdbcUrl(), "test_user", "test_password")) {
            connection.setAutoCommit(false);
            String createTableSQL = "CREATE TABLE users (" +
                    "id SERIAL PRIMARY KEY," +
                    "login VARCHAR(255)," +
                    "password VARCHAR(255)," +
                    "role VARCHAR(255))";
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);
            String insert1 = "INSERT INTO users (login, password, role) VALUES ('fio', 'user', 'user', 'USER')";
            statement.executeUpdate(insert1);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*@Test
    @DisplayName("Проверка на добавление тестового юзера")
    void testAddNewUser(){
        User testUser = new User("Test User new", "NewUserTest", "PASSWORD");
        assertTrue(userRepository.addNewUser(testUser));
    }*/

    /*@Test
    @DisplayName("Проверка на добавление уже существующего пользователя")
    void testAddNewUser2(){
        User testUser = new User("Test User1", "LOGIN1", "PASSWORD");
        assertFalse(userRepository.addNewUser(testUser));
    }

    @Test
    @DisplayName("Проверка на поиск пользователя по Логину/паролю")
    void testFindUser(){
        User testUser1 = new User("", "LOGIN1", "PASSWORD");
        assertEquals(testUser1, userRepository.findUser(testUser1).get());
    }*/
}
