package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO {
    private String customerId;
    private String userId;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
}
