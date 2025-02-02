package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.BrandDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Brand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BrandDAOImpl implements BrandDAO {
    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("select brandId from brand order by brandId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last brand ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%03d", newIdIndex); // Return the new brand ID in format Cnnn
        }
        return "B001"; // Return the default brand ID if no data is found
    }
    @Override
    public String getBrandNameById(String categoryId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select brandName from brand where brandId=?", categoryId);

        if (rst.next()) {
            return rst.getString("brandName");
        }
        return null;
    }
    @Override
    public ArrayList<Brand> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from brand");

        ArrayList<Brand> brandDTOS = new ArrayList<>();

        while (rst.next()) {
            Brand brandDTO = new Brand(
                    rst.getString(1), // Brand ID
                    rst.getString(2),  // Brand Name
                    rst.getString(3)    // Description
            );
            brandDTOS.add(brandDTO);
        }
        return brandDTOS;
    }
    @Override
    public ArrayList<String> getAllBrandIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select brandId from brand");

        ArrayList<String> brandIds = new ArrayList<>();

        while (rst.next()) {
            brandIds.add(rst.getString(1));
        }
        return brandIds;
    }
    @Override
    public boolean save(Brand entity) throws SQLException {
        return SQLUtil.execute(
                "insert into brand values (?,?,?)",
                entity.getBrandId(),  // Brand ID
                entity.getBrandName(),  // Brand Name
                entity.getDescription()   // Description
        );
    }
    @Override
    public boolean update(Brand entity) throws SQLException {
        return SQLUtil.execute(
                "update brand set brandName=?, description=? where brandId=?",
                entity.getDescription(),  // Description
                entity.getBrandId(),  // Brand ID
                entity.getBrandName()   // Brand Name
        );
    }
    @Override
    public boolean delete(String brandId) throws SQLException {
        return SQLUtil.execute("delete from brand where brandId=?", brandId);
    }
}
