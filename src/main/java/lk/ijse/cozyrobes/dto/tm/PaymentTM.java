package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTM {
    private String payment_id;
    private String order_id;
    private String payment_method;
    private String total_amount;
}
