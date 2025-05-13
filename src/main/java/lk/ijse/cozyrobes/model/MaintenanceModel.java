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
            String lastIdNumberString = lastId.substring(1);
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
               maintenanceDto.getMaintenance_id(),
                maintenanceDto.getMaterial_id(),
                maintenanceDto.getSection_id(),
                maintenanceDto.getMaintenance_date(),
                maintenanceDto.getMaintenance_status(),
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
                    resultSet.getString(4),
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
                maintenanceDto.getMaterial_id(),
                maintenanceDto.getSection_id(),
                maintenanceDto.getMaintenance_date(),
                maintenanceDto.getMaintenance_status(),
                maintenanceDto.getCost(),
                maintenanceDto.getMaintenance_id()
        );

    }

    public boolean deleteMaintenance(String maintenance_id) throws SQLException {
        return CrudUtil.execute(
                "delete from maintenance where maintenance_id = ?",
                maintenance_id
        );
    }

    public MaintenanceDto searchMaintenance(String maintenance_id) throws ClassNotFoundException, SQLException{

        ResultSet rst = CrudUtil.execute("select * from maintenance where maintenance_id = ?",
                maintenance_id
        );

        if (rst.next()) {
            MaintenanceDto maintenanceDto = new MaintenanceDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6)
            );
            return maintenanceDto;
        }
        return null;
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
