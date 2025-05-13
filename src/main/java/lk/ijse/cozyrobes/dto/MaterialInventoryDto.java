package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class MaterialInventoryDto {
    private String material_id;
    private String supplier_id;
    private String material_name;
    private int quantity;

}
