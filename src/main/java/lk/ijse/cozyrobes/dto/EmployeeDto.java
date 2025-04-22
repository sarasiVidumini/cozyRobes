package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {
    private String employeeId;
    private String userId;
    private String name;
    private String role;
    private String salary;

}
