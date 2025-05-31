package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTM {
    private String sectionId;
    private String productId;
    private int capacity;
    private String location;
}
