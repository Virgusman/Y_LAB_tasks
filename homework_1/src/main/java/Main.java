import in.Menu;
import in.DialogUser;

import static in.Menu.*;

/**
 * Запуск программы
 *
 */
public class Main {

    public static void main(String[] args) {
        DialogUser dialogUser = new DialogUser();
        Menu menu = MAIN;
        while (!(menu == EXIT)) {
            menu = dialogUser.run(menu);
        }
    }
}
