package lk.ijse.cozyrobes.dto;

public class OrderDto {
    private String orderId;
    private String customerId;
    private String status;
    private String date;

    public OrderDto() {}

    public OrderDto(String orderId, String customerId, String status, String date) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.status = status;
    this.date = date;

    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OrderDto{" + "orderId='" + orderId + '\'' + ", customerId='" + customerId + '\'' + ", status='" + status + '\'' + ", date='" + date + '\'' + '}';
    }
}
