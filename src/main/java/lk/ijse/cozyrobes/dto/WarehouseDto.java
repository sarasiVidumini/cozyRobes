package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class WarehouseDto {
    private String section_id;
    private String product_id;
    private int capacity;
    private String location;

}
