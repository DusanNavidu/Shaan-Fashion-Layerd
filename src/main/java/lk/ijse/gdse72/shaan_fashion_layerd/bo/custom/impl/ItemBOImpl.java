package lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.ItemBO;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.DAOFactory;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.ItemDAO;
import lk.ijse.gdse72.shaan_fashion_layerd.dto.ItemDTO;
import lk.ijse.gdse72.shaan_fashion_layerd.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO  = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allEntityData = itemDAO.getAll();
        ArrayList<ItemDTO> allDTOData= new ArrayList<>();
        for (Item i : allEntityData) {
            allDTOData.add(new ItemDTO(
                    i.getItemId(),
                    i.getItemName(),
                    i.getCategoryId(),
                    i.getBrandId(),
                    i.getItemQuantityOnHand(),
                    i.getBatchNumber(),
                    i.getDescription(),
                    i.getPrice(),
                    i.getProfit()
            ));
        }
        return allDTOData;
    }

    @Override
    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(
                dto.getItemId(),
                dto.getItemName(),
                dto.getCategoryId(),
                dto.getBrandId(),
                dto.getItemQuantityOnHand(),
                dto.getBatchNumber(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getProfit()
        ));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(
                dto.getItemId(),
                dto.getItemName(),
                dto.getCategoryId(),
                dto.getBrandId(),
                dto.getItemQuantityOnHand(),
                dto.getBatchNumber(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getProfit()
        ));
    }

    @Override
    public String generateNewItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

    @Override
    public String generateNewBatchNumber() throws SQLException, ClassNotFoundException {
        return itemDAO.getNexBatchNumber();
    }
}
