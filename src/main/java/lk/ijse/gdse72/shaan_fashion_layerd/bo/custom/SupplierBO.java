package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.SupplierDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getAllSuppliers() throws SQLException;

    boolean saveSupplier(SupplierDTO dto) throws SQLException;

    boolean updateSupplier(SupplierDTO dto) throws SQLException;

    boolean deleteSupplier(String supplierId) throws SQLException;

    String generateNewSupplierId() throws SQLException;
}
