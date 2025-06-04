package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsTM {
    private String orderDetailId;
    private String orderId;
    private String productId;
    private int quantity;
    private double priceAtPurchase;
}
