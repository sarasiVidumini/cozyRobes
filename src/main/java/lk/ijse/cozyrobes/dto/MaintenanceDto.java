package lk.ijse.cozyrobes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class MaintenanceDto {
    private String maintenanceId;
    private String materialId;
    private String sectionId;
    private Date maintenanceDate;
    private String maintenanceStatus;
    private double cost;
}
