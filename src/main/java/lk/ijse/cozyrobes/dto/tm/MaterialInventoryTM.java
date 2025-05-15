package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaterialInventoryTM {
    private String material_id;
    private String supplier_id;
    private String material_name;
    private int quantity;
}
