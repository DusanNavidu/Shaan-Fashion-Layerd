package lk.ijse.gdse72.shaan_fashion_layerd.dao.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.CrudDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.BrandDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Brand;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BrandDAO extends CrudDAO<Brand> {

    String getBrandNameById(String categoryId) throws SQLException;

    ArrayList<String> getAllBrandIds() throws SQLException;
}
