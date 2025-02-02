package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDetailsDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDeatailsDAO extends CrudDAO<OrderDetails> {
    boolean saveOrderDetailsList(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException;
}
