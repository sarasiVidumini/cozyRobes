package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeTM {
    private String employeeId;
    private String userId;
    private String name;
    private String role;
    private double salary;
}
