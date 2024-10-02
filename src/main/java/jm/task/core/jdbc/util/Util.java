package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Util {
    final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    final static String USER = "postgres";
    final static String PASSWORD = "1234";

    static {
        loadDriver();
    }

    private Util() {
    }

    public static Connection openJDBC() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory openHibernate() {
        try{
            Configuration config = new Configuration();
            config.setProperty("hibernate.connection.url", URL);
            config.setProperty("hibernate.connection.username", USER);
            config.setProperty("hibernate.connection.password", PASSWORD);
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
            config.setProperty("hibernate.show_sql", "true");
            config.setProperty("hibernate.hbm2ddl.auto", "update");
            config.setProperty("hibernate.format_sql", "true");
            config.setProperty("connection.driver_class","org.postgresql.Driver");
            config.setProperty("hibernate.current_session_context_class", "thread");
            config.addAnnotatedClass(User.class);
            return config.buildSessionFactory();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }
}

