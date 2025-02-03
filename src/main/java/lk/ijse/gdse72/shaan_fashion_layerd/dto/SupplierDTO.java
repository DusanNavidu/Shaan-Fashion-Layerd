package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class SupplierDTO implements Serializable {
    private String supplierId;
    private String supplierName;
    private String supplyItem;
    private String supplierAddress;
    private String contactNo;
}
