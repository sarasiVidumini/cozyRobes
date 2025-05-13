package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaymentDto {
    private String payment_id;
    private String order_id;
    private String payment_method;
    private double amount;


}
