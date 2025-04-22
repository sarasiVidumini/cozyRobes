package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class DeliveryDto {
    private String deliveryId;
    private String orderId;
    private String address;
    private String status;

}
