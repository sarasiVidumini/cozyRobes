package lk.ijse.cozyrobes.dto;

public class OrderProductDto {
    private String orderProductId;
    private String orderId;
    private String productId;
    private int quantity;

    public OrderProductDto() {}

    public OrderProductDto(String orderProductId, String orderId, String productId, int quantity) {
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;

    }

    public String getOrderProductId() {
        return orderProductId;
    }
    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "OrderProductDto{" + "orderProductId='" + orderProductId + '\'' + ", orderId='" + orderId + '\'' + ", productId='" + productId + '\'' + ", quantity=" + quantity + '}';
    }
}

