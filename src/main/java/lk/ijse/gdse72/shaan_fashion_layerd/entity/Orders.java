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

public class Orders {
    private String orderId;
    private String customerId;
    private Date orderDate;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;

    public Orders(String orderId, String customerId, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
}
