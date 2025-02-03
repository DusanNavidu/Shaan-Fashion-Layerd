package lk.ijse.gdse72.shaan_fashion_layerd.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomEntity {

    //Customer
    private String customerId;
    private String userId;
    private String customerName;
    private String customerAddress;
    private String customerEmail;

    //Category
    private String categoryId;
    private String categoryName;
    private String categoryDescription;

    //Item
    private String itemId;
    private String itemName;
    private String brandId;
    private String itemQuantityOnHand;
    private String batchNumber;
    private String description;
    private BigDecimal price;
    private BigDecimal profit;

    //Brand
    private String brandName;

    //Orders
    private String orderId;
    private Date orderDate;

    //OrderDetails
    private int quantity;

    //Supplier
    private String supplierId;
    private String supplierName;
    private String supplyItem;
    private String supplierAddress;
    private String contactNo;

    public CustomEntity(String ordersId, Date orderDate, String customerId, String itemId, int quantity, BigDecimal price) {
        this.orderId = ordersId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }
}
