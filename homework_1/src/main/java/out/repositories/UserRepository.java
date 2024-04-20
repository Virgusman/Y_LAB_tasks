package out.repositories;

import out.entites.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    boolean addNewUser(User user) throws SQLException;

    Optional<User> findUser(User user) throws SQLException;
}
