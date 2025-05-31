package lk.ijse.cozyrobes.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class SupplierDto {
    private String supplierId;
    private String name;
    private String address;
    private String contact;

}
