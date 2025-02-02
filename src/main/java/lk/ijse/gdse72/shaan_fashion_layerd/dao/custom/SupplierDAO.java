package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierDAO extends CrudDAO<Supplier> {
    ArrayList<String> getAllSupplierIds() throws SQLException;
}
