package out.repositories;

import out.entites.Training;

import java.util.*;

/**
 * Хранение всех тренировок
 */
public class TrainingRepositories {

    /** Мапа для хранеия тренировок по ключу (логин пользователя) */
    private static final HashMap<String, HashSet<Training>> trainings = new HashMap<>();

    /**
     * Добавление тренировки в хранилище для авторизованного пользователя
     *
     * @param userLogin логин пользователя в качестве ключа
     * @param training тренировка для сохранения в мапу
     * @return Возвращает true при успешном добавлении
     */
    public static boolean addNewTraining(String userLogin, Training training) {
        if (trainings.containsKey(userLogin)){
            return trainings.get(userLogin).add(training);
        } else {
            trainings.put(userLogin, new HashSet<>(Collections.singletonList(training)));
            return true;
        }
    }

    /**
     * Получение всех тренировок по указанному пользователю
     *
     * @param userLogin поиск тренировок по ключу (логин пользователя)
     * @return возвращается множество всех тренировок пользователя
     */
    public static HashSet<Training> getAllTrainigForUser(String userLogin) {
        return trainings.get(userLogin);
    }

    /**
     * Обновление списка тренировок для пользователя
     *
     * @param userLogin обновление тренировок по ключу (логин пользователя)
     * @param training обновлённый список тренировк для сохранения
     */
    public static void replaseSet(String userLogin, HashSet<Training> training) {
        trainings.put(userLogin, training);
    }

    /**
     * Получение всех тренировок для просмотра админом
     *
     * @return возвращает мапу тренировок
     */
    public static HashMap<String, HashSet<Training>> getTraining() {
        return trainings;
    }
}
