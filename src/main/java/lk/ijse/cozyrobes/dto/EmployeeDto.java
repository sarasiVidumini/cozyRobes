package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {
    private String employee_id;
    private String user_id;
    private String name;
    private String role;
    private double salary;

}
