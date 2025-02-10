package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.CustomerDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.ItemDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.OrderDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.OrderDetailsDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.db.DBConnection;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Customer;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Item;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.OrderDetails;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public String generateOrderID() throws Exception {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<String> getAllItemIds() throws Exception {
        return itemDAO.getAllItemIds();
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws Exception {
        return customerDAO.getAllCustomerIds();
    }

    @Override
    public Customer findByCustomerId(String customerId) throws SQLException {
        return customerDAO.findById(customerId);
    }

    @Override
    public Item findByItemId(String itemId) throws SQLException {
        return itemDAO.findById(itemId);
    }

    @Override
    public boolean saveOrderDetailsList(ArrayList<OrderDetails> orderDetailsS) throws SQLException {
        for (OrderDetails orderDetails : orderDetailsS) {
            boolean isOrderDetailsSaved = orderDetailsDAO.save(orderDetails);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = itemDAO.reduceQty(orderDetails);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveOrder(OrderDTO orderDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.save(new Orders(orderDTO.getOrderId(),orderDTO.getCustomerId(),orderDTO.getOrderDate()));
            if (isOrderSaved) {
                boolean isOrderDetailListSaved = saveOrderDetailsList(orderDTO.getOrderDetailsDTOS());
                if (isOrderDetailListSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}
