package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTM {
    private String maintenance_id;
    private String material_id;
    private String section_id;
    private String maintenance_date;
    private String maintenance_status;
    private double cost;
}
