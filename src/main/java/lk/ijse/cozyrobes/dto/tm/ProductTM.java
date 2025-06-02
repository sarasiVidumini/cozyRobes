package lk.ijse.cozyrobes.dto.tm;


import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class ProductTM{
    private String productId;
    private String name;
    private int quantity;
    private String category;
    private double unitPrice;
}
