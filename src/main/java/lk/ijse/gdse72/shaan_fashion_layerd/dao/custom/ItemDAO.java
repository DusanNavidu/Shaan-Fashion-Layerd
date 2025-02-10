package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.ItemDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDetailsDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Item;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    String getNexBatchNumber() throws SQLException;

    ArrayList<String> getAllItemIds() throws SQLException;

    ArrayList<String> getAllItemNames() throws SQLException;

    Item findById(String selectedItemId) throws SQLException;

    boolean reduceQty(OrderDetails entity) throws SQLException;
}
