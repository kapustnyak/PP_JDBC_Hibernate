package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age SMALLINT)";

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();
            tx.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        String hql = "DELETE FROM User WHERE id = " + id;

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery(hql).executeUpdate();
            tx.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM User";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Query query = session.createQuery(hql);
            List<User> users;
            users = query.getResultList();

            tx.commit();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        String hql = "DELETE FROM User";

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery(hql).executeUpdate();
            tx.commit();
        }
    }
}
