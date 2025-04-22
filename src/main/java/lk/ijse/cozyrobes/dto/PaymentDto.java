package lk.ijse.cozyrobes.dto;

public class PaymentDto {
    private String paymentId;
    private String orderId;
    private String paymentMethod;
    private double amount;

    public PaymentDto(){}

    public PaymentDto(String paymentId, String orderId, String paymentMethod, double amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;

    }

    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "PaymentDto{" + "paymentId='" + paymentId + '\'' + ", orderId='" + orderId + '\'' + ", paymentMethod='" + paymentMethod + '\'' + ", amount=" + amount + '}';
    }
}
