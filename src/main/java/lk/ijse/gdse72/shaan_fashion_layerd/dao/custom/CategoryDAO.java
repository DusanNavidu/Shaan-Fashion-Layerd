package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CategoryDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDAO extends CrudDAO<Category> {

    String getCategoryNameById(String categoryId) throws SQLException;

    ArrayList<String> getAllCategoryIds() throws SQLException;
}
