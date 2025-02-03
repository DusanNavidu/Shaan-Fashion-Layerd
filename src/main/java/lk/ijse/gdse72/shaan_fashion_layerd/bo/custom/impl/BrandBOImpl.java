package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.BrandBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.BrandDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.BrandDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Brand;

import java.sql.SQLException;
import java.util.ArrayList;

public class BrandBOImpl implements BrandBO {

    BrandDAO brandDAO = (BrandDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BRAND);

    @Override
    public ArrayList<BrandDTO> getAllBrands() throws SQLException {
        ArrayList<Brand> allEntityData = brandDAO.getAll();
        ArrayList<BrandDTO> allDTOData = new ArrayList<>();

        for (Brand b : allEntityData) {
            allDTOData.add(new BrandDTO(
                    b.getBrandId(),
                    b.getBrandName(),
                    b.getDescription()
            ));
        }
        return allDTOData;
    }

    @Override
    public boolean saveBrand(BrandDTO dto) throws SQLException {
        return brandDAO.save(new Brand(
                dto.getBrandId(),
                dto.getBrandName(),
                dto.getDescription()
        ));
    }

    @Override
    public boolean updateBrand(BrandDTO dto) throws SQLException {
        return brandDAO.update(new Brand(
                dto.getBrandId(),
                dto.getBrandName(),
                dto.getDescription()
        ));
    }

    @Override
    public boolean deleteBrand(String brandId) throws SQLException {
        return brandDAO.delete(brandId);
    }

    @Override
    public String generateNewBrandId() throws SQLException {
        return brandDAO.generateNewID();
    }
}
