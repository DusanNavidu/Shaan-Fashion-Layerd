package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.SQLUtil;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.CategoryDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Category;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CategoryDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public String generateNewID() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT categoryId FROM category ORDER BY categoryId DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last category ID
            if (lastId != null && lastId.matches("CAT\\d+")) { // Validate format
                String substring = lastId.substring(3); // Extract numeric part (after 'CAT')
                try {
                    int i = Integer.parseInt(substring); // Convert to integer
                    int newIdIndex = i + 1;
                    return String.format("CAT%03d", newIdIndex); // Format new ID as CAT001, CAT002, etc.
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in categoryId: " + lastId);
                }
            }
        }
        return "CAT001"; // Default ID if no valid data is found
    }


    @Override
    public ArrayList<Category> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from category");

        ArrayList<Category> allCategorys = new ArrayList<>();

        while (rst.next()) {
            Category category = new Category(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            allCategorys.add(category);
        }
        return allCategorys;
    }

    @Override
    public String getCategoryNameById(String categoryId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select categoryName from category where categoryId=?", categoryId);

        if (rst.next()) {
            return rst.getString("categoryName");
        }
        return null;
    }

    @Override
    public boolean save(Category entity) throws SQLException {
        return SQLUtil.execute(
                "insert into category values (?,?,?)",
                entity.getCategoryId(),
                entity.getCategoryName(),
                entity.getDescription()
        );
    }

    @Override
    public boolean update(Category entity) throws SQLException {
        return SQLUtil.execute(
                "update category set categoryName=?, description=? where categoryId=?",
                entity.getCategoryName(),
                entity.getDescription(),
                entity.getCategoryId()
        );
    }

    @Override
    public boolean delete(String categoryId) throws SQLException {
        return SQLUtil.execute("delete from category where categoryId=?", categoryId);
    }

    @Override
    public ArrayList<String> getAllCategoryIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select categoryId from category");

        ArrayList<String> categoryIds = new ArrayList<>();

        while (rst.next()) {
            categoryIds.add(rst.getString(1));
        }
        return categoryIds;
    }
}
