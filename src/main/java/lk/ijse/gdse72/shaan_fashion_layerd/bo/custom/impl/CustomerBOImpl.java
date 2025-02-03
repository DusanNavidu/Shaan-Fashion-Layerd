package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.CustomerBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.CustomerDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CustomerDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException{
        ArrayList<CustomerDTO> allCustomers= new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustomerId(), c.getUserId(), c.getCustomerName(), c.getCustomerAddress(), c.getCustomerEmail()));
        }
        return allCustomers;
    }

    @Override
    public String generateNewCustomerId() throws SQLException {
        return customerDAO.generateNewID();
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.save(new Customer(
                dto.getCustomerId(),
                dto.getUserId(),
                dto.getCustomerName(),
                dto.getCustomerAddress(),
                dto.getCustomerEmail()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCustomerId(), dto.getUserId(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerEmail()));
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        return customerDAO.delete(customerId);
    }
}
