package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTM {
    private String delivery_id;
    private String order_id;
    private String address;
    private String status;
}
