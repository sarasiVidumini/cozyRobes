package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class QuickcheckTM {
    private String check_id;
    private String maintenance_id;
    private String check_type;
    private String status;
}
