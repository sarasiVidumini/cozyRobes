package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class UserTM {
    private String user_id;
    private String role;
    private String name;
    private String contact;
}
