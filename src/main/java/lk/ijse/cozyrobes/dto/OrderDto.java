package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class OrderDto {
    private String order_id;
    private String customer_id;
    private String status;
    private String date;
    private String product_id;

}
