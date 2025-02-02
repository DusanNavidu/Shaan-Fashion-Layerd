package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.ItemDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.ItemDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDetailsDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select itemId from item order by itemId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("I%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "I001"; // Return the default customer ID if no data is found
    }

    @Override
    public String getNexBatchNumber() throws SQLException {
        ResultSet rst = SQLUtil.execute("select batchNumber from item order by batchNumber desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last batch number
            String numericPart = lastId.replaceAll("[^\\d]", ""); // Extract numeric part ignoring characters
            int i = Integer.parseInt(numericPart); // Convert the numeric part to an integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%05d", newIdIndex); // Format to 5 digits with leading zeros
        }
        return "B00001"; // Return the default batch number if no data is found
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from item");

        ArrayList<Item> itemS = new ArrayList<>();

        while (rst.next()) {
            Item item = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDouble(8),
                    rst.getBigDecimal(9)
            );
            itemS.add(item);
        }
        return itemS;
    }

    @Override
    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select itemId from item");

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }

        return itemIds;
    }

    @Override
    public ArrayList<String> getAllItemNames() throws SQLException {
        ResultSet rst = SQLUtil.execute("select itemName from item");

        ArrayList<String> itemNames = new ArrayList<>();

        while (rst.next()) {
            itemNames.add(rst.getString(1));
        }

        return itemNames;
    }

    @Override
    public ItemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from item where itemId=?", selectedItemId);

        if (rst.next()) {
            return new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDouble(8),
                    rst.getBigDecimal(9)
            );
        }
        return null;
    }

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute(
                "insert into item values (?,?,?,?,?,?,?,?,?)",
                entity.getItemId(),
                entity.getItemName(),
                entity.getCategoryId(),
                entity.getBrandId(),
                entity.getItemQuantityOnHand(),
                entity.getBatchNumber(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getProfit()
        );
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute(
                "update item set itemName=?, categoryId=?, brandId=?, itemQuantityOnHand=?, batchNumber=?, description=?, price=?, profit=? where itemId=?",
                entity.getItemName(),
                entity.getCategoryId(),
                entity.getBrandId(),
                entity.getItemQuantityOnHand(),
                entity.getBatchNumber(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getProfit(),
                entity.getItemId()
        );
    }

    @Override
    public boolean delete(String itemId) throws SQLException {
        return SQLUtil.execute("delete from item where itemId=?", itemId);
    }

    @Override
    public boolean reduceQty(OrderDetailsDTO orderDetailsDTO) throws SQLException {
        return SQLUtil.execute(
                "update item set itemQuantityOnHand = itemQuantityOnHand - ? where itemId = ?",
                orderDetailsDTO.getQuantity(),   // QYT to reduce
                orderDetailsDTO.getItemId()      // Item ID
        );
    }
}