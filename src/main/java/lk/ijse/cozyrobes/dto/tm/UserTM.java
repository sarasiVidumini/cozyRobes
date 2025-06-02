package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class UserTM {
    private String userId;
    private String role;
    private String name;
    private String contact;
    private String password;
}
