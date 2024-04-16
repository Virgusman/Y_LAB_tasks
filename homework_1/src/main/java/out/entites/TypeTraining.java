package out.entites;
import java.util.HashSet;
/**
 * Типы тренировок списком
 */
public class TypeTraining {
    private static HashSet<String> title = new HashSet<>();

    static {
        title.add("Кардио");
        title.add("Силовая тренировка");
        title.add("Йога");
    }

    public static HashSet<String> getTitle() {
        return title;
    }

    public static void setTitle(HashSet<String> title) {
        TypeTraining.title = title;
    }

}
