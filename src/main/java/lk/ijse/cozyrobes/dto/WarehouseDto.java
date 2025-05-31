package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class WarehouseDto {
    private String sectionId;
    private String productId;
    private int capacity;
    private String location;

}
