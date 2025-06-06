package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.MaintenanceDto;
import lk.ijse.cozyrobes.dto.WarehouseDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaintenanceModel {
    public String getNextMaintenanceId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select maintenance_id from maintenance order by maintenance_id desc limit 1");
        String tableCharacter = "MA";
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableCharacter.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber); // "C002"
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    public boolean saveMaintenance(MaintenanceDto maintenanceDto) throws SQLException {
        return CrudUtil.execute(
                "insert into maintenance values(?,?,?,?,?,?)",
               maintenanceDto.getMaintenanceId(),
                maintenanceDto.getMaterialId(),
                maintenanceDto.getSectionId(),
                maintenanceDto.getMaintenanceDate(),
                maintenanceDto.getMaintenanceStatus(),
                maintenanceDto.getCost()
        );
    }

    public ArrayList<MaintenanceDto> getAllMaintenance() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from maintenance");
        ArrayList<MaintenanceDto> maintenanceDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            MaintenanceDto maintenanceDto = new MaintenanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            );
            maintenanceDtoArrayList.add(maintenanceDto);
        }
        return maintenanceDtoArrayList;
    }


    public boolean  updateMaintenance(MaintenanceDto maintenanceDto) throws SQLException {
        return CrudUtil.execute(
               "update maintenance set material_id = ? , section_id = ? , maintenance_date = ? , maintenance_status = ? , cost = ? where maintenance_id = ?",
                maintenanceDto.getMaterialId(),
                maintenanceDto.getSectionId(),
                maintenanceDto.getMaintenanceDate(),
                maintenanceDto.getMaintenanceStatus(),
                maintenanceDto.getCost(),
                maintenanceDto.getMaintenanceId()
        );

    }

    public boolean deleteMaintenance(String maintenance_id) throws SQLException {
        return CrudUtil.execute(
                "delete from maintenance where maintenance_id = ?",
                maintenance_id
        );
    }

    public ArrayList<MaintenanceDto> searchMaintenance(String search) throws SQLException {
        ArrayList<MaintenanceDto> dtos = new ArrayList<>();
        String sql = "select * from maintenance where maintenance_id LIKE ? OR material_id LIKE ? OR section_id LIKE ? OR maintenance_date LIKE ? OR maintenance_status LIKE ? OR cost LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern,pattern,pattern,pattern,pattern,pattern);
        while (resultSet.next()) {
            MaintenanceDto maintenanceDto = new MaintenanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            );
            dtos.add(maintenanceDto);
        }
        return dtos;
    }

    public ArrayList<String> getAllMaintenanceIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select maintenance_id from maintenance");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }
}
