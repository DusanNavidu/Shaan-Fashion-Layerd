package lk.ijse.gdse72.shaan_fashion_layerd.dao;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.BrandDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.CustomerDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,CATEGORY,BRAND
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case CATEGORY:
                return new CategoryDAOImpl();
            case BRAND:
                return new BrandDAOImpl();
            default:
                return null;
        }
    }
}
