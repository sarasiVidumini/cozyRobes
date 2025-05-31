package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.QuickcheckDto;
import lk.ijse.cozyrobes.dto.WarehouseDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuickcheckModel {
    public String getNextCheckId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT check_id FROM quick_check ORDER BY check_id DESC LIMIT 1");
        String tableCharacter = "QC";
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableCharacter.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }


    public boolean saveQuickcheck(QuickcheckDto quickcheckDto) throws SQLException {
        return CrudUtil.execute(
                "insert into quick_check values (?,?,?,?)",
                quickcheckDto.getCheckId(),
                quickcheckDto.getMaintenanceId(),
                quickcheckDto.getCheckType(),
                quickcheckDto.getStatus()
        );
    }

    public ArrayList<QuickcheckDto> getAllQuickcheck() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from quick_check");
        ArrayList<QuickcheckDto> quickcheckDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            QuickcheckDto quickcheckDto = new QuickcheckDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            quickcheckDtoArrayList.add(quickcheckDto);
        }
        return quickcheckDtoArrayList;
    }


    public boolean  updateQuickCheck(QuickcheckDto quickcheckDto) throws SQLException {
        return CrudUtil.execute(
                "update quick_check set maintenance_id = ? , check_type = ? , status = ? where check_id = ?",
                quickcheckDto.getMaintenanceId(),
                quickcheckDto.getCheckType(),
                quickcheckDto.getStatus(),
                quickcheckDto.getCheckId()
        );
    }

    public boolean deleteQuickcheck(String check_id) throws SQLException {
        return CrudUtil.execute(
                "delete from quick_check where check_id = ?",
                check_id
        );
    }

    public ArrayList<QuickcheckDto> searchQuickcheck(String search) throws SQLException {
        ArrayList<QuickcheckDto> dtos = new ArrayList<>();
        String sql = "select * from quick_check where check_id LIKE ? OR maintenance_id LIKE ? OR check_type LIKE ? OR status LIKE ? ";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern,pattern,pattern,pattern);
        while (resultSet.next()) {
            QuickcheckDto dto = new QuickcheckDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public ArrayList<String> getAllCheckIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select check_id from quick_check");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }
}
