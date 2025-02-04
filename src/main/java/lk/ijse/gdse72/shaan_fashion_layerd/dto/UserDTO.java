package lk.ijse.gdse72.shaan_fashion_layerd.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class UserDTO implements Serializable {
    private String UserId;
    private String userFullName;
    private String username;
    private String userEmail;
    private String password;
}
