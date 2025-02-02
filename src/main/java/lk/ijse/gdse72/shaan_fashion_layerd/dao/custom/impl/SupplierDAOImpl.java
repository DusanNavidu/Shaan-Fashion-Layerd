package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.SupplierDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select supplierId from supplier order by supplierId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last supplier ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("S%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "S001"; // Return the default supplier ID if no data is found
    }
    @Override
    public ArrayList<Supplier> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from supplier");

        ArrayList<Supplier> suppliers = new ArrayList<>();

        while (rst.next()) {
            Supplier supplier = new Supplier(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // Item
                    rst.getString(4),  // Address
                    rst.getString(5)   // Phone
            );
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute(
                "insert into supplier values (?,?,?,?,?)",
                entity.getSupplierId(),
                entity.getSupplierName(),
                entity.getSupplyItem(),
                entity.getSupplierAddress(),
                entity.getContactNo()
        );
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        String sql = "UPDATE supplier SET supplierName = ?, supplyItem = ?, supplierAddress = ?, contactNo = ? WHERE supplierId = ?";
        return SQLUtil.execute(sql,
                entity.getSupplierName(), // supplierName
                entity.getSupplyItem(),    // supplyItem
                entity.getSupplierAddress(), // supplierAddress
                entity.getContactNo(), // contactNo
                entity.getSupplierId() // supplierId
        );
    }

    @Override
    public boolean delete(String supplierId) throws SQLException {
        return SQLUtil.execute("delete from supplier where supplierId=?", supplierId);
    }

    @Override
    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select supplierId from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }
}
