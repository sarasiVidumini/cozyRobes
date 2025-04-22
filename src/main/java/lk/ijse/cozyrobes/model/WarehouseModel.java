package lk.ijse.cozyrobes.model;

import lk.ijse.cozyrobes.db.DBConnection;
import lk.ijse.cozyrobes.dto.WarehouseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WarehouseModel {
    public String saveWarehouse(WarehouseDto warehouseDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement save = connection.prepareStatement("INSERT INTO warehouse VALUES (?,?,?,?)");
        save.setString(1,warehouseDto.getSectionId());
        save.setString(2,warehouseDto.getProductId());
        save.setInt(3,warehouseDto.getCapacity());
        save.setString(4,warehouseDto.getLocation());

        return save.executeUpdate() > 0 ? "Successfully saved warehouse" :"Fail";

    }

    public String updateWarehouse(WarehouseDto warehouseDto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update = connection.prepareStatement("UPDATE warehouse SET product_Id=?, capacity=?, location=? WHERE section_Id=?");
        update.setString(1,warehouseDto.getProductId());
        update.setInt(2,warehouseDto.getCapacity());
        update.setString(3,warehouseDto.getLocation());
        update.setString(4,warehouseDto.getSectionId());

        return update.executeUpdate() > 0 ? "Successfully updated warehouse" :"Fail";
    }

    public String deleteWarehouse(String sectionId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().connection();
        PreparedStatement update  = connection.prepareStatement("DELETE FROM warehouse WHERE section_Id=?");
        update.setString(1,sectionId);


        return update.executeUpdate() > 0 ? "Successfully deleted warehouse" :"Fail";
    }
}
