package lk.ijse.cozyrobes.dto;

public class ProductDto {
   private String productId;
   private String name;
   private int quantity;
   private double unitPrice;
   private String category;

   public ProductDto() {}

    public ProductDto(String productId, String name, int quantity, double unitPrice, String category) {
       this.productId = productId;
       this.name = name;
       this.quantity = quantity;
       this.unitPrice = unitPrice;
       this.category = category;

    }

    public String getProductId() {
       return productId;
    }
    public void setProductId(String productId) {
       this.productId = productId;
    }
    public String getName() {
       return name;
    }
    public void setName(String name) {
       this.name = name;
    }
    public int getQuantity() {
       return quantity;
    }
    public void setQuantity(int quantity) {
       this.quantity = quantity;
    }
    public double getUnitPrice() {
       return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
       this.unitPrice = unitPrice;
    }
    public String getCategory() {
       return category;
    }
    public void setCategory(String category) {
       this.category = category;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", category='" + category + '\'' +
                '}';
    }
}
