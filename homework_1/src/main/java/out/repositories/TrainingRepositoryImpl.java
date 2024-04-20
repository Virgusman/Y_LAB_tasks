package out.repositories;

import out.entites.Training;
import out.utils.LiquibaseExample;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Хранение всех тренировок
 */
public class TrainingRepositoryImpl implements TrainingRepository {

    /**
     * Подключение к БД
     */
    private Connection connection;

    /**
     * Инициализация подключения к БД
     */
    public TrainingRepositoryImpl() {
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
     * Добавление тренировки в хранилище для авторизованного пользователя
     *
     * @param userLogin логин пользователя в качестве ключа
     * @param training  тренировка для сохранения в мапу
     * @return Возвращает true при успешном добавлении
     */
    @Override
    public boolean addNewTraining(String userLogin, Training training) throws SQLException {
        int id = returnIdByLogin(userLogin);
        //Проверка существует ли тренировка в БД
        String selectSql = "SELECT COUNT(*) FROM trainings.training WHERE (date = ?) and (type = ?) and (user_id = ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSql)) {
            pstmt.setDate(1, Date.valueOf(training.getDate()));
            pstmt.setString(2, training.getType());
            pstmt.setInt(3, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    // Можно добавить тренировку
                    String sql = "INSERT INTO trainings.training (user_id, date, type, duration, calories, comment) " +
                            "VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setDate(2, Date.valueOf(training.getDate()));
                    preparedStatement.setString(3, training.getType());
                    preparedStatement.setInt(4, training.getDuration());
                    preparedStatement.setInt(5, training.getCaloriesBurned());
                    preparedStatement.setString(6, training.getComment());
                    preparedStatement.executeUpdate();
                    return true;
                } else {
                    // Тренировка уже существует
                    return false;
                }
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Получение всех тренировок по указанному пользователю
     *
     * @param userLogin поиск тренировок по ключу (логин пользователя)
     * @return возвращается множество всех тренировок пользователя
     */
    @Override
    public Set<Training> getAllTrainingForUser(String userLogin) throws SQLException {
        int id = returnIdByLogin(userLogin);
        String sql = "SELECT * FROM trainings.training WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Set<Training> trains = new HashSet<>();
        while (resultSet.next()) {
            trains.add(new Training(resultSet.getInt("id"), resultSet.getDate("date").toLocalDate(), resultSet.getString("type"),
                    resultSet.getInt("duration"), resultSet.getInt("calories"),
                    resultSet.getString("comment")));
        }
        return trains;
    }

    /**
     * Обновление списка тренировок для пользователя
     * @param id ID тренировки для редактирования
     * @param training новая тренировка
     * @throws SQLException
     */
    @Override
    public void replaseSet(int id, Training training) throws SQLException {
        String sql = " UPDATE trainings.training SET date = ?, type = ?, duration = ?, calories = ?, comment = ? " +
                " WHERE id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, Date.valueOf(training.getDate()));
        preparedStatement.setString(2, training.getType());
        preparedStatement.setInt(3, training.getDuration());
        preparedStatement.setInt(4, training.getCaloriesBurned());
        preparedStatement.setString(5, training.getComment());
        preparedStatement.setInt(6, id);
        preparedStatement.executeUpdate();
    }

    /**
     * Получение всех тренировок для просмотра админом
     *
     * @return возвращает мапу тренировок
     */
    @Override
    public Map<String, Set<Training>> getTraining() throws SQLException {
        String query = "SELECT login, date, type, duration, calories, comment " +
                "FROM trainings.users JOIN trainings.training ON trainings.users.id = trainings.training.user_id";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        Map<String, Set<Training>> trains = new HashMap<>();
        while (resultSet.next()) {
            Training training = new Training(resultSet.getDate("date").toLocalDate(), resultSet.getString("type"),
                    resultSet.getInt("duration"), resultSet.getInt("calories"),
                    resultSet.getString("comment"));
            trains.putIfAbsent(resultSet.getString("login"), new HashSet<>());
            trains.get(resultSet.getString("login")).add(training);
        }
        return trains;
    }

    /**
     * Удалить тренировку по ID
     *
     * @param indexTrain ID тренировки
     * @throws SQLException
     */
    public void deleteTrain(int indexTrain) throws SQLException {
        String sql = "DELETE FROM trainings.training WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, indexTrain);
        preparedStatement.executeUpdate();
    }

    /**
     * Определение ID по логину
     *
     * @param login логин пользователя
     * @return вовзращает ID
     * @throws SQLException
     */
    public int returnIdByLogin(String login) throws SQLException {
        String query = "SELECT * FROM trainings.users WHERE login = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        return resultSet.getInt(1);
    }
}
