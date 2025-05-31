package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetailsDto {
    private String orderDetailId;
    private String orderId;
    private String productId;
    private int quantity;
    private double priceAtPurchase;
    private double updatePrice;


}

