package lk.ijse.cozyrobes.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartTM {
        private String customerId;
        private String productId;
        private String productName;
        private int cartQty;
        private double unitPrice;
        private double total;
        private String paymentMethod;
        private Button btnRemove;

}
