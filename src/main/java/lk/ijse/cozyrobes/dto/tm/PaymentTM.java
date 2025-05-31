package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTM {
    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private double totalAmount;
}
