package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DeliveryDto {
    private String delivery_id;
    private String order_id;
    private String address;
    private String status;

}
