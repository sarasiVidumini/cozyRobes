package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {
   private String product_id;
   private String name;
   private int quantity;
   private String category;
   private double unit_price;

}
