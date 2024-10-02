package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS public.users (id BIGSERIAL PRIMARY KEY, name TEXT, lastname TEXT, age SMALLINT);";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS public.users;";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() throws SQLException {
        try (SessionFactory sessionFactory = Util.openHibernate();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        try (SessionFactory sessionFactory = Util.openHibernate();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DELETE_TABLE).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.openHibernate();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = User.builder()
                    .name(name)
                    .lastName(lastName)
                    .age(age)
                    .build();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        try (SessionFactory sessionFactory = Util.openHibernate();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> users = null;
        try (SessionFactory sessionFactory = Util.openHibernate();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        try (SessionFactory sessionFactory = Util.openHibernate();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
