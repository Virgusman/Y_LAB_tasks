package out.repositories;

import out.entites.Training;

import java.util.*;

/**
 * Хранение всех тренировок
 */
public class TrainingRepositoryImpl implements TrainingRepository {

    /**
     * Мапа для хранеия тренировок по ключу (логин пользователя)
     */
    private final Map<String, Set<Training>> trainings = new HashMap<>();

    /**
     * Добавление тренировки в хранилище для авторизованного пользователя
     *
     * @param userLogin логин пользователя в качестве ключа
     * @param training  тренировка для сохранения в мапу
     * @return Возвращает true при успешном добавлении
     */
    @Override
    public boolean addNewTraining(String userLogin, Training training) {
        trainings.putIfAbsent(userLogin, new HashSet<>());
        return trainings.get(userLogin).add(training);
    }

    /**
     * Получение всех тренировок по указанному пользователю
     *
     * @param userLogin поиск тренировок по ключу (логин пользователя)
     * @return возвращается множество всех тренировок пользователя
     */
    @Override
    public Set<Training> getAllTrainingForUser(String userLogin) {
        return new HashSet<>(trainings.get(userLogin));
    }

    /**
     * Обновление списка тренировок для пользователя
     *
     * @param userLogin обновление тренировок по ключу (логин пользователя)
     * @param training  обновлённый список тренировк для сохранения
     */
    @Override
    public void replaseSet(String userLogin, HashSet<Training> training) {
        trainings.put(userLogin, training);
    }

    /**
     * Получение всех тренировок для просмотра админом
     *
     * @return возвращает мапу тренировок
     */
    @Override
    public Map<String, Set<Training>> getTraining() {
        return trainings;
    }
}
