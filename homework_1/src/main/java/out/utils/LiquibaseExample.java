package out.utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * Активация Liquibase: создание таблиц, заполнение данными
 */
public class LiquibaseExample {

    /**
     * Запуск выполнение модуля
     */
    public void run() {
        Properties prop = new Properties();
        try (InputStream input = LiquibaseExample.class.getClassLoader().getResourceAsStream("database.properties")) {
            prop.load(input);
            String url = prop.getProperty("database.url");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");
            Connection connection = DriverManager.getConnection(url,
                    username, password);
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("db/changelog/changelog-master.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (IOException | LiquibaseException | SQLException e) {
            System.err.println("Error : " + e.getMessage());
        }
    }
}


