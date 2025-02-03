package lk.ijse.gdse72.shaan_fashion_layerd.bo;

import lk.ijse.gdse72.shaan_fashion_layerd.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER, CATEGORY,BRAND,SUPPLIER,ITEM
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case CUSTOMER:
                return new CustomerBOImpl();
            case CATEGORY:
                return new CategoryBOImpl();
            case BRAND:
                return new BrandBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case ITEM:
                return new ItemBOImpl();
            default:
                return null;
        }
    }
}
