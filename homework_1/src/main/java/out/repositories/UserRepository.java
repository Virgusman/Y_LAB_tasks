package out.repositories;

import out.entites.Access;
import out.entites.User;

import java.util.HashSet;

/**
 * Работа с хранилищем пользователей
 */
public class UserRepository {


    /** Множество для хранения пользователей */
    private static final HashSet<User> users = new HashSet<User>();

    /** добавление начальных пользователей */
    static {
        User user = new User("admin", "admin", "admin");
        user.setAccess(Access.ADMIN);
        users.add(user);

        user = new User("user", "user", "user");
        user.setAccess(Access.USER);
        users.add(user);
    }

    /**
     * Добавление нового пользователя в хралище
     *
     * @param user Новый пользователь для добавления
     * @return true при успешном добавлении
     */
    public static boolean addNewUser(User user) {
        return users.add(user);
    }

    /**
     * Получение данных Юзера при аунтетификации
     *
     * @param user получения пользователя по Логину/паролю
     * @return возвращается найденного пользователя
     */
    public static User findUser(User user) {
        return users.stream().filter(n -> n.equals(user)).findFirst().get();
    }

    /**
     * Проверка на наличие Юзера в хралищие при аунтетификации
     *
     * @param user поиск по сущности пользователя логин/пароль
     * @return возвращает true если данный пользователь найден
     */
    public static boolean containsUser(User user) {
        return users.contains(user);
    }
}
