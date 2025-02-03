package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException;

    String generateNewCustomerId() throws SQLException;

    boolean saveCustomer(CustomerDTO dto) throws SQLException;

    boolean updateCustomer(CustomerDTO dto) throws SQLException;

    boolean deleteCustomer(String id) throws SQLException;
}
