package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CustomerDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {

    ArrayList<String> getAllCustomerIds() throws SQLException;

    Customer findById(String selectedCusId) throws SQLException;

}
