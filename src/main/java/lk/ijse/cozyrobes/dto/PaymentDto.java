package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaymentDto {
    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private double totalAmount;


}
