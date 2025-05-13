package lk.ijse.cozyrobes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MaintenanceDto {
    private String maintenance_id;
    private String material_id;
    private String section_id;
    private String maintenance_date;
    private String maintenance_status;
    private double cost;
}
