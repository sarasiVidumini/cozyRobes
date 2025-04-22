package lk.ijse.cozyrobes.dto;

public class SupplierDto {
    private String supplierId;
    private String name;
    private String address;
    private String contact;

    public SupplierDto() {}

    public SupplierDto(String supplierId, String name, String address, String contact) {
        this.supplierId = supplierId;
        this.name = name;
        this.address = address;
        this.contact = contact;

    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "SupplierDto{" +
                "supplierId='" + supplierId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
