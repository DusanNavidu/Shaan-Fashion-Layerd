package lk.ijse.gdse72.shaan_fashion_layerd.dao;

import lk.ijse.gdse72.shaan_fashion_layerd.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,CATEGORY,BRAND,ITEM,ORDER,ORDERDETAILS,SUPPLIER,USER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case CATEGORY:
                return new CategoryDAOImpl();
            case BRAND:
                return new BrandDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
