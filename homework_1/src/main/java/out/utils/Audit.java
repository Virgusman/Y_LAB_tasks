package out.utils;

import in.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Аудит действий пользователей
 */
public class Audit {

    /**
     * Список всех записей в аудит
     */
    private static final Map<String, HashMap<LocalDateTime, String>> auditLog = new HashMap<>();

    /**
     * Форматирование даты и времени
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");

    /**
     * Добавление новой записи в аудит
     *
     * @param user   пользователь совершивший действие
     * @param menu   этап в меню совершенного дейсвтия
     * @param choice выбранное действие полльзователем
     */
    public static void addLog(String user, Menu menu, int choice) {
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
    public static void printLogAudit() {
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
     * @param username логин пользователя
     * @param timestamp время записи
     * @param action описание действия
     */
    private static void addAuditLog(String username, LocalDateTime timestamp, String action) {
        auditLog.putIfAbsent(username, new HashMap<>());
        auditLog.get(username).put(timestamp, action);
    }
}
