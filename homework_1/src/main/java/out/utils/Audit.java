package out.utils;

import in.Menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * Аудит действий пользователей
 */
public class Audit {

    /** Список всех записей в аудит */
    private static final LinkedList<String> logAudit = new LinkedList<>();

    /** Форматирование даты и времени */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss ");

    /**
     * Добавление новой записи в аудит
     *
     * @param user пользователь совершивший действие
     * @param menu этап в меню совершенного дейсвтия
     * @param choice выбранное действие полльзователем
     */
    public static void addLog(String user, Menu menu, int choice) {
        LocalDateTime dateTime = LocalDateTime.now();
        switch (menu) {
            case MAIN -> {
                switch (choice) {
                    case 1 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Регистрация нового пользователя");
                    }
                    case 2 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Попытка авторизации пользователя");
                    }
                    case 3 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Выход из системы");
                    }
                }
            }
            case USER_MENU -> {
                switch (choice){
                    case 1 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Добавление новой тренировки");
                    }
                    case 2 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Просмотр всех тренировок");
                    }
                    case 3 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Подсчёт калорий в отрезке времени");
                    }
                    case 4 -> {
                        logAudit.add(dateTime.format(formatter) + user + " : Разлогирование");
                    }
                }
            }
        }
    }

    /**
     * Вывод всех записей в консоль
     */
    public static void printLogAudit() {
        for (String s : logAudit) {
            System.out.println(s);
        }
    }
}
