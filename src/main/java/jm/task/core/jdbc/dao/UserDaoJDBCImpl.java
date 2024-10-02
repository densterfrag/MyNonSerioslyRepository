package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import lombok.Builder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.openJDBC();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                                   CREATE TABLE IF NOT EXISTS my_table(
                                   id BIGSERIAL PRIMARY KEY,
                                   name TEXT, last_name TEXT,
                                   age int);
                    """);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.openJDBC();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    DROP TABLE IF EXISTS my_table;
                    """);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = Util.openJDBC();
             Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO my_table (name, last_name, age) VALUES ('" + name + "', '" + lastName + "'," + age + ")";
            statement.execute(sql);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM my_table WHERE id = " + id + ";";
        try (Connection connection = Util.openJDBC();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public List<User> getAllUsers() throws SQLException {
        try (Connection connection = Util.openJDBC();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM my_table");
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                User user = User.builder()
                        .id(id)
                        .name(name)
                        .lastName(lastName)
                        .age(age)
                        .build();
                user.setId(id);
                users.add(user);
            }
            return users;
        }
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.openJDBC();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    DELETE FROM my_table;
                    """);
        }
    }
}
