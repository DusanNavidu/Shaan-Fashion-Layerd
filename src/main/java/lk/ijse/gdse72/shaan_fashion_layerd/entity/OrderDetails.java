package lk.ijse.gdse72.shaan_fashion_layerd.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetails {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
