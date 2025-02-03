package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.SuperBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.BrandDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BrandBO extends SuperBO {
    ArrayList<BrandDTO> getAllBrands() throws SQLException;

    boolean saveBrand(BrandDTO dto) throws SQLException;

    boolean updateBrand(BrandDTO dto) throws SQLException;

    boolean deleteBrand(String brandId) throws SQLException;

    String generateNewBrandId() throws SQLException;
}
