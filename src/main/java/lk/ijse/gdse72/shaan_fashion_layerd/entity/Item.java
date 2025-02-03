package lk.ijse.gdse72.shaan_fashion_layerd.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {
    private String itemId;
    private String itemName;
    private String categoryId;
    private String brandId;
    private int itemQuantityOnHand;
    private String batchNumber;
    private String description;
    private double price;
    private BigDecimal profit;
}
