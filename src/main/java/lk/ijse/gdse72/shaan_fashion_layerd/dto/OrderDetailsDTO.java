package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
