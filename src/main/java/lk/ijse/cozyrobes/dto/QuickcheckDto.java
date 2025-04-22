package lk.ijse.cozyrobes.dto;

public class QuickcheckDto {
    private String checkId;
    private String maintenanceId;
    private String checkType;
    private String status;

    public QuickcheckDto() {}

    public QuickcheckDto(String checkId, String maintenanceId, String checkType, String status) {
        this.checkId = checkId;
        this.maintenanceId = maintenanceId;
        this.checkType = checkType;
        this.status = status;

    }

    public String getCheckId() {
        return checkId;
    }
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
    public String getMaintenanceId() {
        return maintenanceId;
    }
    public void setMaintenanceId(String maintenanceId) {
        this.maintenanceId = maintenanceId;
    }
    public String getCheckType() {
        return checkType;
    }
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "QuickcheckDto{" +
                "checkId='" + checkId + '\'' +
                ", maintenanceId='" + maintenanceId + '\'' +
                ", checkType='" + checkType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
