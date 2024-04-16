package out.repositories;

import out.entites.User;

import java.util.Optional;

public interface UserRepository {
    boolean addNewUser(User user);

    Optional<User> findUser(User user);
}
