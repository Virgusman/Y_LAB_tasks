package repositories;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import out.entites.User;
import out.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    @Rule
    public static UserRepository userRepository = new UserRepository();

    @BeforeAll
    @DisplayName("Добавление тестовых пользователей")
    static void addTestsUsers(){
        User testUser1 = new User("Test User1", "LOGIN1", "PASSWORD");
        User testUser2 = new User("Test User2", "LOGIN2", "PASSWORD");
        userRepository.addNewUser(testUser1);
        userRepository.addNewUser(testUser2);
    }

    @Test
    @DisplayName("Проверка на добавление тестового юзера")
    void testAddNewUser(){
        User testUser = new User("Test User new", "NewUserTest", "PASSWORD");
        assertTrue(userRepository.addNewUser(testUser));
    }

    @Test
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
    }

    @Test
    @DisplayName("Проверка на наличие пользователя в хранилище")
    void testContainsUser(){
        User testUser1 = new User("Test User1", "LOGIN1", "PASSWORD");
        assertTrue(userRepository.findUser(testUser1).isPresent());
    }
}
