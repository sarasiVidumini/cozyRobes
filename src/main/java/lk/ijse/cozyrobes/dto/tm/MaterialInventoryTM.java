package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaterialInventoryTM {
    private String materialId;
    private String supplierId;
    private String materialName;
    private int quantity;
}
