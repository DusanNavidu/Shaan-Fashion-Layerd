package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDetailsDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDetailsDAOImpl implements OrderDetailsDAO {


    @Override
    public boolean save(OrderDetails entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                entity.getOrderId(),
                entity.getItemId(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    @Override
    public String generateNewID() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }


    @Override
    public boolean update(OrderDetails entity) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        throw new UnsupportedOperationException("This feature is not implemented yet");
    }
}
