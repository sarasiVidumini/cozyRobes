package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.MaintenanceDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaintenanceModel {
    public String saveMaintenance(MaintenanceDto maintenanceDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO maintenance VALUES (?,?,?,?,?,?)");
        save.setString(1, maintenanceDto.getMaintenanceId());
        save.setString(2,maintenanceDto.getMaterialId());
        save.setString(3,maintenanceDto.getSectionId());
        save.setDouble(4,maintenanceDto.getCost());
        save.setString(5,maintenanceDto.getMaintenanceStatus());
        save.setString(6,maintenanceDto.getMaintenanceDate());

        return  save.executeUpdate() > 0 ? "Successful maintenance saved" : "Fail";

    }

    public String updateMaintenance(MaintenanceDto maintenanceDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE maintenance SET material_Id = ? , section_Id = ?, cost = ?, maintenance_status = ?,maintenance_date = ? WHERE maintenance_id = ?");
        update.setString(1,maintenanceDto.getMaterialId());
        update.setString(2,maintenanceDto.getSectionId());
        update.setDouble(3,maintenanceDto.getCost());
        update.setString(4,maintenanceDto.getMaintenanceStatus());
        update.setString(5,maintenanceDto.getMaintenanceDate());
        update.setString(6,maintenanceDto.getMaintenanceId());

        return update.executeUpdate() > 0 ? "Successful maintenance updated" : "Fail";

    }

    public String deleteMaintenance(String maintenanceId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM maintenance WHERE maintenance_id = ?");
        update.setString(1,maintenanceId);

        return update.executeUpdate() > 0 ? "Successful maintenance deleted" : "Fail";
    }

}
