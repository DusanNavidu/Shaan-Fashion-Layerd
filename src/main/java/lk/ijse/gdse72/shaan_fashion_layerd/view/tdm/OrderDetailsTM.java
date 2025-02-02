package lk.ijse.gdse72.shaan_fashion_layerd.view.tdm;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsTM {
    private String orderId;
    private String itemId;
    private int quantity;
    private double price;
}
