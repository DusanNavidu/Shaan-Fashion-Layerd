package lk.ijse.gdse72.shaan_fashion_layerd.dao;

import lk.ijse.gdse72.shaan_fashion_layerd.entity.Customer;
import lk.ijse.gdse72.shaan_fashion_layerd.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{
    String generateNewID() throws SQLException;
    ArrayList<Customer> getAll() throws SQLException;
    boolean save(CustomerDTO customerDTO) throws SQLException;
    boolean update(CustomerDTO customerDTO) throws SQLException;
    boolean delete(String customerId) throws SQLException;
}