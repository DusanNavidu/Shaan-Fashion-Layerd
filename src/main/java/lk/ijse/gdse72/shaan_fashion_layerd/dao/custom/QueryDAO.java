package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SuperDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> searchOrder(String orderId) throws SQLException, ClassNotFoundException;
}
