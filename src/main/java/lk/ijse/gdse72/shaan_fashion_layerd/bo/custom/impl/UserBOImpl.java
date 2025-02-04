package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.UserBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.UserDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.UserDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.User;

import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDTO> getAllUsers() throws Exception {
        ArrayList<UserDTO> allUsers = new ArrayList<>();
        ArrayList<User> all = userDAO.getAll();
        for (User u : all) {
            allUsers.add(new UserDTO(u.getUserId(), u.getUserFullName(), u.getUsername(), u.getUserEmail(), u.getPassword()));
        }
        return allUsers;
    }

    @Override
    public String generateNewUserId() throws Exception {
        return userDAO.generateNewID();
    }

    @Override
    public boolean saveUser(UserDTO dto) throws Exception {
        return userDAO.save(new User(dto.getUserId(), dto.getUserFullName(), dto.getUsername(), dto.getUserEmail(), dto.getPassword()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws Exception {
        return userDAO.update(new User(dto.getUserId(), dto.getUserFullName(), dto.getUsername(), dto.getUserEmail(), dto.getPassword()));
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        return userDAO.delete(userId);
    }
}
