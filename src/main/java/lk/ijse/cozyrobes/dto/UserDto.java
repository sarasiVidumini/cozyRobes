package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private String userId;
    private String role;
    private String name;
    private String contact;

}

