package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class MaterialInventoryDto {
    private String materialId;
    private String supplierId;
    private String materialName;
    private int quantity;

}
