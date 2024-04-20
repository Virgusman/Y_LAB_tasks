package out.utils;

import in.Menu;
import out.repositories.AuditRepository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Аудит действий пользователей
 */
public class Audit {

    public AuditRepository auditRepository;

    public Audit() {
        this.auditRepository = new AuditRepository();
    }

    /**
     * Форматирование даты и времени
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");

    /**
     * Добавление новой записи в аудит
     *
     * @param user   пользователь совершивший действие
     * @param menu   этап в меню совершенного дейсвтия
     * @param choice выбранное действие полльзователем
     */
    public void addLog(String user, Menu menu, int choice) throws SQLException {
        LocalDateTime dateTime = LocalDateTime.now();
        switch (menu) {
            case MAIN -> {
                switch (choice) {
                    case 1 -> addAuditLog(user, dateTime, "Регистрация нового пользователя");
                    case 2 -> addAuditLog(user, dateTime, "Попытка авторизации пользователя");
                    case 3 -> addAuditLog(user, dateTime, "Выход из системы");
                }
            }
            case USER_MENU -> {
                switch (choice) {
                    case 1 -> addAuditLog(user, dateTime, "Добавление новой тренировки");
                    case 2 -> addAuditLog(user, dateTime, "Просмотр всех тренировок");
                    case 3 -> addAuditLog(user, dateTime, "Подсчёт калорий в отрезке времени");
                    case 4 -> addAuditLog(user, dateTime, "Разлогирование");
                }
            }
        }
    }

    /**
     * Вывод всех записей в консоль
     */
    public void printLogAudit() throws SQLException {
        Map<String, HashMap<LocalDateTime, String>> auditLog = auditRepository.getAll();
        for (String user : auditLog.keySet()) {
            System.out.println("Аудиты для пользователя " + user + ":");
            HashMap<LocalDateTime, String> userAuditLogs = auditLog.get(user);
            for (LocalDateTime timestamp : userAuditLogs.keySet()) {
                System.out.println(timestamp.format(formatter) + " - " + userAuditLogs.get(timestamp));
            }
        }
    }

    /**
     * Запись данных в аудит
     *
     * @param username  логин пользователя
     * @param timestamp время записи
     * @param action    описание действия
     */
    private void addAuditLog(String username, LocalDateTime timestamp, String action) throws SQLException {
        auditRepository.addLog(username, timestamp, action);
    }
}
