package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class QuickcheckDto {
    private String checkId;
    private String maintenanceId;
    private String checkType;
    private String status;

}
