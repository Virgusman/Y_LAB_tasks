package out.service;

import out.entites.Training;
import out.repositories.TrainingRepositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Бизнес логика по работе с Тренировками
 */
public class TrainingService {

    /**
     * Добавление новой тренировки.
     * Выводит уведомление об успешности добавления тренировки
     *
     * @param userLogin ключ значение для сохранения тренировки
     * @param training тренировка для сохранения
     */
    public static void addNewTraining(String userLogin, Training training) {
        String s = (TrainingRepositories.addNewTraining(userLogin, training)) ?
                "Тренировка добавлена" : "Тренировка не добавлена (Тренировка указанного типа уже заведена в указанную дату)";
        System.out.println(s);
    }

    /**
     * Получение всех тренировок для Юзера в отсортированном виде
     *
     * @param userLogin поиск по ключу в мапе
     * @return возвращет список тренировок в отсортированном виде по дате
     */
    public static ArrayList<Training> watchAllMyTraining(String userLogin) {
        ArrayList<Training> trainings = new ArrayList<>(TrainingRepositories.getAllTrainigForUser(userLogin));
        trainings = (ArrayList<Training>) trainings.stream()
                .sorted(Comparator.comparing(Training::getDate))
                .collect(Collectors.toList());
        return trainings;
    }

    /**
     * Обновление списка тренировок пользователя
     *
     * @param userLogin обновление по ключу (логин пользователя)
     * @param trainings обновлённый список тренировок
     */
    public static void replaseSet(String userLogin, ArrayList<Training> trainings) {
        TrainingRepositories.replaseSet(userLogin, new HashSet<>(trainings));
    }


    /**
     * Подсчёт калорий в отрезке времени
     *
     * @param userLogin поиск тренировок по ключу (Логин пользователя)
     * @param date1 Дата начала отрезка времени
     * @param date2 Дата окончания отрезка времени
     * @return возвращает числовое значение - сумма каллорий
     */
    public static int countCalories(String userLogin, LocalDate date1, LocalDate date2) {
        ArrayList<Training> trains= TrainingService.watchAllMyTraining(userLogin);
        trains = (ArrayList<Training>) trains.stream()
                .filter(n -> (n.getDate().isAfter(date1)) && (n.getDate().isBefore(date2)))
                .collect(Collectors.toList());
        int count = 0;
        for (Training train : trains){
            count += train.getCaloriesBurned();
        }
        return count;
    }

    /**
     * Получить все тренировки всех пользователей
     * для просмотра админом
     *
     * @return возвращает все тренировки для всех пользователей
     */
    public static HashMap<String, HashSet<Training>> getAllTrainUsers() {
        return TrainingRepositories.getTraining();
    }
}
