package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class QuickcheckTM {
    private String checkId;
    private String maintenanceId;
    private String checkType;
    private String status;
}
