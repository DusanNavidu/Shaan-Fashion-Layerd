package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.OrderDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.db.DBConnection;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDAOImpl implements OrderDAO {

    private final OrderDetailsDAOImpl orderDetailsDAO = new OrderDetailsDAOImpl();

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
    public ArrayList<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Order entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String customerId) throws SQLException {
        return false;
    }

    @Override
    public boolean save(Order entity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false); // 1

            boolean isOrderSaved = SQLUtil.execute(
                    "insert into orders values (?,?,?)",
                    entity.getOrderId(),
                    entity.getCustomerId(),
                    entity.getOrderDate()
            );
            if (isOrderSaved) {
                boolean isOrderDetailListSaved = orderDetailsDAO.saveOrderDetailsList(entity.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    connection.commit(); // 2
                    return true;
                }
            }
            connection.rollback(); // 3
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true); // 4
        }
    }
}
