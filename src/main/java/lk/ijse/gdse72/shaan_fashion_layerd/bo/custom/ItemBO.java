package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    String generateNewItemId() throws SQLException, ClassNotFoundException;

    String generateNewBatchNumber() throws SQLException, ClassNotFoundException;
}
