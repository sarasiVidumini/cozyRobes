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
    private String maintenanceId;
    private String materialId;
    private String sectionId;
    private double cost;
    private String maintenanceStatus;
    private String maintenanceDate;

}
