package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTM {
    private String deliveryId;
    private String orderId;
    private String address;
    private String status;
}
