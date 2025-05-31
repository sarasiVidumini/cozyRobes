package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTM {
    private String maintenanceId;
    private String materialId;
    private String sectionId;
    private String maintenanceDate;
    private String maintenanceStatus;
    private double cost;
}
