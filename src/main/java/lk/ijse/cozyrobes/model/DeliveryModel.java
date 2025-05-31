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

    public boolean saveDelivery(DeliveryDto deliveryDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO delivery VALUES(?,?,?,?)",
                deliveryDto.getDeliveryId(),
                deliveryDto.getOrderId(),
                deliveryDto.getAddress(),
                deliveryDto.getStatus()

        );
    }

    public boolean updateDelivery(DeliveryDto deliveryDto) throws SQLException {
        return CrudUtil.execute(
                "update delivery set order_id = ? , address = ? , status = ? where delivery_id = ? ",
                deliveryDto.getOrderId(),
                deliveryDto.getAddress(),
                deliveryDto.getStatus(),
                deliveryDto.getDeliveryId()
        );
    }

    public boolean deleteDelivery(String delivery_id) throws SQLException {
        return CrudUtil.execute(
                "delete from delivery where delivery_id = ?",
                delivery_id
        );
    }

   public ArrayList<DeliveryDto> searchDelivery(String search) throws SQLException {
        ArrayList<DeliveryDto> dtos = new ArrayList<>();
        String sql = "select * from delivery where delivery_id LIKE ? OR order_id LIKE ? OR address LIKE ? OR status LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            DeliveryDto deliveryDto = new DeliveryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            dtos.add(deliveryDto);
        }
        return dtos;
   }

    public ArrayList<DeliveryDto> getAllDelivery() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM delivery");
        ArrayList<DeliveryDto> deliveryDtos = new ArrayList<>();
        while (resultSet.next()) {
            DeliveryDto deliveryDto = new DeliveryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            deliveryDtos.add(deliveryDto);
        }
        return deliveryDtos;
    }

}

