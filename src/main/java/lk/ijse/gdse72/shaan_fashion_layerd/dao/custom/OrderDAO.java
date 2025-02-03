package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Orders;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Orders> {

    boolean save(OrderDTO orderDTO) throws SQLException;

    boolean exist(String orderId) throws SQLException;
}
