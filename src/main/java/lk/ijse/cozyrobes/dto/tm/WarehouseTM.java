package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTM {
    private String section_id;
    private String product_id;
    private String capacity;
    private String location;
}
