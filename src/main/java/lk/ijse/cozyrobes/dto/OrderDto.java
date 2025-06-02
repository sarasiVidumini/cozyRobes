package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String status;
    private String productId;

}
