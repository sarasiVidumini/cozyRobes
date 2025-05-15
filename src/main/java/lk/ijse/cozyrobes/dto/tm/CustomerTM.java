package lk.ijse.cozyrobes.dto.tm;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CustomerTM {
   private String customer_id;
   private String name;
   private String phone;
   private String email;
}
