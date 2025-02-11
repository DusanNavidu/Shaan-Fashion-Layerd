package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.QueryDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.CustomEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrder(String orderId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute(
                "SELECT orders.orderId,orders.orderDate,orders.customerID,orderdetails.orderId,orderdetails.itemId,orderdetails.quantity,orderdetails.price from Orders inner join OrderDetails on Orders.orderId=OrderDetails.orderId where Orders.orderId= ?" , orderId);
        ArrayList<CustomEntity> allRecords = new ArrayList<>();

        while (rst.next()) {
            String ordersId = rst.getString("ordersId");
            Date orderDate = rst.getDate("orderDate");
            String customerId = rst.getString("customerId");
            String itemId = rst.getString("itemId");
            int quantity = rst.getInt("quantity");
            BigDecimal price = rst.getBigDecimal("price");

            CustomEntity customEntity = new CustomEntity(ordersId, orderDate, customerId, itemId, quantity, price);
            allRecords.add(customEntity);
        }
        return allRecords;
    }
}
