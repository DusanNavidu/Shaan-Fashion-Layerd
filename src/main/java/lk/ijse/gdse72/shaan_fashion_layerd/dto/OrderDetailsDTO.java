package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO implements Serializable {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
