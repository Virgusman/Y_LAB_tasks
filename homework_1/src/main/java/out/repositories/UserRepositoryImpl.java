package out.repositories;

import out.entites.Role;
import out.entites.User;
import out.utils.LiquibaseExample;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

/**
 * Работа с хранилищем пользователей
 */
public class UserRepositoryImpl implements UserRepository {
    /**
     * Подключение к БД
     */
    private Connection connection;

    /**
     * Инициализация подключения к БД
     */
    public UserRepositoryImpl() {
        Properties prop = new Properties();
        try (InputStream input = LiquibaseExample.class.getClassLoader().getResourceAsStream("database.properties")) {
            prop.load(input);
            String url = prop.getProperty("database.url");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");
            this.connection = DriverManager.getConnection(url,
                    username, password);
        } catch (IOException | SQLException e) {
            System.err.println("Error : " + e.getMessage());
        }
    }

    /**
     * Добавление нового пользователя в хранилище
     *
     * @param user Новый пользователь для добавления
     * @return true при успешном добавлении
     */
    @Override
    public boolean addNewUser(User user) throws SQLException {
        String selectSql = "SELECT COUNT(*) FROM trainings.users WHERE login = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
            pstmt.setString(1, user.getLogin());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    // Юзер не существует
                    String sql = "INSERT INTO trainings.users (fio, login, password, role) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, user.getFio());
                    preparedStatement.setString(2, user.getLogin());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setString(4, String.valueOf(user.getRole()));
                    preparedStatement.executeUpdate();
                    return true;
                } else {
                    // Юзер уже существует
                    return false;
                }
            }
        }
    }

    /**
     * Получение данных Юзера при аунтетификации
     *
     * @param user получения пользователя по Логину/паролю
     * @return возвращается найденного пользователя
     */
    @Override
    public Optional<User> findUser(User user) throws SQLException {
        String sql = "SELECT * FROM trainings.users WHERE login = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getLogin());
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        if (resultSet.next()) {
            // Юзер найден
            User findUser = new User(resultSet.getString("fio"), resultSet.getString("login"),
                    resultSet.getString("password"));
            findUser.setRole(Role.valueOf(resultSet.getString("role")));
            return Optional.of(findUser);
        } else {
            // Юзер не найден
            return Optional.empty();
        }
    }

}
