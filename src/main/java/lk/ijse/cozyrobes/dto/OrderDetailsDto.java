package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetailsDto {
    private String orderDetail_id;
    private String order_id;
    private String product_id;
    private int quantity;
    private double price_at_purchase;


}

