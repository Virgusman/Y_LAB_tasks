package out.entites;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Сущность "Тренировка"
 */
public class Training {

    /** Дата тренировки */
    LocalDate date; //Дата тренировки

    /** Тип тренировки */
    String type;

    /** Затраченное время на тренировку в минутах */
    int duration;

    /** Калории */
    int caloriesBurned;

    /** Дополнительная информация */
    String comment;

    public Training(LocalDate date, String type, int duration, int caloriesBurned, String comment) {
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(date, training.date) && Objects.equals(type, training.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, type);
    }

    @Override
    public String toString() {
        return "Дата тренировки - " + date +
                ", Тип тренировки - '" + type + '\'' +
                ", Время тренировки в минутах - " + duration +
                ", Затраченные каллории - " + caloriesBurned +
                ", Дополнительная информация - '" + comment + '\'';
    }
}
