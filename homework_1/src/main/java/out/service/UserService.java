package out.service;

import in.Menu;
import out.entites.Access;
import out.entites.User;
import out.repositories.UserRepository;

/**
 * Бизнес логина по работе с пользователями
 */
public class UserService {

    /**
     * Добавление нового пользователя
     *
     * @param fio ФИО пользователя
     * @param login Логин пользователя
     * @param password пароль пользователя
     */
    public static void addNewUser(String fio, String login, String password) {
        String s = (UserRepository.addNewUser(new User(fio, login, password))) ? "\nНовый пользователь добавлен"
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
    public static Menu authorization(String login, String password) {
        User user = new User("", login, password);
        if (UserRepository.containsUser(user)) {
            User userBase = UserRepository.findUser(user);
            if (user.getPassword().equals(userBase.getPassword())) {
                if (userBase.getAccess().equals(Access.USER)) {
                    return Menu.USER_MENU;
                } else if (userBase.getAccess().equals(Access.ADMIN)) {
                    return Menu.ADMIN_MENU;
                }
            }
        }
        System.out.println("Введённые данные не верны");
        return Menu.MAIN;
    }
}
