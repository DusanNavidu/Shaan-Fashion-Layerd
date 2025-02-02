package lk.ijse.gdse72.shaan_fashion_layerd.entity;

import lk.ijse.gdse72.shaan_fashion_layerd.dto.OrderDetailsDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Order {
    private String orderId;
    private String customerId;
    private Date orderDate;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}
