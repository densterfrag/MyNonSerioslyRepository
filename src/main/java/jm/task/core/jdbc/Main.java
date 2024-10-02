package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("sf", "sf", (byte) 3);
        userService.saveUser("s23f", "sf23", (byte) 4);
        userService.saveUser("Максим", "Кац", (byte) 53);
        userService.saveUser("Кирилл", "Бледный", (byte) 42);
        userService.saveUser("Вадим", "Шашлык", (byte) 23);
        userService.saveUser("Петр", "Первый", (byte) 100);
        List<User> list = userService.getAllUsers();
        System.out.println(list);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
