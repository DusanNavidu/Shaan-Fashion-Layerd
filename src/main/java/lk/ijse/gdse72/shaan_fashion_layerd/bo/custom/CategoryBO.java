package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CategoryDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryBO extends SuperBO {
    ArrayList<CategoryDTO> getAllCategoryS() throws SQLException, ClassNotFoundException;

    boolean saveCategory(CategoryDTO dto) throws SQLException;

    boolean updateCategory(CategoryDTO dto) throws SQLException;

    boolean deleteCategory(String itemId) throws SQLException;

    String generateNewCategoryId() throws SQLException;
}
