package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private String user_id;
    private String role;
    private String name;
    private String contact;

}

