package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.UserDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select userId from user order by userId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("U%03d", newIdIndex);
        }
        return "U001";
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from user");

        ArrayList<User> allUsers = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            allUsers.add(user);
        }
        return allUsers;
    }

    @Override
    public boolean save(User entity) throws SQLException {
        return SQLUtil.execute(
                "insert into user values (?,?,?,?,?)",
                entity.getUserId(),
                entity.getUserFullName(),
                entity.getUsername(),
                entity.getUserEmail(),
                entity.getPassword()
        );
    }

    @Override
    public boolean update(User entity) throws SQLException {
        String sql = "UPDATE User SET userFullName = ?, username = ?, userEmail = ?, password = ? WHERE userId = ?";
        return SQLUtil.execute(sql,
                entity.getUserFullName(),
                entity.getUsername(),
                entity.getUserEmail(),
                entity.getPassword(),
                entity.getUserId()
        );
    }

    @Override
    public boolean delete(String userId) throws SQLException {
        return SQLUtil.execute("delete from user where userId=?", userId);
    }

    @Override
    public boolean ValidUser(String username, String password) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM user WHERE username = ? AND password = ?", username, password);
        return rst.next();
    }

    @Override
    public boolean updatePassword(String username, String password) throws SQLException {
        return SQLUtil.execute("UPDATE user SET password = ? WHERE username = ?", password, username);
    }

    @Override
    public boolean ValidUserFullName(String fullName) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT userId FROM user WHERE userId = 'U001' AND userFullName = ?", fullName);
        return rst.next();
    }
}
