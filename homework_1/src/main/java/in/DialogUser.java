package in;

import out.entites.Training;
import out.entites.TypeTraining;
import out.service.UserService;
import out.service.TrainingService;
import out.utils.Audit;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static in.Menu.*;
import static in.lexicon.Rus.*;

/**
 * Реализация диалога с пользователем
 */
public class DialogUser {

    private final Scanner sc = new Scanner(System.in);

    /**
     * Выбор действия пользователя
     */
    private int choice = 0;

    /**
     * Отслеживаемый логин пользователя
     */
    private String userLogin = "Неизвестный";


    private final TrainingService trainingService;

    private final UserService userService;

    public DialogUser() {
        this.trainingService = new TrainingService();
        this.userService = new UserService();
    }

    /**
     * Диалог с пользователем
     *
     * @param menu определение раздела меню
     * @return возвращение нового раздела меню
     */
    public Menu run(Menu menu) {

        switch (menu) {
            case MAIN -> {
                System.out.println(MAIN_TEXT);
                System.out.println(ENTER_TEXT);
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(ERROR_INPUT_TEXT);
                    sc.nextLine();
                    break;
                }
                Audit.addLog(userLogin, menu, choice);
                sc.nextLine();
                if (choice == 1) {
                    RegisterUser();
                } else if (choice == 2) {
                    return Authentication();
                } else if (choice == 3) {
                    return EXIT;
                }
            }
            case USER_MENU -> {
                System.out.println(USER_MENU_TEXT);
                System.out.println(ENTER_TEXT);
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(ERROR_INPUT_TEXT);
                    sc.nextLine();
                    break;
                }
                Audit.addLog(userLogin, menu, choice);
                sc.nextLine();
                if (choice == 4) {
                    userLogin = "Неизвестный";
                    return MAIN;
                } else if (choice == 1) {
                    addNewTraining();
                    return USER_MENU;
                } else if (choice == 2) {
                    getAllMyTraining();
                    return USER_MENU;
                } else if (choice == 3) {
                    getCalories();
                    return USER_MENU;
                }
            }
            case ADMIN_MENU -> {
                System.out.println(USER_MENU_TEXT);
                System.out.println(ADMIN_MENU_TEXT);
                System.out.println(ENTER_TEXT);
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println(ERROR_INPUT_TEXT);
                    sc.nextLine();
                    break;
                }
                Audit.addLog(userLogin, menu, choice);
                sc.nextLine();
                if (choice == 4) {
                    return MAIN;
                } else if (choice == 1) {
                    addNewTraining();
                    return ADMIN_MENU;
                } else if (choice == 2) {
                    getAllMyTraining();
                    return ADMIN_MENU;
                } else if (choice == 3) {
                    getCalories();
                    return ADMIN_MENU;
                } else if (choice == 5) {
                    getAllTrainUsers();
                    return ADMIN_MENU;
                } else if (choice == 6) {
                    Audit.printLogAudit();
                    return ADMIN_MENU;
                }
            }
        }
        return MAIN;
    }

    /**
     * Просмотр всех тренировок всех пользователей (для админа)
     */
    private void getAllTrainUsers() {
        Map<String, Set<Training>> trainings = trainingService.getAllTrainUsers();
        for (Map.Entry<String, Set<Training>> entry : trainings.entrySet()) {
            System.out.println("Тренировки учетной записи: " + entry.getKey());
            for (Training train : entry.getValue()) {
                System.out.println(train);
            }
        }
    }

    /**
     * Просмотр потраченных калорий в разрезе времени
     */
    private void getCalories() {
        try {
            System.out.println("=================================");
            System.out.println("Введите начальную дату в формате гггг-мм-дд:");
            LocalDate date1 = LocalDate.parse(sc.nextLine());
            System.out.println("Введите конечную дату в формате гггг-мм-дд:");
            LocalDate date2 = LocalDate.parse(sc.nextLine());
            int countCalories = trainingService.countCalories(userLogin, date1, date2);
            System.out.println("Количество потраченных калорий за указанное время: " + countCalories);
        } catch (DateTimeParseException | IndexOutOfBoundsException | InputMismatchException | NullPointerException e) {
            System.out.println(ERROR_INPUT_TEXT);
        }
    }

    /**
     * Просмотр всех своих треннировок для User
     * Плюс редактирование тренировок
     */
    private void getAllMyTraining() {
        try {
            List<Training> trainings = trainingService.watchAllMyTraining(userLogin);
            for (int i = 0; i < trainings.size(); i++) {
                System.out.print((i + 1) + " - ");
                System.out.println(trainings.get(i));
            }
            System.out.println("\nВыберете дейстие:");
            System.out.println("1 - Выбрать тренировку для редактирования");
            System.out.println("2 - Вернуться назад");
            choice = sc.nextInt();
            if (choice == 1) {
                try {
                    System.out.println("Введите номер тренировки:");
                    int indexTrain = sc.nextInt() - 1;
                    System.out.println(trainings.get(indexTrain));
                    System.out.println("1 - Редактировать тренировку");
                    System.out.println("2 - Удалить тренировку");
                    System.out.println("3 - Вернуться в меню");
                    choice = sc.nextInt();
                    if (choice == 1) {
                        sc.nextLine();
                        System.out.println("Дата тренировки - " + trainings.get(indexTrain).getDate());
                        System.out.println("Введите новую дату:");
                        LocalDate date = LocalDate.parse(sc.nextLine());
                        System.out.println("Тип тренировки - " + trainings.get(indexTrain).getType());
                        System.out.println("Выберете новый тип:");
                        ArrayList<String> types = new ArrayList<>(TypeTraining.getTitle());
                        for (String s : types) {
                            System.out.println(types.indexOf(s) + " - " + s);
                        }
                        String type = types.get(sc.nextInt());
                        System.out.println("Длительность тренировки - " + trainings.get(indexTrain).getDuration());
                        System.out.println("Выберете новый длительность в минутах:");
                        int duration = sc.nextInt();
                        System.out.println("Затраченные каллории - " + trainings.get(indexTrain).getCaloriesBurned());
                        System.out.println("Укажите новое значение затраченных каллорий:");
                        int caloriesBurned = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Дополнительная информация - " + trainings.get(indexTrain).getComment());
                        System.out.println("Укажите новую дополнительную информацию:");
                        String comment = sc.nextLine();
                        trainings.set(indexTrain, new Training(date, type, duration, caloriesBurned, comment));
                        trainingService.replaseSet(userLogin, trainings);
                    } else if (choice == 2) {
                        trainings.remove(indexTrain);
                        trainingService.replaseSet(userLogin, trainings);
                    }
                } catch (DateTimeParseException | IndexOutOfBoundsException | InputMismatchException e) {
                    System.out.println(ERROR_INPUT_TEXT);
                }
            }
        } catch (NullPointerException | InputMismatchException e) {
            System.out.println(ERROR_INPUT_TEXT);
        }
    }

    /**
     * Добавление новой тренировки
     */
    private void addNewTraining() {
        System.out.println("=================================");
        System.out.println("Введите дату в формате гггг-мм-дд:");
        try {
            LocalDate date = LocalDate.parse(sc.nextLine());
            System.out.println("Выберете тип тренировки:");
            ArrayList<String> types = new ArrayList<>(TypeTraining.getTitle());
            for (String s : types) {
                System.out.println(types.indexOf(s) + " - " + s);
            }
            String type = types.get(sc.nextInt());
            System.out.println("Укажите длительность тренировки в минутах:");
            int duration = sc.nextInt();
            System.out.println("Укажите затраченные каллории:");
            int caloriesBurned = sc.nextInt();
            sc.nextLine();
            System.out.println("Укажите дополнительную информацию по тренировке:");
            String comment = sc.nextLine();
            if (trainingService.addNewTraining(userLogin, new Training(date, type, duration, caloriesBurned, comment))){
                System.out.println("Тренировка добавлена");
            } else {
                System.out.println("Тренировка не добавлена (Тренировка указанного типа уже заведена в указанную дату)");
            }
        } catch (DateTimeParseException | IndexOutOfBoundsException | InputMismatchException e) {
            System.out.println(ERROR_INPUT_TEXT);
        }
    }

    /**
     * Авторизация пользователя
     *
     * @return возвращает новый раздел меню для перехода
     */
    private Menu Authentication() {
        System.out.println("=================================");
        System.out.println("Введите ЛОГИН пользователя:");
        userLogin = sc.nextLine();
        System.out.println("Введите ПАРОЛЬ пользователя:");
        String password = sc.nextLine();
        return userService.authorization(userLogin, password);
    }

    /**
     * Регистрация нового пользователя
     */
    private void RegisterUser() {
        System.out.println("=================================");
        System.out.println("Введите ФИО пользователя:");
        String fio = sc.nextLine();
        System.out.println("Введите ЛОГИН пользователя:");
        String login = sc.nextLine();
        System.out.println("Введите ПАРОЛЬ пользователя:");
        String password = sc.nextLine();
        userService.addNewUser(fio, login, password);
    }
}
