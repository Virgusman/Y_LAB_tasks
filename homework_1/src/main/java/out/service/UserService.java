package out.service;

import in.Menu;
import out.entites.Role;
import out.entites.User;
import out.repositories.UserRepository;
import out.repositories.UserRepositoryImpl;

import java.sql.SQLException;

/**
 * Бизнес логина по работе с пользователями
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    /**
     * Добавление нового пользователя
     *
     * @param fio ФИО пользователя
     * @param login Логин пользователя
     * @param password пароль пользователя
     */
    public void addNewUser(String fio, String login, String password) throws SQLException {
        String s = (userRepository.addNewUser(new User(fio, login, password))) ? "\nНовый пользователь добавлен"
                : "\nПользователь с таким ЛОГИНОМ уже занят";
        System.out.println(s);
    }

    /**
     * Авторизация пользователя
     *
     * @param login логин пользователя
     * @param password пароль пользователя
     * @return точка входа в меню
     */
    public Menu authorization(String login, String password) throws SQLException {
        User user = new User("", login, password);
        if (userRepository.findUser(user).isPresent()) {
            User userBase = userRepository.findUser(user).get();
            if (user.getPassword().equals(userBase.getPassword())) {
                if (userBase.getRole().equals(Role.USER)) {
                    return Menu.USER_MENU;
                } else if (userBase.getRole().equals(Role.ADMIN)) {
                    return Menu.ADMIN_MENU;
                }
            }
        }
        System.out.println("Введённые данные не верны");
        return Menu.MAIN;
    }
}
