package out.entites;

import java.util.Objects;

/**
 * Модель "Пользователь"
 */
public class User {

    /** Фамилия Имя Отчество */
    String fio;
    /** логин пользователя */
    String login;
    /** пароль пользователя */
    String password;
    /** Уровень доступа */
    Access access;

    public User(String fio, String login, String password) {
        this.fio = fio;
        this.login = login;
        this.password = password;
        access = Access.USER;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
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
