package lk.ijse.cozyrobes.dto;

import javafx.scene.control.Button;
import lombok.*;

@Data
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CartDto {
        private String customerId;
        private String productId;
        private String productName;
        private int cartQty;
        private double unitPrice;
        private double total;
        private String paymentMethod;
        private Button btnRemove;
}
