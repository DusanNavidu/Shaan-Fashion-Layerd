package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BrandDTO implements Serializable {
    private String brandId;
    private String brandName;
    private String description;
}
