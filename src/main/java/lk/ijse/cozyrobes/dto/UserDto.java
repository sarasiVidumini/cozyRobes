package lk.ijse.cozyrobes.dto;

public class UserDto {
    private String userId;
    private String role;
    private String name;
    private String contact;

    public UserDto() {}

    public UserDto(String id, String role, String name, String contact) {
        this.userId = id;
        this.role = role;
        this.name = name;
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }


    @Override
    public String toString() {
        return "UserDto{" + "userId='" + userId + '\'' + ", role='" + role + '\'' + ", name='" + name + '\'' + ", contact='" + contact + '\'' + '}';
    }
}

