package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.CategoryBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.CategoryDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.CategoryDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO  = (CategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CATEGORY);

    @Override
    public ArrayList<CategoryDTO> getAllCategoryS() throws SQLException, ClassNotFoundException {
        ArrayList<Category> allEntityData = categoryDAO.getAll();
        ArrayList<CategoryDTO> allDTOData= new ArrayList<>();
        for (Category ca : allEntityData) {
            allDTOData.add(new CategoryDTO(
                    ca.getCategoryId(),
                    ca.getCategoryName(),
                    ca.getDescription()
            ));
        }
        return allDTOData;
    }

    @Override
    public boolean saveCategory(CategoryDTO dto) throws SQLException{
        return categoryDAO.save(new Category(
                dto.getCategoryId(),
                dto.getCategoryName(),
                dto.getDescription()
        ));
    }

    @Override
    public boolean updateCategory(CategoryDTO dto) throws SQLException {
        return categoryDAO.update(new Category(
                dto.getCategoryId(),
                dto.getCategoryName(),
                dto.getDescription()
        ));
    }

    @Override
    public boolean deleteCategory(String categoryId) throws SQLException {
        return categoryDAO.delete(categoryId);
    }

    @Override
    public String generateNewCategoryId() throws SQLException {
        return categoryDAO.generateNewID();
    }
}
