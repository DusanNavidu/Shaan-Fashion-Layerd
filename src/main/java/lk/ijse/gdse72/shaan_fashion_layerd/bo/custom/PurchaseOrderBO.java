package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Customer;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {

    String generateOrderID() throws Exception;

    ArrayList<String> getAllItemIds() throws Exception;

    ArrayList<String> getAllCustomerIds() throws Exception;

    Customer findByCustomerId(String customerId) throws SQLException;

    Item findByItemId(String itemId) throws SQLException;

    boolean saveOrder(OrderDTO orderDTO) throws SQLException;
}
