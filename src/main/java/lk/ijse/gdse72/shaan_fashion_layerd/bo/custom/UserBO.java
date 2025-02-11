package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.UserDTO;

import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDTO> getAllUsers() throws Exception;

    String generateNewUserId() throws Exception;

    boolean saveUser(UserDTO dto) throws Exception;

    boolean updateUser(UserDTO dto) throws Exception;

    boolean deleteUser(String userId) throws Exception;

    boolean isValidUser(String username, String password) throws Exception;

    boolean isUpdatePassword(String username, String password) throws Exception;

    boolean isValidUserFullName(String fullName) throws Exception;
}
