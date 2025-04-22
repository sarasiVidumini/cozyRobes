package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.DeliveryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeliveryModel {
    public String saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO delivery VALUES (?,?,?,?)");
        save.setString(1, deliveryDto.getDeliveryId());
        save.setString(2,deliveryDto.getOrderId());
        save.setString(3,deliveryDto.getAddress());
        save.setString(4,deliveryDto.getStatus());

        return save.executeUpdate() > 0 ? "Successfully saved delivery " : "Fail";

    }

    public String updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("UPDATE delivery SET order_id=?,address=?,status=? WHERE delivery_id=?");
        update.setString(1,deliveryDto.getOrderId());
        update.setString(2,deliveryDto.getAddress());
        update.setString(3,deliveryDto.getStatus());
        update.setString(4,deliveryDto.getDeliveryId());

        return update.executeUpdate() > 0 ? "Successfully updated delivery " : "Fail";
    }

    public String deleteDelivery(String deliveryId) throws SQLException, ClassNotFoundException {
        Connection connection = (Connection) DBConnection.getInstance().getConnection();
        PreparedStatement update = connection.prepareStatement("DELETE FROM delivery WHERE delivery_id=?");
        update.setString(1,deliveryId);

        return update.executeUpdate() > 0 ? "Successfully deleted delivery " : "Fail";

    }
}
