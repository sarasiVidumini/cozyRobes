package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrdersTM {
    private String order_id;
    private String customer_id;
    private String order_date;
    private String order_status;
    private String product_id;
}
