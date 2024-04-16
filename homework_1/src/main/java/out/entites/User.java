package out.entites;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
/**
 * Модель "Пользователь"
 */
@Getter
@Setter
public class User {

    /** Фамилия Имя Отчество */
    private String fio;
    /** логин пользователя */
    private String login;
    /** пароль пользователя */
    private String password;
    /** Уровень доступа */
    private Role role;

    public User(String fio, String login, String password) {
        this.fio = fio;
        this.login = login;
        this.password = password;
        this.role = Role.USER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
