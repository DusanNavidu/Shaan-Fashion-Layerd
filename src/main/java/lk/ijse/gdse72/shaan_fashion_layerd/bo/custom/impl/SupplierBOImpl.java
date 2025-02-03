package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.SupplierBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.SupplierDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.SupplierDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ArrayList<SupplierDTO> allSuppliers = new ArrayList<>();
        ArrayList<Supplier> all = supplierDAO.getAll();

        for (Supplier s : all) {
            allSuppliers.add(new SupplierDTO(
                    s.getSupplierId(),
                    s.getSupplierName(),
                    s.getSupplyItem(),
                    s.getSupplierAddress(),
                    s.getContactNo()
            ));
        }
        return allSuppliers;
    }

    @Override
    public boolean saveSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.save(new Supplier(
                dto.getSupplierId(),
                dto.getSupplierName(),
                dto.getSupplyItem(),
                dto.getSupplierAddress(),
                dto.getContactNo()
        ));
    }

    @Override
    public boolean updateSupplier(SupplierDTO dto) throws SQLException {
        return supplierDAO.update(new Supplier(
                dto.getSupplierId(),
                dto.getSupplierName(),
                dto.getSupplyItem(),
                dto.getSupplierAddress(),
                dto.getContactNo()
        ));
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public String generateNewSupplierId() throws SQLException {
        return supplierDAO.generateNewID();
    }
}
