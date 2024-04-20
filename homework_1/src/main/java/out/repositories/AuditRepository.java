package out.repositories;

import out.utils.LiquibaseExample;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Работа с БД таблицы аудит
 */
public class AuditRepository {
    private Connection connection;

    /**
     * Ининиализация подключение к БД
     */
    public AuditRepository() {
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
     * Добавление новой записи в аудит
     * @param username логин пользователя
     * @param timestamp дата время
     * @param action действие
     * @throws SQLException
     */
    public void addLog(String username, LocalDateTime timestamp, String action) throws SQLException {
        String sql = "INSERT INTO trainings.audit (user_login, datetime, action) " +
                "VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setTimestamp(2, Timestamp.valueOf(timestamp));
        preparedStatement.setString(3, action);
        preparedStatement.executeUpdate();
    }

    /**
     * Получение всех записей аудита для просмотра
     * @return мапа (логин пользователя / время / действие)
     * @throws SQLException
     */
    public Map<String, HashMap<LocalDateTime, String>> getAll() throws SQLException {
        String query = "SELECT user_login, datetime, action " +
                " FROM trainings.audit ";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        Map<String, HashMap<LocalDateTime, String>> audit = new HashMap<>();
        while (resultSet.next()) {
            audit.putIfAbsent(resultSet.getString("user_login"), new HashMap<>());
            audit.get(resultSet.getString("user_login")).put(resultSet.getTimestamp("datetime").toLocalDateTime(), resultSet.getString("action"));
        }
        return audit;
    }
}
