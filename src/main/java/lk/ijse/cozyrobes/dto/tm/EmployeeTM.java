package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeTM {
    private String employee_id;
    private String user_id;
    private String name;
    private String role;
    private double salary;
}
