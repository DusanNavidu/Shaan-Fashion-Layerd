package lk.ijse.gdse72.shaan_fashion_layerd.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ItemTM {
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
