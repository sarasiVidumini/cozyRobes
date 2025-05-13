package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.DeliveryDto;
import lk.ijse.cozyrobes.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryModel {
//    public String saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
//        Connection connection = (Connection) DBConnection.getInstance().getConnection();
//        PreparedStatement save = connection.prepareStatement("INSERT INTO delivery VALUES (?,?,?,?)");
//        save.setString(1, deliveryDto.getDeliveryId());
//        save.setString(2,deliveryDto.getOrderId());
//        save.setString(3,deliveryDto.getAddress());
//        save.setString(4,deliveryDto.getStatus());
//
//        return save.executeUpdate() > 0 ? "Successfully saved delivery " : "Fail";
//
//    }
//
//    public String updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
//        Connection connection = (Connection) DBConnection.getInstance().getConnection();
//        PreparedStatement update = connection.prepareStatement("UPDATE delivery SET order_id=?,address=?,status=? WHERE delivery_id=?");
//        update.setString(1,deliveryDto.getOrderId());
//        update.setString(2,deliveryDto.getAddress());
//        update.setString(3,deliveryDto.getStatus());
//        update.setString(4,deliveryDto.getDeliveryId());
//
//        return update.executeUpdate() > 0 ? "Successfully updated delivery " : "Fail";
//    }
//
//    public String deleteDelivery(String deliveryId) throws SQLException, ClassNotFoundException {
//        Connection connection = (Connection) DBConnection.getInstance().getConnection();
//        PreparedStatement update = connection.prepareStatement("DELETE FROM delivery WHERE delivery_id=?");
//        update.setString(1,deliveryId);
//
//        return update.executeUpdate() > 0 ? "Successfully deleted delivery " : "Fail";


//    }
    public String getNextDeliveryId() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("SELECT delivery_id FROM delivery ORDER BY delivery_id DESC LIMIT 1");
        char tableCharacter = 'D'; // Use any character Ex:- customer table for C, item table for I
        if (resultSet.next()) {
            String lastId = resultSet.getString(1); // "C001"
            String lastIdNumberString = lastId.substring(1); // "001"
            int lastIdNumber = Integer.parseInt(lastIdNumberString); // 1
            int nextIdNUmber = lastIdNumber + 1; // 2
            // "C002"
            return String.format(tableCharacter + "%03d", nextIdNUmber);
        }
            // No data recode in table so return initial primary key
            return tableCharacter + "001";
    }

    public String saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into delivery values(?,?,?,?)",
                deliveryDto.getDelivery_id(),
                deliveryDto.getOrder_id(),
                deliveryDto.getAddress(),
                deliveryDto.getStatus()

        );
    }

    public String updateDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update delivery set order_id = ? , address = ? , status = ? where delivery_id ",
                deliveryDto.getOrder_id(),
                deliveryDto.getAddress(),
                deliveryDto.getStatus(),
                deliveryDto.getDelivery_id()
        );
    }

    public String deleteDelivery(String delivery_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from delivery where delivery_id = ?",
                delivery_id
        );
    }

    public DeliveryDto searchDelivery(String delivery_id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "select * from delivery where delivery_id = ?",
                delivery_id
        );
        if (resultSet.next()) {
            DeliveryDto deliveryDto = new DeliveryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            return deliveryDto;
        }

        return null;

    }

    public ArrayList<DeliveryDto> getAllDelivery() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from delivery");
        ArrayList<DeliveryDto> deliveryDtos = new ArrayList<>();
        while (resultSet.next()) {
            DeliveryDto deliveryDto = new DeliveryDto(
                    resultSet.getString("delivery_id"),
                    resultSet.getString("order_id"),
                    resultSet.getString("address"),
                    resultSet.getString("status")
            );
            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;
    }

}

