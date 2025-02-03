package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDTO implements Serializable {
    private String customerId;
    private String userId;
    private String customerName;
    private String customerAddress;
    private String customerEmail;
}
