package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.OrderDeatailsDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDetailsDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;


public class OrderDetailsDAOImpl implements OrderDeatailsDAO {

    private final ItemDAOImpl itemDAO = new ItemDAOImpl();

    @Override
    public boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            boolean isOrderDetailsSaved = saveOrderDetail(orderDetailsDTO);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = itemDAO.reduceQty(orderDetailsDTO);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "insert into orderdetails values (?,?,?,?)",
                orderDetailsDTO.getOrderId(),
                orderDetailsDTO.getItemId(),
                orderDetailsDTO.getQuantity(),
                orderDetailsDTO.getPrice()
        );
    }

    @Override
    public String generateNewID() throws SQLException {
        return "";
    }

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(OrderDetails entity) throws SQLException {
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
