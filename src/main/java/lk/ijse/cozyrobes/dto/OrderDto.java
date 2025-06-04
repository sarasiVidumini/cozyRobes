package lk.ijse.cozyrobes.dto;

import lombok.*;

import java.util.ArrayList;

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
    private ArrayList<CartDto> cartItems;

}
