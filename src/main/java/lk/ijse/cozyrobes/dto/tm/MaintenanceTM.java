package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTM {
    private String maintenanceId;
    private String materialId;
    private String sectionId;
    private Date maintenanceDate;
    private String maintenanceStatus;
    private double cost;
}
