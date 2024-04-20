package out.entites;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Сущность "Тренировка"
 */
@Getter
@Setter
public class Training {

    /** ID тренировки */
    private int id;
    /** Дата тренировки */
    private LocalDate date;

    /** Тип тренировки */
    private String type;

    /** Затраченное время на тренировку в минутах */
    private int duration;

    /** Калории */
    private int caloriesBurned;

    /** Дополнительная информация */
    private String comment;

    public Training(LocalDate date, String type, int duration, int caloriesBurned, String comment) {
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.comment = comment;
    }

    public Training(int id, LocalDate date, String type, int duration, int caloriesBurned, String comment) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
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
