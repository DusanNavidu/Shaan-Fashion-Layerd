package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.OrderDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDAOImpl implements OrderDAO {

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select orderId from orders order by orderId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "O002"
            String substring = lastId.substring(1); // e.g., "002"
            int i = Integer.parseInt(substring); // 2
            int newIdIndex = i + 1; // 3
            return String.format("O%03d", newIdIndex);
        }
        return "O001";
    }

    @Override
    public boolean save(Orders entity) throws SQLException {
        return SQLUtil.execute(
                "insert into orders values (?,?,?)",
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getOrderDate()
        );
    }


    @Override
    public boolean exist(String orderId) throws SQLException {
        return SQLUtil.execute("select orderId from orders where orderId=?", orderId);

    }
    @Override
    public ArrayList<Orders> getAll() throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean update(Orders entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }
}
