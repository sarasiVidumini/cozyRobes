package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CustomerDto {
    private String customer_id;
    private String name;
    private String phone;
    private String email;


}
