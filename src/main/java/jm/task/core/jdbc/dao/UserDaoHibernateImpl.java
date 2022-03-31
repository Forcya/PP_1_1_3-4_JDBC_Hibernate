package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

    private static String CREATE = "CREATE TABLE IF NOT EXISTS Users(id INT primary key AUTO_INCREMENT, name VARCHAR(15), lastName VARCHAR(20), age INT);";
    private static String DELETE_TABLE = "DROP TABLE IF EXISTS Users;";
    Statement statement;
    Util util = new Util();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            statement = util.getConnection().createStatement();
            statement.execute(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            statement = util.getConnection().createStatement();
            statement.execute(DELETE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {

            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> listUser = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

//            listUser = session.createQuery("from User").getResultList();
//            transaction.commit();

            listUser = session.createQuery("from User").getResultList();
            for (User p : listUser) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
