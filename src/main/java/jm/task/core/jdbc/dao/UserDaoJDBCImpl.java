package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private static String DELETE = "delete from Users where id = ?";
    private static String CREATE = "CREATE TABLE IF NOT EXISTS Users(id INT primary key AUTO_INCREMENT, name VARCHAR(15), lastName VARCHAR(20), age INT);";
    private static String INSERT = "insert into Users (name, lastName, age) values(?, ?, ?)";
    private static String CLEANE = "TRUNCATE TABLE Users";
    private static String DELETE_TABLE = "DROP TABLE IF EXISTS Users;";
    private static String SELECT_ALL = "select * from Users;";
    Util util = new Util();

    Statement statement;
    PreparedStatement preparedStatement;


    public void createUsersTable() {
        try {
            statement = util.getConnection().createStatement();
            statement.execute(CREATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement = util.getConnection().createStatement();
            statement.execute(DELETE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = util.getConnection().prepareStatement(INSERT);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();

            System.out.println("User c именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            preparedStatement = util.getConnection().prepareStatement(DELETE);
            preparedStatement.setInt((int) id, 1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = util.getConnection().prepareStatement(SELECT_ALL);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                Byte age = rs.getByte("age");

                users.add(new User(name, lastName, age));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        try {
            statement = util.getConnection().createStatement();
            statement.execute(CLEANE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
