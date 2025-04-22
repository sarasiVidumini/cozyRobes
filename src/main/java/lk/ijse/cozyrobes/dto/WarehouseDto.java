package lk.ijse.cozyrobes.dto;

public class WarehouseDto {
    private String sectionId;
    private String productId;
    private int capacity;
    private String location;

    public WarehouseDto(){}

    public WarehouseDto(String sectionId, String productId, int capacity, String location) {
        this.sectionId = sectionId;
        this.productId = productId;
        this.capacity = capacity;
        this.location = location;

    }

    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }


    @Override
    public String toString() {
        return "WarehouseDto{" + "sectionId='" + sectionId + '\'' + ", productId='" + productId + '\'' + ", capacity=" + capacity + ", location='" + location + '\'' + '}';
    }
}
