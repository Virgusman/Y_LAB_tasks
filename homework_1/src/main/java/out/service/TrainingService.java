package out.service;

import out.entites.Training;
import out.repositories.TrainingRepository;
import out.repositories.TrainingRepositoryImpl;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Бизнес логика по работе с Тренировками
 */
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService() {
        this.trainingRepository = new TrainingRepositoryImpl();
    }

    /**
     * Добавление новой тренировки.
     * Выводит уведомление об успешности добавления тренировки
     *
     * @param userLogin ключ значение для сохранения тренировки
     * @param training тренировка для сохранения
     */
    public boolean addNewTraining(String userLogin, Training training) {
        return trainingRepository.addNewTraining(userLogin, training);
    }

    /**
     * Получение всех тренировок для Юзера в отсортированном виде
     *
     * @param userLogin поиск по ключу в мапе
     * @return возвращет список тренировок в отсортированном виде по дате
     */
    public List<Training> watchAllMyTraining(String userLogin) {
        ArrayList<Training> trainings = new ArrayList<>(trainingRepository.getAllTrainingForUser(userLogin));
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
    public void replaseSet(String userLogin, List<Training> trainings) {
        trainingRepository.replaseSet(userLogin, new HashSet<>(trainings));
    }


    /**
     * Подсчёт калорий в отрезке времени
     *
     * @param userLogin поиск тренировок по ключу (Логин пользователя)
     * @param date1 Дата начала отрезка времени
     * @param date2 Дата окончания отрезка времени
     * @return возвращает числовое значение - сумма каллорий
     */
    public int countCalories(String userLogin, LocalDate date1, LocalDate date2) {
        List<Training> trains= watchAllMyTraining(userLogin);
        trains = trains.stream()
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
    public Map<String, Set<Training>> getAllTrainUsers() {
        return trainingRepository.getTraining();
    }
}
