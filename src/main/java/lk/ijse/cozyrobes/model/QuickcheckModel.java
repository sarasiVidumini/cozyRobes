package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.QuickcheckDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuickcheckModel {
    public String saveQuickcheck(QuickcheckDto quickcheckDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO quickcheck VALUES (?,?,?,?)");
        save.setString(1,quickcheckDto.getCheckId());
        save.setString(2,quickcheckDto.getMaintenanceId());
        save.setString(3,quickcheckDto.getCheckType());
        save.setString(4,quickcheckDto.getStatus());

        return save.executeUpdate() > 0 ? "Successfully saved quickcheck" : "Fail";

    }

    public String updateQuickcheck(QuickcheckDto quickcheckDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE quickcheck SET maintenance_id=?, check_type=?, status=? WHERE check_id=?");
        update.setString(1,quickcheckDto.getMaintenanceId());
        update.setString(2,quickcheckDto.getCheckType());
        update.setString(3,quickcheckDto.getStatus());
        update.setString(4,quickcheckDto.getCheckId());

        return update.executeUpdate() > 0 ? "Successfully updated quickcheck" : "Fail";

    }

    public String deleteQuickcheck(QuickcheckDto quickcheckDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM quickcheck WHERE check_id=?");
        update.setString(1,quickcheckDto.getCheckId());

        return update.executeUpdate() > 0 ? "Successfully deleted quickcheck" : "Fail";
    }
}
