package out.repositories;

import out.entites.Role;
import out.entites.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Работа с хранилищем пользователей
 */
public class UserRepositoryImpl implements UserRepository {


    /**
     * Множество для хранения пользователей
     */
    private final Set<User> users = new HashSet<>();

    /* добавление начальных пользователей */ {
        User user = new User("admin", "admin", "admin");
        user.setRole(Role.ADMIN);
        users.add(user);

        user = new User("user", "user", "user");
        user.setRole(Role.USER);
        users.add(user);
    }

    /**
     * Добавление нового пользователя в хранилище
     *
     * @param user Новый пользователь для добавления
     * @return true при успешном добавлении
     */
    @Override
    public boolean addNewUser(User user) {
        return users.add(user);
    }

    /**
     * Получение данных Юзера при аунтетификации
     *
     * @param user получения пользователя по Логину/паролю
     * @return возвращается найденного пользователя
     */
    @Override
    public Optional<User> findUser(User user) {
        return users.stream().filter(n -> n.equals(user)).findFirst();
    }
}
