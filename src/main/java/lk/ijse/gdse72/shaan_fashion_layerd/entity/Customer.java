package lk.ijse.gdse72.shaan_fashion_layerd.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String customerId;
    private String userId;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
}
