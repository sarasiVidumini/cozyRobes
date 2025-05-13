package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class QuickcheckDto {
    private String check_id;
    private String maintenance_id;
    private String check_type;
    private String status;

}
