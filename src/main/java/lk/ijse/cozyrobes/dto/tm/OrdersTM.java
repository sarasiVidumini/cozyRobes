package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersTM {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String status;
    private String productId;
}
