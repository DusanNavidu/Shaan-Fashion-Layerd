package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<User> {
    boolean ValidUser(String username, String password) throws SQLException;

    boolean updatePassword(String username, String password) throws SQLException;

    boolean ValidUserFullName(String fullName) throws SQLException;
}
