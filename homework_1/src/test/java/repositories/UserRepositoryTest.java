package repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import out.entites.User;
import out.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    /** Добавление тестовых пользователей */
    @BeforeAll
    static void addTestsUsers(){
        User testUser1 = new User("Test User1", "LOGIN1", "PASSWORD");
        User testUser2 = new User("Test User2", "LOGIN2", "PASSWORD");
        UserRepository.addNewUser(testUser1);
        UserRepository.addNewUser(testUser2);
    }

    /** Проверка на добавление тестового юзера */
    @Test
    void testAddNewUser(){
        User testUser = new User("Test User new", "NewUserTest", "PASSWORD");
        assertTrue(UserRepository.addNewUser(testUser));
    }

    /** Проверка на добавление уже существующего пользователя */
    @Test
    void testAddNewUser2(){
        User testUser = new User("Test User1", "LOGIN1", "PASSWORD");
        assertFalse(UserRepository.addNewUser(testUser));
    }

    /** Проверка на поиск пользователя по Логину/паролю*/
    @Test
    void testFindUser(){
        User testUser1 = new User("", "LOGIN1", "PASSWORD");
        assertEquals(testUser1, UserRepository.findUser(testUser1));
    }

    /** Проверка на наличие пользователя в хранилище */
    @Test
    void testContainsUser(){
        User testUser1 = new User("Test User1", "LOGIN1", "PASSWORD");
        assertTrue(UserRepository.containsUser(testUser1));
    }
}
