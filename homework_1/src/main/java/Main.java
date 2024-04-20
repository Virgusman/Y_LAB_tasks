import in.Menu;
import in.DialogUser;
import out.utils.LiquibaseExample;

import java.sql.SQLException;

import static in.Menu.*;

/**
 * Запуск программы
 *
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        LiquibaseExample liq = new LiquibaseExample();
        liq.run();
        DialogUser dialogUser = new DialogUser();
        Menu menu = MAIN;
        while (!(menu == EXIT)) {
            menu = dialogUser.run(menu);
        }
    }
}
