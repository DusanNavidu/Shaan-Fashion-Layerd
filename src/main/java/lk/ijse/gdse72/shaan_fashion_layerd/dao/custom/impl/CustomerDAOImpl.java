package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.CustomerDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Customer;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customerId from customer order by customerId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("C%03d", newIdIndex);
        }
        return "C001";
    }
    @Override
    public ArrayList<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from customer");

        ArrayList<Customer> allCustomers = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // User ID
                    rst.getString(3),  // Name
                    rst.getString(4),  // Address
                    rst.getString(5)   // Email
            );
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        String sql = "UPDATE Customer SET userId = ?, customerName = ?, customerAddress = ?, customerEmail = ? WHERE customerId = ?";
        return SQLUtil.execute(sql,
                entity.getUserId(),
                entity.getCustomerName(),
                entity.getCustomerAddress(),
                entity.getCustomerEmail(),
                entity.getCustomerId()
        );
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute(
                "insert into customer values (?,?,?,?,?)",
                entity.getCustomerId(),
                entity.getUserId(),
                entity.getCustomerName(),
                entity.getCustomerAddress(),
                entity.getCustomerEmail()
        );
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return SQLUtil.execute("delete from customer where customerId=?", customerId);
    }


    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select customerId from customer");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }
    @Override
    public Customer findById(String selectedCusId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from customer where customerId=?", selectedCusId);

        if (rst.next()) {
            return new Customer(
                    rst.getString(1),  // Customer ID
                    rst.getString(2),  // User ID
                    rst.getString(3),  // Name
                    rst.getString(4),  // Address
                    rst.getString(5)   // Email
            );
        }
        return null;
    }
}
